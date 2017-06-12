package org.hopandfork.jgnuplot.control;

import java.util.Observable;
import java.util.Observer;

import org.hopandfork.jgnuplot.model.Plot;
import org.hopandfork.jgnuplot.model.Project;

public class PlotController extends Observable {
	
	/**
	 * This method allows to update an existing plot.
	 */
	public Plot updatePlot(String title){
		Plot plot = Project.currentProject().getPlot();
		
		plot.setTitle(title);
		
		return plot;
	}
	
	public Plot getCurrent(){
		return Project.currentProject().getPlot();
	}
	
	@Override
	public void notifyObservers(Object o) {
		this.setChanged();
		super.notifyObservers(o);
		this.clearChanged();
	}
}
