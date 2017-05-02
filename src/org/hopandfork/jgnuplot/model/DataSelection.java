package org.hopandfork.jgnuplot.model;

/**
 * This class keeps track of the data associated with the graph.
 * 
 * @author luca
 *
 */
public abstract class DataSelection implements Plottable{
	protected int x = 0, y = 0;

	public DataSelection(int x, int y) {
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
}