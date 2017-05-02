package org.hopandfork.jgnuplot.runtime;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Runnable used to asynchronously run Gnuplot.
 */
public class GnuplotRunner implements Runnable {

	/** Plot string. */
	private String gpPlotString;

	/** Command to use for running gnuplot. */
	private static final String GNUPLOT_CMD = "gnuplot";

	public GnuplotRunner(String plotString) {
		this.gpPlotString = plotString;
	}

	/**
	 * Entry point for GnuplotRunner.
	 */
	public void run() {
		try {
			/* Spawns a new process to execute gnuplot */
			Process p = Runtime.getRuntime().exec(GNUPLOT_CMD);

			/* Writes plot script to stdin */
			OutputStream stdin = p.getOutputStream();
			BufferedOutputStream bw = new BufferedOutputStream(stdin);
			bw.write(gpPlotString.getBytes());
			bw.flush();

			/* Waits for gnuplot process termination */
			p.waitFor();
			bw.close();
			stdin.close();

			if (p.exitValue() != 0) {
				System.err.println("GNUplot exited with value: " + p.exitValue());
			}
		} catch (IOException e) {
			e.printStackTrace(); // TODO display an error message?
		} catch (InterruptedException e) {
			e.printStackTrace(); // TODO display an error message?
		}

	}

}