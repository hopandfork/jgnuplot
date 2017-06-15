/*
 * JGNUplot is a GUI for gnuplot (http://www.gnuplot.info/)
 * The GUI is build on JAVA wrappers for gnuplot alos provided in this package.
 * 
 * Copyright (C) 2006  Maximilian H. Fabricius 
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.hopandfork.jgnuplot.model;

public class DataFile extends PlottableData {

	/** Columns selection. */
	private DataSelection dataSelection;
	
	/** DataFile filename. */
	private String fileName;

	public String toPlotString() {

		String s = "";

		if (dataSelection == null || fileName == null) {
			System.err.println("Nothing to plot!");
			return s;
		}

		s += "'" + fileName + "'";
		s += " using " + dataSelection.toPlotString() + " ";

		if (style != null || addStyleOpt != null || color != null) {
			s += " with ";
			if (style != null)
				s += style.name() + "  ";
			if (addStyleOpt != null)
				s += addStyleOpt + "  ";
			if (color != null) {

				s += "lc rgb '#" + color.getHexString() + "'";
			}
		}

		if (title != null && !title.trim().equals(""))
			s += " title \"" + title + " \" ";

		return s;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String s) {
		this.fileName = s;
	};

	public DataSelection getDataSelection() {
		return dataSelection;
	}

	public void setDataSelection(DataSelection dataSelection) {
		this.dataSelection = dataSelection;
	}
}
