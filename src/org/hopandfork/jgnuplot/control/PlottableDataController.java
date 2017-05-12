package org.hopandfork.jgnuplot.control;

import org.hopandfork.jgnuplot.model.*;
import org.hopandfork.jgnuplot.model.style.PlottingStyle;

public class PlottableDataController {


	public Function addFunction (String expression, String title, PlottingStyle style)
	{
		Plot plot = Project.currentProject().getPlot();

		Function function = new Function();
		function.setFunctionString(expression);
		function.setTitle(title);
		function.setStyle(style);

		plot.addPlottableData(function);
		return function;
	}


	public DataFile addDataFile (String filename, String title, PlottingStyle style, DataSelection dataSelection, String preProcessProgram)
	{
		Plot plot = Project.currentProject().getPlot();

		DataFile dataFile = new DataFile();
		dataFile.setFileName(filename);
		dataFile.setTitle(title);
		dataFile.setStyle(style);
		dataFile.setDataSelection(dataSelection);
		dataFile.setPreProcessProgram(preProcessProgram);
		
		
		plot.addPlottableData(dataFile);
		return dataFile;
	}

	// TODO edit

	// TODO delete


}
