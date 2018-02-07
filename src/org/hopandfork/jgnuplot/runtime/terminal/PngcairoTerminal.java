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
