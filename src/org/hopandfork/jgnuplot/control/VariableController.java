package org.hopandfork.jgnuplot.control;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.hopandfork.jgnuplot.model.Function;
import org.hopandfork.jgnuplot.model.Plot;
import org.hopandfork.jgnuplot.model.Project;
import org.hopandfork.jgnuplot.model.StringVariable;
import org.hopandfork.jgnuplot.model.Variable;

public class VariableController {

	static private Logger LOG = Logger.getLogger(VariableController.class);

	/**
	 * This method allows to create a new Variable.
	 * 
	 * @param function
	 *            is the function target for the new Variable.
	 * @param name
	 *            of the Variable.
	 * @param value
	 *            of the Variable.
	 * 
	 * @return the new Variable.
	 * @throws IOException
	 *             if name or value is null an exception is thrown.
	 */
	public Variable addVariable(Function function, String name, String value) throws IOException {
		Plot plot = Project.currentProject().getPlot();
		function = (Function) plot.getPlottableData().get(plot.getPlottableData().indexOf(function));
		Variable var = new StringVariable();

		if (name != null && value != null) {
			var.setName(name);
			var.setValue(value);

			function.addVariable(var);
		} else {
			throw new IOException("Name or/and Value are null, please set a correct value");
		}
		return var;
	}
}
