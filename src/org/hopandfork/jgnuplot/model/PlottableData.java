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

import org.hopandfork.jgnuplot.model.style.GnuplotColor;
import org.hopandfork.jgnuplot.model.style.PlottingStyle;

/**
 * Abstract data item which can be added to a plot (e.g., a Function, a DataFile).
 */
public abstract class PlottableData implements Plottable {

	/** Title of this item in the plot. */
	protected String title;

	/** Enable flag for this item. */
	protected boolean enabled = true;

	/** PlottingStyle to use. */
	protected PlottingStyle style;

	/** Color to use. */
	protected GnuplotColor color;

	/** Additional style options like linecolor, pointsize a.s.o. */
	protected String addStyleOpt; // TODO we should avoid a user-defined String...

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

	public void setStyle(PlottingStyle s) {
		this.style = s;
	}

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
