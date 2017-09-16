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
