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

package org.hopandfork.jgnuplot;


/**
 * A GPVariable renders a actual GNUplot variable. Befor plotting the
 * variable will be set in GNUplot 
 * 		VARIABLENAME = VALUE
 * 
 * @author mxhf
 *
 */
public class GnuplotVariable extends Variable {
	
	public String getPlotString() {
		return getName() + "=" + getValue();
	}
	
	public Object[] getData() {
		Object data[] = new Object[4];
		data[0] = Variable.Type.GNUPLOT;
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

	public Type getType() {
		return Variable.Type.GNUPLOT;
	}


}