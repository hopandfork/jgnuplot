package org.hopandfork.jgnuplot.runtime.terminal;

import java.io.File;

public class PngcairoTerminal extends Terminal {

	private int width;
	private int height;

	public PngcairoTerminal (int width, int height)
	{
		this(width, height, null);
	}

	public PngcairoTerminal (int width, int height, File outputFile)
	{
		super(outputFile);
		this.width = width;
		this.height = height;
	}

	@Override
	protected String getTerminalString() {
		return String.format("set terminal pngcairo size %d,%d\n", width, height);
	}
}
