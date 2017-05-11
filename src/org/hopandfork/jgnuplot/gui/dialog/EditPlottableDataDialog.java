package org.hopandfork.jgnuplot.gui.dialog;

import java.io.IOException;

import org.hopandfork.jgnuplot.control.PlottableDataController;
import org.hopandfork.jgnuplot.model.DataFile;
import org.hopandfork.jgnuplot.model.Function;
import org.hopandfork.jgnuplot.model.PlottableData;

public class EditPlottableDataDialog extends PlottableDataDialog {
	private static final long serialVersionUID = 7768154692608038103L;

	public static final String TITLE = "Edit plottable data";

	private PlottableData plottableObject;
	
	/**
	 * This constructor is used to create a PlottableDataDialog filled with a
	 * PlottableData values, ready to be modified.
	 * 
	 * @param plottableObject
	 *            the object from which values are taken.
	 * @param controller
	 *            a PlottableDataController to update the PlottableData in case
	 *            of changes.
	 * @throws NullPointerException is thrown in case of null plottableObject. 
	 */
	public EditPlottableDataDialog(PlottableData plottableObject, PlottableDataController controller) throws IOException {
		super(TITLE, controller);
		
		/* Checks if a PlottableData is valid. */
		if (plottableObject != null){
			initFields();
			this.plottableObject = plottableObject;
		} else {
			throw new NullPointerException("plottableObject has to be not null");
		}
	}
	
	/**
	 * This method allows to set all field in the dialog to apply some changes.
	 *
	 * @param plottableObject
	 *            could be a Function or DataFile to be modified.
	 * 
	 */
	private void initFields() {

		/* Checks if plottableObject is a Function or DataFile */
		if (plottableObject instanceof Function) {
			rbFunc.setSelected(true);
			Function func = (Function) plottableObject;
			tfDataSelection.setText(func.getFunctionString());
		} else {
			rbFile.setSelected(true);
			DataFile dataFile = (DataFile) plottableObject;
			tfFileName.setText(dataFile.getFileName());
			tfPreProcess.setText(dataFile.getPreProcessProgram());
			tfDataSelection.setText(dataFile.getDataSelection().toPlotString());
		}

		tfTitle.setText(plottableObject.getTitle());

		cbStyle.setSelectedItem(plottableObject.getStyle());
	}
}
