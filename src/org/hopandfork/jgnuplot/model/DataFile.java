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

import java.awt.Color;
import java.io.IOException;

import org.hopandfork.jgnuplot.model.style.GnuplotColor;
import org.hopandfork.jgnuplot.model.style.PlottingStyle;
import org.hopandfork.jgnuplot.runtime.PreProcessPlugin;
import org.hopandfork.jgnuplot.utility.TempFile;

public class DataFile extends PlottableData {

	/** Expression to be passed to gnuplot for selecting input data (e.g. 'using 1:2') */
	@Deprecated
	private String dataSelectionString;
	
	/** DataFile filename. */
	private String fileName;

	/** Last modified flag/timestamp (?) */
	private long lastChanged = 0; // TODO check this field

	/** Optional plugin for preprocessing. */
	private PreProcessPlugin preProcessPlugin;

	/** Optional external command for preprocessing. */
	private String preProcessProgram;

	public long getLastChanged() {
		return lastChanged;
	}

	public void setLastChanged(long lastChanged) {
		this.lastChanged = lastChanged;
	}

	@Deprecated
	public Object[] getData() {
		Object data[] = new Object[8];
		data[0] = this.fileName;
		data[1] = this.dataSelectionString;
		data[2] = this.title;
		data[3] = this.color;
		data[4] = this.style;
		if (this.addStyleOpt == null)
			addStyleOpt = "";
		data[5] = this.addStyleOpt;
		data[6] = this.enabled;
		data[7] = this.preProcessProgram;
		return data;
	}

	@Deprecated
	public void setData(int i, Object value) {
		if (i == 0)
			fileName = (String) value;
		else if (i == 1)
			dataSelectionString = (String) value;
		else if (i == 2)
			title = (String) value;
		else if (i == 3)
			color = new GnuplotColor((Color) value);
		else if (i == 4)
			style = (PlottingStyle) value;
		else if (i == 5)
			addStyleOpt = (String) value;
		else if (i == 6)
			enabled = ((Boolean) value).booleanValue();
		else if (i == 7)
			preProcessProgram = (String) value;
	}

	public DataFile(String fileName, String dataString) {
		super();
		this.dataSelectionString = dataString;
		this.fileName = fileName;
	}

	public DataFile(String fileName, String dataString, String title) {
		super();
		this.dataSelectionString = dataString;
		this.fileName = fileName;
		this.title = title;
	}

	public DataFile(String fileName, String dataString, String title, PlottingStyle style) {
		super();
		this.dataSelectionString = dataString;
		this.fileName = fileName;
		this.title = title;
		this.style = style;
	}

	public DataFile() {
	}

	public String toPlotString() {

		String s = "";

		if (preProcessProgram != null && !preProcessProgram.trim().equals("")
				&& !preProcessProgram.trim().equals("null")) {
			// call prepocess program
			String tmpFileName = TempFile.getTempFileName();
			callPreProcessProgram(preProcessProgram.trim(), fileName, tmpFileName);
			// and plot the tmp output
			s += "'" + tmpFileName + "'";
		} else {
			s += "'" + fileName + "'";
		}

		s += " using " + dataSelectionString + " ";

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

	public void callPreProcessProgram(String program, String inputFileName, String outputFileName) {
		// TODO poor implementation without feedback (by M.H.F.)
		String cmdline = "";
		cmdline = program.replaceAll("\\$if", inputFileName);
		cmdline = cmdline.replaceAll("\\$of", outputFileName);

		System.out.println("Calling preprocess program: " + cmdline);
		try {
			Process p = Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", cmdline });
			p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("done");

	}

	@Deprecated
	public String getDataString() {
		return dataSelectionString;
	}

	public String getDataSelectionString() {
		return dataSelectionString;
	}

	public void setDataSelectionString(String dataSelectionString) {
		this.dataSelectionString = dataSelectionString;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String s) {
		this.fileName = s;
	};

	@Deprecated
	public void setDataString(String function) {
		this.dataSelectionString = function;
	}

	public PreProcessPlugin getPreProcessPlugin() {
		return preProcessPlugin;
	}

	public void setPreProcessPlugin(PreProcessPlugin preProcessPlugin) {
		this.preProcessPlugin = preProcessPlugin;
	}

	public String getPreProcessProgram() {
		return preProcessProgram;
	}

	public void setPreProcessProgram(String preProcessProgram) {
		if (preProcessProgram.equals("null"))
			preProcessProgram = null;
		else
			this.preProcessProgram = preProcessProgram;
	}

	@Override
	public PlottableData getClone() {
		DataFile ds = new DataFile();
		ds.fileName = fileName;
		ds.dataSelectionString = dataSelectionString;
		ds.title = title;
		ds.color = color;
		ds.style = style;
		ds.addStyleOpt = addStyleOpt;
		ds.enabled = enabled;
		ds.preProcessProgram = preProcessProgram;
		return ds;
	}

}
