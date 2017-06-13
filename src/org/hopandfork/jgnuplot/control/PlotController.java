package org.hopandfork.jgnuplot.control;

import java.util.Observable;

import org.apache.log4j.Logger;
import org.hopandfork.jgnuplot.model.Plot;
import org.hopandfork.jgnuplot.model.Plot.Mode;
import org.hopandfork.jgnuplot.model.Project;

public class PlotController extends Observable {

	private static Logger LOG = Logger.getLogger(PlotController.class);

	/**
	 * This method allows to update an existing plot.
	 */
	public Plot updatePlot(Mode mode, String title, String xMax, String xMin, String yMax, String yMin, String zMax,
			String zMin, String xLabel, String yLabel, String zLabel) {
		Plot plot = Project.currentProject().getPlot();

		Double xmin = plot.getXmin();
		Double xmax = plot.getXmax();
		Double ymin = plot.getYmin();
		Double ymax = plot.getYmax();
		Double zmin = plot.getZmin();
		Double zmax = plot.getZmax();
		try {
			xmin = Double.parseDouble(xMin);
		} catch (NumberFormatException e) {
			LOG.error("Skips invalid values");
		}
		try {
			xmax = Double.parseDouble(xMax);
		} catch (NumberFormatException e) {
			LOG.error("Skips invalid values");
		}
		try {
			ymin = Double.parseDouble(yMin);
		} catch (NumberFormatException e) {
			LOG.error("Skips invalid values");
		}
		try {
			ymax = Double.parseDouble(yMax);
		} catch (NumberFormatException e) {
			LOG.error("Skips invalid values");
		}
		try {
			zmin = Double.parseDouble(zMin);
		} catch (NumberFormatException e) {
			LOG.error("Skips invalid values");
		}
		try {
			zmax = Double.parseDouble(zMax);
		} catch (NumberFormatException e) {
			LOG.error("Skips invalid values");
		}

		plot.setTitle(title);
		if (mode != null) {
			plot.setMode(mode);
		}
		plot.setXmin(xmin);
		plot.setXmax(xmax);
		plot.setXlabel(xLabel);
		plot.setYmin(ymin);
		plot.setYmax(ymax);
		plot.setYlabel(yLabel);
		plot.setZmin(zmin);
		plot.setZmax(zmax);
		plot.setZlabel(zLabel);

		notifyObservers(plot);
		return plot;
	}

	public Plot getCurrent() {
		return Project.currentProject().getPlot();
	}

	@Override
	public void notifyObservers(Object o) {
		this.setChanged();
		super.notifyObservers(o);
		this.clearChanged();
	}
}
