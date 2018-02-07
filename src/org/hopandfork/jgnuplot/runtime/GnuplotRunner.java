/*
 * Copyright 2006, 2017 Maximilian H Fabricius, Hop and Fork.
 * 
 * This file is part of JGNUplot.
 * 
 * JGNUplot is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * JGNUplot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JGNUplot.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.hopandfork.jgnuplot.runtime;

import java.awt.Image;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.hopandfork.jgnuplot.model.Plot;
import org.hopandfork.jgnuplot.runtime.terminal.Terminal;

/**
 * Runnable used to asynchronously run Gnuplot.
 */
public class GnuplotRunner implements Runnable {

	/**
	 * Plot.
	 */
	private Plot plot;

	/**
	 * Gnuplot terminal.
	 */
	private Terminal terminal;

	/**
	 * Command to use for running gnuplot.
	 */
	private static final String GNUPLOT_CMD = "gnuplot";

	/**
	 * Consumer of the generated plot.
	 */
	private ImageConsumer imageConsumer = null;

	/**
	 * Logger.
	 */
	static final private Logger LOG = Logger.getLogger(GnuplotRunner.class);

	/**
	 * Callback interface for receiving gnuplot output.
	 */
	public interface ImageConsumer {
		void onImageGenerated(Image image);

		void onImageGenerated(File output);

		void onImageGenerationError(String errorMessage);
	}

	private GnuplotRunner(Plot plot, Terminal terminal, ImageConsumer imageConsumer) {
		this.plot = plot;
		this.terminal = terminal;
		this.imageConsumer = imageConsumer;
	}

	public static void runGnuplot(Terminal terminal, Plot plot) {
		runGnuplot(terminal, plot, null);
	}

	public static void runGnuplot(Terminal terminal, Plot plot, ImageConsumer imageConsumer) {
		GnuplotRunner gnuplotRunner = new GnuplotRunner(plot, terminal, imageConsumer);
		new Thread(gnuplotRunner).start();
	}


	/**
	 * Entry point for GnuplotRunner.
	 */
	public void run() {
		try {
			/* Spawns a new process to execute gnuplot */
			Process p = Runtime.getRuntime().exec(GNUPLOT_CMD);
			LOG.info("Running gnuplot...");

			/* Writes plot script to stdin */
			OutputStream stdin = p.getOutputStream();
			BufferedOutputStream bw = new BufferedOutputStream(stdin);
			bw.write(terminal.toPlotString().getBytes());
			bw.write(plot.toPlotString().getBytes());
			bw.flush();
			bw.close();
			stdin.close();

			/* Reads from stdoutput of the process. */
			InputStream stdout = p.getInputStream();

			Image image = null;
			if (terminal.getOutputFile() == null)
				image = ImageIO.read(stdout);

			InputStream stderr = p.getErrorStream();

			/* Waits for gnuplot process termination */
			p.waitFor();
			stdout.close();

			if (p.exitValue() != 0) {
				LOG.error("gnuplot exited with value: " + p.exitValue());

				/* Reads error message. */
				StringBuilder errorMsgBuilder = new StringBuilder();
				while (stderr.available() > 0) {
					errorMsgBuilder.append((char) stderr.read());
				}

				LOG.error("Gnuplot: " + errorMsgBuilder.toString());

				if (imageConsumer != null) {
					imageConsumer.onImageGenerationError(errorMsgBuilder.toString());
				}
			} else if (imageConsumer != null) {
				if (terminal.getOutputFile() == null)
					imageConsumer.onImageGenerated(image);
				else
					imageConsumer.onImageGenerated(terminal.getOutputFile());
			}


		} catch (IOException e) {
			e.printStackTrace();
			if (imageConsumer != null)
				imageConsumer.onImageGenerationError("Could not run 'gnuplot'!");
		} catch (InterruptedException e) {
			e.printStackTrace();
			if (imageConsumer != null)
				imageConsumer.onImageGenerationError("'gnuplot' execution failed!");
		}

	}

}