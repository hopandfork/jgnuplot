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
		String s = String.format(Locale.ROOT, "($%d*%f+%f):($%d*%f+%f)", x, scaleX, shiftX, y, scaleY, shiftY);
		if (labelled) {
			s = s + ":" + labels;
		}
		return s;
	}
}