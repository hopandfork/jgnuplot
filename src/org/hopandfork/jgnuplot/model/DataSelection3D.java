package org.hopandfork.jgnuplot.model;

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
	 */
	public DataSelection3D(int x, int y, int z) {
		super(x, y);
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