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

public class Label implements Plottable {
	private double x;
	
	private double y;
	
	private String text;
	
	private RelativePosition relativePos = RelativePosition.FIRST;
	
	private boolean enabled = true;
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String toPlotString(){
		return "set label \"" + text + "\" at " + relativePos.name().toLowerCase() + " " + x + "," + y + "\n";
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public Object[] getData(){
		Object data[] = new Object[5];
		data[0] = this.text;
		data[1] = this.x;
		data[2] = this.y;
		data[3] = this.relativePos;
		data[4] = this.enabled;
		return data;
	}

	public void setData(int i, Object value){
		if (i == 0)			text = (String)value ;
		else if (i == 1)	x = ((Double)value).doubleValue();
		else if (i == 2)	y = ((Double)value).doubleValue();
		else if (i == 3)	relativePos = (RelativePosition)value;
		else if (i == 4)	enabled = ((Boolean) value).booleanValue();
	}

	public RelativePosition getRelativePos() {
		return relativePos;
	}

	public void setRelativePos(RelativePosition relativePos) {
		this.relativePos = relativePos;
	}
}
