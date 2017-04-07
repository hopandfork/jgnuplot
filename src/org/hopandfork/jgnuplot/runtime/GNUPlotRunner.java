package org.hopandfork.jgnuplot.runtime;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.hopandfork.jgnuplot.utility.JGPPrintWriter;

/**
 * Runnable used to asynchronously run GNUplot.
 */
public class GNUPlotRunner extends ProcessRunner {

	/** Plot string. */
	private String gpPlotString;

	/** File to which plot string is written. */
	private static final String PLOTFILENAME = "work.gnuplot";

	/** Command to use for running gnuplot. */
	private static final String GNUPLOT_CMD = "gnuplot";

	public GNUPlotRunner(String plotString, JGPPrintWriter printWriter) {
		super(printWriter);
		this.gpPlotString = plotString;
	}
	
	/**
	 * Writes the plot script to file.
	 * @param filename Filename for writing to.
	 * @throws IOException if writing fails.
	 */
	private void writePlotScript(String filename) throws IOException
	{
			PrintWriter logWriter = new PrintWriter(new BufferedWriter(new FileWriter(filename, false)));
			logWriter.write(gpPlotString);
			logWriter.flush();
			logWriter.close();
	}
	

	/**
	 * Entry point for GNUPlotRunner.
	 */
	public void run() {
		try {
			/* Writes plot script to file. */
			writePlotScript(PLOTFILENAME);
			
			/* Runs gnuplot. */
			cmdExec(GNUPLOT_CMD + " " + PLOTFILENAME);

			System.out.println("GNUplot exited with value: " + p.exitValue());
			System.out.println("GNUplot has finished.");
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}