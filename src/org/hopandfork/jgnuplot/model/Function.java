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

import java.util.ArrayList;
import java.util.List;

public class Function extends PlottableData implements Cloneable{

	/** Expression for the function. */
	private String functionString;

	private List<Constant> constants = new ArrayList<>();

	public String getFunctionString() {
		return functionString;
	}

	public void setFunctionString(String functionString) {
		this.functionString = functionString;
	}

	@Override
	public String toPlotString() {
		String s = "" + functionString;
		
		if (style != null || addStyleOpt != null || color != null) {
			s += " with ";
			if (style != null)
				s += style.name() + "  ";
			if (addStyleOpt != null)
				s += addStyleOpt + "  ";
			if (color != null) {

				s += "lc rgb '#" + color.getHexString() + "'";
			}
			if (title != null && !title.equals(""))
				s += "title '" + title + "'  ";
		}
		
		return s;
	}

	/**
	 * This method allows to add a new Variable to the function.
	 * 
	 * @param var
	 *            is the new Variable.
	 */
	public void addConstant(Constant constant) {
		constants.add(constant);
	}

	public List<Constant> getConstants() {
		return constants;
	}

	public void setConstants(List<Constant> constants) {
		this.constants = constants;
	}
	
	public void rmConstant(Constant constant){
		constants.remove(constant);
	}
	
	public Object clone() {
		Function function = new Function();
		
		function.setAddStyleOpt(this.addStyleOpt);
		function.setColor(color);
		function.setConstants((ArrayList<Constant>)((ArrayList<Constant>)constants).clone());
		function.setEnabled(enabled);
		function.setFunctionString(functionString);
		function.setStyle(this.style);
		function.setTitle(title);
		
		return function;
	}
}
