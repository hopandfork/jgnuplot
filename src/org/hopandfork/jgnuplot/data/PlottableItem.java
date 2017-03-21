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

import org.hopandfork.jgnuplot.plot.GnuplotColor;
import org.hopandfork.jgnuplot.plot.PlottingStyle;

/**
 * Abstract item which can be added to a plot (e.g., a function, a dataset).
 */
public abstract class PlottableItem {

	/** Title of this item in the plot. */
	public String title;

	/** Enable flag for this item. */
	public boolean enabled = true;

	/** PlottingStyle to use. */
	public PlottingStyle style;

	/** Color to use. */
	public GnuplotColor color;

	/** Additional style options like linecolor, pointsize a.s.o. */
	public String addStyleOpt; // TODO we should avoid a user-defined String...

	public boolean isEnabled() {
		return this.enabled;
	}

	public String getTitle() {
		return title;
	}

	public PlottingStyle getStyle() {
		return this.style;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Deprecated
	public abstract String getDataString();

	/**
	 * Returns plot command string to be passed to gnuplot.
	 * @return Command string to be passed to gnuplot.
	 */
	public abstract String getPlotString();

	/**
	 * Returns a clone of this PlottableItem.
	 * @return A clone of this PlottableItem.
	 */
	public abstract PlottableItem getClone();

	public void setStyle(PlottingStyle s) {
		this.style = s;
	}

	@Deprecated
	public abstract Object[] getData();

	@Deprecated
	public abstract void setData(int i, Object value);

	@Deprecated
	public abstract void setDataString(String function);

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddStyleOpt() {
		return addStyleOpt;
	}

	public void setAddStyleOpt(String addStyleOpt) {
		this.addStyleOpt = addStyleOpt;
	}

	public GnuplotColor getColor() {
		return color;
	}

	public void setColor(GnuplotColor color) {
		this.color = color;
	}

}
