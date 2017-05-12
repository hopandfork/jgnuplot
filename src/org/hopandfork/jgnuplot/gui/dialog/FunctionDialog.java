package org.hopandfork.jgnuplot.gui.dialog;

import java.io.IOException;

import org.hopandfork.jgnuplot.control.PlottableDataController;
import org.hopandfork.jgnuplot.model.Function;

public class FunctionDialog extends PlottableDataDialog{
	private static final long serialVersionUID = 7768154692608038103L;

	public static final String TITLE = "Function";

	
	
	/**
	 * This constructor allows to create an empty FunctionDialog.
	 * 
	 * @param controller allows to add a new Function.
	 */
	public FunctionDialog(PlottableDataController controller) {
		this.controller = controller;

		//add(createMainPanel());

		this.setTitle(TITLE);
		this.pack();
		this.setModal(true);
	}
	
	/**
	 * This constructor is used to create a FunctionDialog filled with a
	 * Function values, ready to be modified.
	 * 
	 * @param plottableObject
	 *            the object from which values are taken.
	 * @param controller
	 *            a PlottableDataController to update the PlottableData in case
	 *            of changes.
	 * @throws NullPointerException is thrown in case of null plottableObject. 
	 */
	public FunctionDialog(Function function, PlottableDataController controller) throws IOException {
		
		/* Checks if a PlottableData is valid. */
		if (plottableObject != null){
			//initFields();
			this.plottableObject = function;
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
/*	private void initFields() {

		/* Checks if plottableObject is a Function or DataFile */
/*		Function func = (Function) plottableObject;
		tfDataSelection.setText(func.getFunctionString());

		tfTitle.setText(plottableObject.getTitle());

		cbStyle.setSelectedItem(plottableObject.getStyle());
	}*/
	
/*	private JPanel createMainPanel() {
		// Create the panel.
		JGPPanel jp = new JGPPanel();

		// Set the default panel layout.
		GridBagLayout gbl = new GridBagLayout();
		jp.setLayout(gbl);

		// Create the buttons.
		JButton bOk = new JButton("ok");
		bOk.setPreferredSize(new Dimension(60, 20));
		bOk.setActionCommand("ok");
		bOk.addActionListener(this);

		JButton bAdvanced = new JButton("Advanced");
		bAdvanced.setPreferredSize(new Dimension(100, 20));
		bAdvanced.setActionCommand("advanced");
		bAdvanced.addActionListener(this);

		JButton bCancel = new JButton("cancel");
		bCancel.setPreferredSize(new Dimension(100, 20));
		bCancel.setActionCommand("cancel");
		bCancel.addActionListener(this);

		rbFile = new JRadioButton("File", true);
		rbFunc = new JRadioButton("Function");
		rbFile.addActionListener(this);
		rbFile.setActionCommand("file");
		rbFunc.addActionListener(this);
		rbFunc.setActionCommand("function");

		ButtonGroup group = new ButtonGroup();
		group.add(rbFile);
		group.add(rbFunc);

		tfFileName = new JTextField("", 6);
		tfFileName.setToolTipText("Path to the data file.");

		tfPreProcess = new JTextField("", 6);
		tfPreProcess.setToolTipText("Allows to preprocess the file throu an external command.\n"
				+ "Variables: $if == input file, $of == ouput file. \n"
				+ "Example: cat $if | sort -n > $of; sorts the data file.");

		tfDataSelection = new JTextField("1:2", 6);
		tfDataSelection.setToolTipText(
				"This sting will be passed as 'using' part \n" + "of the plot command to gnuplot. Start gnuplot \n"
						+ "from the command line and enter 'help using' \n" + "for documentation.");

		tfTitle = new JTextField("", 6);
		tfTitle.setToolTipText("The title will be passed vie the 'title' option of the plot command to gnuplot.");

		cbStyle = new StyleComboBox();

		int row = 0;
		jp.add(new JLabel("Source:"), 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(rbFile, 1, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(rbFunc, 2, row, 2, 1, GridBagConstraints.HORIZONTAL);
		row += 1;
		jp.add(new JLabel("File"), 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(tfFileName, 1, row, 3, 1, GridBagConstraints.HORIZONTAL);
		jp.add(bFileChose, 4, row, 1, 1, GridBagConstraints.HORIZONTAL);
		row += 1;
		jp.add(new JLabel("Preprocess external program"), 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(tfPreProcess, 1, row, 3, 1, GridBagConstraints.HORIZONTAL);
		jp.add(bProgChose, 4, row, 1, 1, GridBagConstraints.HORIZONTAL);

		row += 1;
		jp.add(lFunction = new JLabel("Columns selection"), 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
		//Create the scroll pane and add the table to it.
		JDataSelection dataSelectionTable = new JDataSelection();
        JScrollPane scrollPane = new JScrollPane(dataSelectionTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jp.add(scrollPane, 1, row, 3, 1, GridBagConstraints.HORIZONTAL);
		
		row += 1;
		jp.add(new JLabel("Title"), 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(tfTitle, 1, row, 3, 1, GridBagConstraints.HORIZONTAL);
		row += 1;
		jp.add(new JLabel("Style"), 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(cbStyle, 1, row, 3, 1, GridBagConstraints.HORIZONTAL);

		row += 1;
		jp.add(bAdvanced, 1, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(bOk, 2, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(bCancel, 3, row, 1, 1, GridBagConstraints.HORIZONTAL);

		return jp;
	}*/

	/**
	 * This method allows to manage actions performed on the AddDialog.
	 */
/*	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("..."))
			acFileBrowse();
		if (e.getActionCommand().equals("progchoose"))
			acProgBrowse();
		else if (e.getActionCommand().equals("ok")) {
			// TODO checks if file path is empty
			// TODO checks if function is empty
			acApply();
			this.setVisible(false);
		} else if (e.getActionCommand().equals("advanced")) {
			// TODO change AddDialog content to advanced option
			// PreProcessprogram

		} else if (e.getActionCommand().equals("cancel"))
			this.setVisible(false);
		else if (e.getActionCommand().equals("file")) {
			tfFileName.setEnabled(true);
			bFileChose.setEnabled(true);
			tfPreProcess.setEnabled(true);
			bProgChose.setEnabled(true);
			tfDataSelection.setText("1:2");
			lFunction.setText("Columns selection");
		} else if (e.getActionCommand().equals("function")) {
			tfFileName.setEnabled(false);
			bFileChose.setEnabled(false);
			tfPreProcess.setEnabled(false);
			bProgChose.setEnabled(false);
			tfDataSelection.setText("x**2");
			lFunction.setText("Function");
		}
	}
	*/
}
