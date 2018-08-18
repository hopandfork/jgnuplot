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

import java.io.IOException;
import java.util.Locale;

/**
 * This class represents a data selection for 2D plot with or without labels.
 * 
 * @author luca
 *
 */
public class DataSelection2D extends DataSelection {
	private int labels = 0;
	private boolean labelled = false;

	/**
	 * This Constructor allows to create a DataSelection of twoD graph.
	 * 
	 * @param x
	 *            data file column associated with x values.
	 * @param y
	 *            data file column associated with y values.
	 * @throws IOException 
	 */
	public DataSelection2D(int x, int y) throws IOException {
		super(x, y);
	}

	/**
	 * This Constructor allows to create a DataSelection of twoD graph with
	 * specific labels.
	 * 
	 * @param x
	 *            data file column associated with x values.
	 * @param y
	 *            data file column associated with y values.
	 * @param labels
	 *            data file column associated with labels.
	 * @throws IOException 
	 */
	public DataSelection2D(int x, int y, int labels) throws IOException {
		super(x, y);
		
		if (labels <= 0){
			throw new IOException("labels has to be greather then 0.");
		}
		
		setLabels(labels);
	}

	public int getLabels() {
		return labels;
	}

	public void setLabels(int labels) throws IOException {
		this.labels = labels;
		this.labelled = true;
	}

	public boolean isLabelled() {
		return labelled;
	}

	public void setLabelled(boolean labelled) {
		this.labelled = labelled;
	}

	public String toPlotString() {
		String s = String.format(Locale.ROOT, "($%d*%f+%f):($%d*%f+%f)", x, scaleX, shiftX, y, scaleY, shiftY);
		if (labelled) {
			s = s + ":" + labels;
		}
		return s;
	}
}