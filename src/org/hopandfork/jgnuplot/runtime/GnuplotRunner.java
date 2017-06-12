package org.hopandfork.jgnuplot.runtime;

import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

/**
 * Runnable used to asynchronously run Gnuplot.
 */
public class GnuplotRunner implements Runnable {

	/** Plot string. */
	private String gpPlotString;

	/** Command to use for running gnuplot. */
	private static final String GNUPLOT_CMD = "gnuplot";

	private ImageConsumer imageConsumer = null;

	static final private Logger LOG = Logger.getLogger(GnuplotRunner.class);

	public GnuplotRunner(String plotString) {
		this.gpPlotString = plotString;
	}

	public GnuplotRunner(String plotString, ImageConsumer imageConsumer) {
		this.gpPlotString = plotString;
		this.imageConsumer = imageConsumer;
	}

	public interface ImageConsumer {
		void readImage(Image image);
	}

	/**
	 * Entry point for GnuplotRunner.
	 */
	public void run() {
		try {
			/* Spawns a new process to execute gnuplot */
			Process p = Runtime.getRuntime().exec(GNUPLOT_CMD);
			LOG.debug("Initialized process...");

			/* Writes plot script to stdin */
			OutputStream stdin = p.getOutputStream();
			BufferedOutputStream bw = new BufferedOutputStream(stdin);
			bw.write(gpPlotString.getBytes());
			bw.flush();
			bw.close();
			stdin.close();

			/* Reads from stdoutput of the process. */
			InputStream stdout = p.getInputStream();
			Image image = ImageIO.read(stdout);
			InputStream stderr = p.getErrorStream();

			/* Waits for gnuplot process termination */
			p.waitFor();
			stdout.close();

			if (p.exitValue() != 0) {
				String errorMsg = "";
				while (stderr.available() > 0) {
					errorMsg += (char)stderr.read();
				}
				LOG.error("GNUplot exited with value: " + p.exitValue() + " - " + errorMsg);
				return;
			}

			if (imageConsumer != null)
				imageConsumer.readImage(image);

		} catch (IOException e) {
			e.printStackTrace(); // TODO display an error message?
		} catch (InterruptedException e) {
			e.printStackTrace(); // TODO display an error message?
		}

	}

}