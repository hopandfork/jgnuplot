package org.hopandfork.jgnuplot.model;

import java.io.IOException;

/**
 * This class keeps track of the data associated with the plot.
 * 
 * @author luca
 *
 */
public abstract class DataSelection implements Plottable {

	protected int x = 0;
	protected int y = 0;

	protected double shiftX = 0.0;
	protected double shiftY = 0.0;

	protected double scaleX = 1.0;
	protected double scaleY = 1.0;

	/**
	 * This method allows to create a DataSelection object with x and y
	 * different from 0.
	 * 
	 * @param x
	 *            has to be the data column represented on x axis, > 0.
	 * @param y
	 *            has to be the data column represented on y axis, > 0.
	 * @throws IOException
	 *             if x or y is lower the 1.
	 */
	public DataSelection(int x, int y) throws IOException {
		if (x <= 0 || y <= 0) {
			throw new IOException("x and y have to be greather then 0.");
		}

		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getShiftX() {
		return shiftX;
	}

	public void setShiftX(double shiftX) {
		this.shiftX = shiftX;
	}

	public double getShiftY() {
		return shiftY;
	}

	public void setShiftY(double shiftY) {
		this.shiftY = shiftY;
	}

	public double getScaleX() {
		return scaleX;
	}

	public void setScaleX(double scaleX) {
		this.scaleX = scaleX;
	}

	public double getScaleY() {
		return scaleY;
	}

	public void setScaleY(double scaleY) {
		this.scaleY = scaleY;
	}
}