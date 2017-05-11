package org.hopandfork.jgnuplot.gui.dialog;

import org.hopandfork.jgnuplot.control.PlottableDataController;

public class AddPlottableDataDialog extends PlottableDataDialog {
	private static final long serialVersionUID = 3629675300426530447L;
	
	public static final String TITLE = "Add plottable data";
	
	/**
	 * This constructor allows to create an empty PlottableDataDialog to add a
	 * new Function or DataFile.
	 * 
	 * @param controller a PlottableDataController to add new PlottableData.
	 * 
	 */
	public AddPlottableDataDialog(PlottableDataController controller) {
		super(TITLE, controller);
	}
}
