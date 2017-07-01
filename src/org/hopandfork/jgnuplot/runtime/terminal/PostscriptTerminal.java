package org.hopandfork.jgnuplot.runtime.terminal;

import java.io.File;

public class PostscriptTerminal extends Terminal {

	private int fontSize;
	private String fontName = null;
	private int width;
	private int height;

	public PostscriptTerminal(int width, int height)
	{
		this(width, height, null);
	}

	public PostscriptTerminal(int widthPixels, int heightPixels, File outputFile)
	{
		super(outputFile);

		/* Postscript terminal accepts size in inches (1/100 of pixels size) */
		this.width = widthPixels/100;
		this.height = heightPixels/100;
	}

	@Override
	protected String getTerminalString() {
		return String.format("set terminal postscript eps size %d,%d enhanced color %s\n", width, height, fontString());
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
