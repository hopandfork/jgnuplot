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


package org.hopandfork.jgnuplot.data;

import java.awt.Color;
import java.io.IOException;

import org.hopandfork.jgnuplot.GnuplotColor;
import org.hopandfork.jgnuplot.GnuplotExecutor;
import org.hopandfork.jgnuplot.PlotStyle;
import org.hopandfork.jgnuplot.PreProcessPlugin;

public class DataSet extends PlottableItem {
	
	private String dataString;
	
	private String fileName;
	
	private long lastChanged = 0;
	
	private PreProcessPlugin preProcessPlugin;
	
	private String preProcessProgram;
	
	public long getLastChanged() {
		return lastChanged;
	}

	public void setLastChanged(long lastChanged) {
		this.lastChanged = lastChanged;
	}

	public Object[] getData(){
		Object data[] = new Object[8];
		data[0] = this.fileName;
		data[1] = this.dataString;
		data[2] = this.title;
		data[3] = this.color;
		data[4] = this.style;
		if (this.addStyleOpt == null) addStyleOpt = "";
		data[5] = this.addStyleOpt;
		data[6] = this.doPlot;
		data[7] = this.preProcessProgram;
		return data;
	}

	public void setData(int i, Object value){
		if (i == 0)			fileName = (String) value ;
		else if (i == 1)	dataString = (String) value;
		else if (i == 2)	title = (String) value;
		else if (i == 3)	color = new GnuplotColor((Color) value );
		else if (i == 4)	style = (PlotStyle) value;
		else if (i == 5)	addStyleOpt = (String) value;
		else if (i == 6)	doPlot = ((Boolean) value).booleanValue();
		else if (i == 7)	preProcessProgram = (String)value;
	}

	public DataSet(String fileName, String dataString) {
		super();
		// TODO Auto-generated constructor stub
		this.dataString = dataString;
		this.fileName = fileName;
	}

	public DataSet(String fileName, String dataString,  String title) {
		super();
		// TODO Auto-generated constructor stub
		this.dataString = dataString;
		this.fileName = fileName;
		this.title = title;
	}

	public DataSet(String fileName, String dataString, String title, PlotStyle style) {
		super();
		// TODO Auto-generated constructor stub
		this.dataString = dataString;
		this.fileName = fileName;
		this.title = title;
		this.style = style;
	}
	
	public DataSet() {
	}

	public String getPlotString(){
		
		String s = "";
		
		if ( preProcessProgram != null && !preProcessProgram.trim().equals("") && !preProcessProgram.trim().equals("null")){
			//call prepocess program
			String tmpFileName = GnuplotExecutor.getTempFileName();
			callPreProcessProgram(preProcessProgram.trim(), fileName, tmpFileName);
			//and plot the tmp output
			s += "'" + tmpFileName  + "'";
		} else {
			s += "'" + fileName + "'";
		}
		
		s += " using " + dataString + " ";
		
		if (style != null || addStyleOpt != null || color != null){
			s += " with ";
			if (style != null) s +=  style.name() + "  ";
			if (addStyleOpt != null)s +=  addStyleOpt + "  ";
			if (color != null){

				s +=  "lc rgb '#"  + color.getHexString() + "'";
			}
		}


		if (title != null && !title.trim().equals(""))
			s += " title \""+ title + " \" ";
			
		return s;
	}
	
	public void callPreProcessProgram(String program, String inputFileName, String outputFileName){
		//poor implementation without feedback
		String cmdline = "";
		cmdline = program.replaceAll("\\$if", inputFileName);
		cmdline = cmdline.replaceAll("\\$of", outputFileName);
		
		System.out.println("Calling preprocess program: " + cmdline);
	    try {
			Process  p = Runtime.getRuntime().exec(new String[] { "/bin/sh","-c",cmdline });
			p.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("done");

	}

	public String getDataString() {
		return dataString;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String s){
		this.fileName = s;
	};
	
	public void setDataString(String function) {
		this.dataString = function;
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
		if ( preProcessProgram.equals("null") ) preProcessProgram = null;
		else this.preProcessProgram = preProcessProgram;
	}

	public PlottableItem getClone() {
		DataSet ds = new DataSet();
		ds.fileName = fileName ;
		ds.dataString = dataString;
		ds.title = title;
		ds.color = color;
		ds.style = style;
		ds.addStyleOpt = addStyleOpt;
		ds.doPlot = doPlot;
		ds.preProcessProgram = preProcessProgram;
		// TODO Auto-generated method stub
		return ds;
	}
	
	
	
}
