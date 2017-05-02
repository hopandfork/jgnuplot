package org.hopandfork.jgnuplot.model;

/**
 * This class represents a data selection for 2D graph with or without labels.
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
	 */
	public DataSelection2D(int x, int y) {
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
	 */
	public DataSelection2D(int x, int y, int labels) {
		super(x, y);
		this.labels = labels;
		labelled = true;
	}

	public int getLabels() {
		return labels;
	}

	public void setLabels(int labels) {
		this.labels = labels;
		labelled = true;
	}

	public boolean isLabelled() {
		return labelled;
	}

	public void setLabelled(boolean labelled) {
		this.labelled = labelled;
	}

	public String toPlotString() {
		if (labelled) {
			return x + ":" + y + ":" + labels;
		} else {
			return x + ":" + y;
		}
	}
}