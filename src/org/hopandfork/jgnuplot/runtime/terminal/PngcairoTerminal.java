package org.hopandfork.jgnuplot.runtime.terminal;

import java.io.File;

public class PngcairoTerminal extends Terminal {

	private int fontSize;
	private String fontName = null;
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
		return String.format("set terminal pngcairo size %d,%d enhanced %s\n", width, height, fontString());
	}

	private String fontString()
	{
		if (fontName != null) {
			return String.format("font \"%s, %d\"", fontName, fontSize);
		}

		return "";
	}

	public void setFont (String name, int size)
	{
		this.fontName = name;
		this.fontSize = size;
	}
}
