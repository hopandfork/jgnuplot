package org.hopandfork.jgnuplot.control;

import org.apache.log4j.Logger;
import org.hopandfork.jgnuplot.model.*;
import org.hopandfork.jgnuplot.model.style.PlottingStyle;

import java.util.Collection;
import java.util.List;
import java.util.Observable;


public class PlottableDataController extends Observable {


	static private final Logger LOG = Logger.getLogger(PlottableDataController.class);

	public Function addFunction(String expression, String title, PlottingStyle style, List<Constant> constants) {
		Plot plot = Project.currentProject().getPlot();

		Function function = new Function();
		function.setFunctionString(expression);
		function.setTitle(title);
		function.setStyle(style);
		function.setConstants(constants);

		plot.addPlottableData(function);

		notifyObservers(function);
		return function;
	}

	public DataFile addDataFile(String filename, String title, PlottingStyle style, DataSelection dataSelection) {
		Plot plot = Project.currentProject().getPlot();

		DataFile dataFile = new DataFile();
		dataFile.setFileName(filename);
		dataFile.setTitle(title);
		dataFile.setStyle(style);
		dataFile.setDataSelection(dataSelection);

		plot.addPlottableData(dataFile);

		notifyObservers(dataFile);
		return dataFile;
	}

	public Function updateFunction(Function function, String expression, String title, PlottingStyle style, List<Constant> constants) {
		
		/* Updates function */
		function.setFunctionString(expression);
		function.setTitle(title);
		function.setStyle(style);
		function.setConstants(constants);
		
		notifyObservers(function);
		return function;
	}

	public DataFile updateDataFile(DataFile dataFile, String filename, String title, PlottingStyle style, DataSelection dataSelection) {
	
	/* Updates dataFile */
		dataFile.setFileName(filename);
		dataFile.setTitle(title);
		dataFile.setStyle(style);
		dataFile.setDataSelection(dataSelection);

		notifyObservers(dataFile);
		return dataFile;
	}

	public List<PlottableData> getPlottableData() {
		Plot plot = Project.currentProject().getPlot();
		return plot.getPlottableData();
	}

	public void delete(PlottableData plottableData) {
		Plot plot = Project.currentProject().getPlot();
		plot.deletePlottableData(plottableData);
		notifyObservers(plottableData);
	}

	public void deleteAll() {
		Plot plot = Project.currentProject().getPlot();
		plot.deleteAllPlottableData();
		notifyObservers(null);
	}

	@Override
	public void notifyObservers(Object o) {
		this.setChanged();
		super.notifyObservers(o);
		this.clearChanged();
	}

	public void moveUp (PlottableData plottableData) {
		Plot plot = Project.currentProject().getPlot();
		List<PlottableData> list = plot.getPlottableData();

		int position = list.indexOf(plottableData);
		if (position < 0) {
			LOG.warn("Trying to moveUp() a plottable data which is not in the plot!");
			return;
		}

		if (position == 0)
			return;

		list.remove(position);
		list.add(position-1, plottableData);
		notifyObservers(plottableData);
	}

	public void moveDown (PlottableData plottableData) {
		Plot plot = Project.currentProject().getPlot();
		List<PlottableData> list = plot.getPlottableData();

		int position = list.indexOf(plottableData);
		if (position < 0) {
			LOG.warn("Trying to moveDown() a plottable data which is not in the plot!");
			return;
		}

		if (position == list.size()-1)
			return;

		list.remove(position);
		list.add(position+1, plottableData);
		notifyObservers(plottableData);
	}

}
