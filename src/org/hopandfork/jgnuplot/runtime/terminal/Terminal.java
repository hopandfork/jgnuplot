package org.hopandfork.jgnuplot.runtime.terminal;

import org.hopandfork.jgnuplot.model.Plottable;

import java.io.File;

/**
 * Gnuplot terminal.
 */
public abstract class Terminal implements Plottable {

	private File outputFile = null;

	public Terminal()
	{
	}

	public Terminal (File outputFile)
	{
		this.outputFile = outputFile;
	}

	public File getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}


	abstract protected String getTerminalString();

	private String getOutputString()
	{
		if (outputFile != null)
			return String.format("set output \"%s\"\n", outputFile.getAbsolutePath());

		return "";
	}

	@Override
	public String toPlotString() {
		return getTerminalString() + getOutputString();
	}
}
