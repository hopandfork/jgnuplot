package org.hopandfork.jgnuplot.runtime.terminal;

import java.io.File;

public class SvgTerminal extends Terminal {

	private int fontSize;
	private String fontName = null;
	private int width;
	private int height;

	public SvgTerminal(int width, int height)
	{
		this(width, height, null);
	}

	public SvgTerminal(int width, int height, File outputFile)
	{
		super(outputFile);
		this.width = width;
		this.height = height;
	}

	@Override
	protected String getTerminalString() {
		return String.format("set terminal svg size %d,%d enhanced %s\n", width, height, fontString());
	}

	private String fontString()
	{
		if (fontName != null) {
			return String.format("fname '%s' fsize %d", fontName, fontSize);
		}

		return "";
	}

	public void setFont (String name, int size)
	{
		this.fontName = name;
		this.fontSize = size;
	}
}
