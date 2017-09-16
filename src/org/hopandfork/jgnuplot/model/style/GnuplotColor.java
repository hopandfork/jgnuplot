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

package org.hopandfork.jgnuplot.model.style;

import java.awt.Color;
import java.awt.color.ColorSpace;

/**
 * Just a Java Color with some extra utilities.
 */
public class GnuplotColor extends Color {

	public GnuplotColor(ColorSpace cspace, float[] components, float alpha) {
		super(cspace, components, alpha);
	}

	public GnuplotColor(float r, float g, float b, float a) {
		super(r, g, b, a);
	}

	public GnuplotColor(float r, float g, float b) {
		super(r, g, b);
	}

	public GnuplotColor(int rgba, boolean hasalpha) {
		super(rgba, hasalpha);
	}

	public GnuplotColor(int r, int g, int b, int a) {
		super(r, g, b, a);
	}

	public GnuplotColor(int r, int g, int b) {
		super(r, g, b);
	}

	public GnuplotColor(int rgb) {
		super(rgb);
	}

	public GnuplotColor(Color c) {
		super(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
	}
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Returns HEX representation of this color.
	 * @return HEX representation of this color.
	 */
	public String getHexString(){
		String s = "";
		String r,g,b;
		r = Integer.toHexString(this.getRed());
		g = Integer.toHexString(this.getGreen());
		b = Integer.toHexString(this.getBlue());
		if (r.length() == 1) r = "0" + r;
		if (g.length() == 1) g = "0" + g;
		if (b.length() == 1) b = "0" + b;
		s += r + "" + g + "" + b;
		return s;
	}

	
	/**
	 * Returns a GnuplotColor given its HEX representation.
	 * @param hex HEX representation.
	 * @return A GnuplotColor given its HEX representation.
	 */
	public static GnuplotColor parseHexString(String hex){
		int r,g,b;
		r = Integer.parseInt(hex.substring(0,2),16);
		g = Integer.parseInt(hex.substring(2,4),16);
		b = Integer.parseInt(hex.substring(4,6),16);

		
		GnuplotColor c = new GnuplotColor(r,g,b);
		return c;
	}
}
