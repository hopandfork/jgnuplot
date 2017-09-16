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
	public void updatePlot(Mode mode, String title, String xMax, String xMin, String yMax, String yMin, String zMax,
						   String zMin, String xLabel, String yLabel, String zLabel, boolean xLogScale, boolean yLogScale, boolean zLogScale) {
		Plot plot = Project.currentProject().getPlot();

		Double xmin = plot.getXmin();
		Double xmax = plot.getXmax();
		Double ymin = plot.getYmin();
		Double ymax = plot.getYmax();
		Double zmin = plot.getZmin();
		Double zmax = plot.getZmax();

		try {
			if (!xMin.isEmpty())
				xmin = Double.parseDouble(xMin);
		} catch (NumberFormatException e) {
			LOG.error("Skips invalid values");
		}
		try {
			if (!xMax.isEmpty())
				xmax = Double.parseDouble(xMax);
		} catch (NumberFormatException e) {
			LOG.error("Skips invalid values");
		}
		try {
			if (!yMin.isEmpty())
				ymin = Double.parseDouble(yMin);
		} catch (NumberFormatException e) {
			LOG.error("Skips invalid values");
		}
		try {
			if (!yMax.isEmpty())
				ymax = Double.parseDouble(yMax);
		} catch (NumberFormatException e) {
			LOG.error("Skips invalid values");
		}
		try {
			if (!zMin.isEmpty())
				zmin = Double.parseDouble(zMin);
		} catch (NumberFormatException e) {
			LOG.error("Skips invalid values");
		}
		try {
			if (!zMax.isEmpty())
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
		plot.setLogScaleX(xLogScale);
		plot.setLogScaleY(yLogScale);
		plot.setLogScaleZ(zLogScale);

		notifyObservers(plot);
	}

	public void updatePreplotScript(String preplotScript) {
		Plot plot = Project.currentProject().getPlot();
		plot.setPrePlotString(preplotScript);
		//notifyObservers(plot); TODO preview would fail if user is still digiting the commands!
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
