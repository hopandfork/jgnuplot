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

package jgp;

/**
 * A string variable is not a actual varaible. It is rather a constant which
 * is pasted into the plot string prior to issuing it to GNUplot.
 * 
 * All string variable start of with a "$".
 * Each encounter of $VARIABLE_NAME in the plotstring will be replaced with
 * the value.
 * 
 * @author mxhf
 *
 */
public class JGPStringVariable extends JGPVariable {
	public String apply(String plotString){
		
		return plotString.replace("$" + getName(), getValue());
	}

	public Object[] getData() {
		Object data[] = new Object[4];
		data[0] = JGPVariable.Type.STRING;
		data[1] = getName();
		data[2] = getValue();
		data[3] = isActive();
		
		return data;
	}

	public void setData(int i, Object value) {
		if (i == 1)	setName( (String)value );
		else if (i == 2)	setValue( (String)value );
		else if (i == 3)	setActive( (Boolean)value );
	}

	@Override
	public Type getType() {
		return JGPVariable.Type.STRING;
	}

}
