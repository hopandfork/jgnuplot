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

import org.hopandfork.jgnuplot.GnuplotColor;
import org.hopandfork.jgnuplot.PlottingStyle;



public class Function extends PlottableItem{
	private String function;


	public String getPlotString() {
		String s = function;
		if (style != null || addStyleOpt != null || color != null){
			s += " with ";
			if (style != null) s +=  style.name() + "  ";
			if (addStyleOpt != null)s +=  addStyleOpt + "  ";
			if (color != null){

				s +=  "lc rgb '#"  + color.getHexString() + "'";
			}
			if (title != null && !title.equals("")) s += "title '" + title + "'  ";
		}
		return s;
	}

	public String getFileName() {
		return "FUNCTION";
	}
	
	public String getDataString() {
		return function;
	}

	public void setFileName(String s){};
	
	public void setDataString(String function) {
		this.function = function;
	}


	public Object[] getData() {
		Object data[] = new Object[8];
		data[0] = getFileName();
		data[1] = this.function;
		data[2] = this.title;
		data[3] = this.color;
		data[4] = this.style;
		data[5] = this.addStyleOpt;
		data[6] = this.enabled;
		data[7] = "";
		return data;	}
	


	public void setData(int i, Object value) {
		if (i == 1)	function = (String)value;
		else if (i == 2)	title = (String) value;
		else if (i == 3)	color = new GnuplotColor((Color) value );
		else if (i == 4)	style = (PlottingStyle) value;
		else if (i == 5)	addStyleOpt = (String)value;
		else if (i == 6)	enabled = ((Boolean) value).booleanValue();
	}

	public String getPreProcessProgram() {
		// TODO Auto-generated method stub
		return "";
	}

	public Function getClone() {
		Function f = new Function();
		f.function = function ;
		f.title = title;
		f.style = style;
		f.color = color;
		f.addStyleOpt = addStyleOpt;
		f.enabled = enabled;
		// TODO Auto-generated method stub
		return f;
	}

	public void setPreProcessProgram(String textContent) {
		//does nothing
		
	}

}
