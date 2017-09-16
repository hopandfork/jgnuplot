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
 * This class represents a data selection for 3D plot.
 * 
 * @author luca
 *
 */
public class DataSelection3D extends DataSelection {
	private int z = 0;
	private double shiftZ = 0.0;
	private double scaleZ = 1.0;

	/**
	 * This Constructor allows to create a DataSelection of twoD graph with
	 * labels associated.
	 * 
	 * @param x
	 *            data file column associated with x values.
	 * @param y
	 *            data file column associated with y values.
	 * @param z
	 *            data file column associated with z values.
	 * @throws IOException 
	 */
	public DataSelection3D(int x, int y, int z) throws IOException {
		super(x, y);
		if(z <= 0){
			throw new IOException("z has to be greather then 0.");
		}
		this.z = z;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public double getShiftZ() {
		return shiftZ;
	}

	public void setShiftZ(double shiftZ) {
		this.shiftZ = shiftZ;
	}

	public double getScaleZ() {
		return scaleZ;
	}

	public void setScaleZ(double scaleZ) {
		this.scaleZ = scaleZ;
	}

	public String toPlotString() {
		return String.format(Locale.ROOT, "($%d*%f+%f):($%d*%f+%f):($%d*%f+%f)", x, scaleX, shiftX, y, scaleY, shiftY, z, scaleZ, shiftZ);
	}
}