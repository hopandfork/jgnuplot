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

	public Label ()
	{

	}

	public Label(String text, double x, double y, RelativePosition relativePos) {
		this.x = x;
		this.y = y;
		this.text = text;
		this.relativePos = relativePos;
	}

	public void setY(double y) {
		this.y = y;
	}

	public RelativePosition getRelativePos() {
		return relativePos;
	}

	public void setRelativePos(RelativePosition relativePos) {
		this.relativePos = relativePos;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Label label = (Label) o;

		if (Double.compare(label.x, x) != 0) return false;
		if (Double.compare(label.y, y) != 0) return false;
		if (enabled != label.enabled) return false;
		if (text != null ? !text.equals(label.text) : label.text != null) return false;
		return relativePos == label.relativePos;
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + (text != null ? text.hashCode() : 0);
		result = 31 * result + (relativePos != null ? relativePos.hashCode() : 0);
		result = 31 * result + (enabled ? 1 : 0);
		return result;
	}
}
