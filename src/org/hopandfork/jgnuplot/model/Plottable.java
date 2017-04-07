package org.hopandfork.jgnuplot.model;

/**
 * Interface implemented by everything can be represented on a plot.
 */
public interface Plottable {

	/** 
	 * Returns the string to use in Gnuplot for plotting this Plottable.
	 * @return The string to use in Gnuplot for plotting this Plottable.
	 */
	String toPlotString();
}
