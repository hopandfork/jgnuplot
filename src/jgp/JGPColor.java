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

import java.awt.Color;
import java.awt.color.ColorSpace;

public class JGPColor extends Color {

	public JGPColor(ColorSpace cspace, float[] components, float alpha) {
		super(cspace, components, alpha);
		// TODO Auto-generated constructor stub
	}

	public JGPColor(float r, float g, float b, float a) {
		super(r, g, b, a);
		// TODO Auto-generated constructor stub
	}

	public JGPColor(float r, float g, float b) {
		super(r, g, b);
		// TODO Auto-generated constructor stub
	}

	public JGPColor(int rgba, boolean hasalpha) {
		super(rgba, hasalpha);
		// TODO Auto-generated constructor stub
	}

	public JGPColor(int r, int g, int b, int a) {
		super(r, g, b, a);
		// TODO Auto-generated constructor stub
	}

	public JGPColor(int r, int g, int b) {
		super(r, g, b);
		// TODO Auto-generated constructor stub
	}

	public JGPColor(int rgb) {
		super(rgb);
		// TODO Auto-generated constructor stub
	}

	public JGPColor(Color c) {
		super(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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

	
	public static JGPColor parseHexString(String hex){
		String s = "";
		int r,g,b;
		r = Integer.parseInt(hex.substring(0,2),16);
		g = Integer.parseInt(hex.substring(2,4),16);
		b = Integer.parseInt(hex.substring(4,6),16);

		
		JGPColor c = new JGPColor(r,g,b);
		return c;
	}
}
