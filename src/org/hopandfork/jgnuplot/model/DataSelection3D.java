package org.hopandfork.jgnuplot.model;

/**
 * This class represents a data selection for 3D graph.
 * 
 * @author luca
 *
 */
public class DataSelection3D extends DataSelection {
	private int z = 0;

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

	public String toPlotString() {
		return x + ":" + y + ":" + z;
	}
}