package org.hopandfork.jgnuplot.gui.dialog;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.hopandfork.jgnuplot.control.ConstantController;
import org.hopandfork.jgnuplot.control.PlottableDataController;
import org.hopandfork.jgnuplot.gui.panel.JGPPanel;
import org.hopandfork.jgnuplot.gui.buttons.MinusButton;
import org.hopandfork.jgnuplot.gui.buttons.PlusButton;
import org.hopandfork.jgnuplot.gui.combobox.StyleComboBox;
import org.hopandfork.jgnuplot.gui.table.ConstantsTableModel;
import org.hopandfork.jgnuplot.model.Function;
import org.hopandfork.jgnuplot.model.style.PlottingStyle;

public class FunctionDialog extends PlottableDataDialog implements ActionListener {
	private static final long serialVersionUID = 7768154692608038103L;

	private static Logger LOG = Logger.getLogger(FunctionDialog.class);

	public static final String TITLE = "Function";

	private JTextField tfTitle;

	private JTextField tfFunction;

	private StyleComboBox cbStyle;

	private PlusButton bAddConstant;
	private MinusButton bRemoveConstant;
	private JTable constantsTable;
	private ConstantsTableModel constantsTableModel;

	private Function tempFunction;

	/**
	 * Controller for Function Constants.
	 */
	private ConstantController constantController = new ConstantController();

	/**
	 * This constructor allows to create an empty FunctionDialog.
	 * 
	 * @param controller
	 *            allows to add a new Function.
	 */
	public FunctionDialog(PlottableDataController controller) {
		this.controller = controller;
		this.tempFunction = new Function();

		add(createMainPanel());

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
	 * @throws NullPointerException
	 *             is thrown in case of null plottableObject.
	 */
	public FunctionDialog(Function function, PlottableDataController controller) throws IOException {
		this.plottableObject = function;
		tempFunction = (Function) function.clone();
		this.controller = controller;

		add(createMainPanel());

		/* Checks if a PlottableData is valid. */
		if (plottableObject != null) {
			initFields();
		} else {
			throw new NullPointerException("plottableObject has to be not null");
		}

		this.setTitle(TITLE);
		this.pack();
		this.setModal(true);
	}

	/**
	 * This method allows to set all field in the dialog to apply some changes.
	 */
	private void initFields() {

		/* Checks if plottableObject is a Function or DataFile */
		Function func = (Function) plottableObject;
		tfFunction.setText(func.getFunctionString());

		tfTitle.setText(func.getTitle());

		cbStyle.setSelectedItem(func.getStyle());

	}

	/**
	 * This method allows to compose the GUI.
	 * 
	 * @return
	 */
	private JPanel createMainPanel() {
		// Create the panel.
		JGPPanel jp = new JGPPanel();

		GridBagLayout gbl = new GridBagLayout();
		jp.setLayout(gbl);

		// Create the buttons.
		JButton bOk = new JButton("ok");
		bOk.setActionCommand("ok");
		bOk.addActionListener(this);

		JButton bCancel = new JButton("cancel");
		bCancel.setActionCommand("cancel");
		bCancel.addActionListener(this);

		bAddConstant = new PlusButton();
		bAddConstant.setActionCommand("add-constant");
		bAddConstant.addActionListener(this);

		bRemoveConstant = new MinusButton();
		bRemoveConstant.setActionCommand("rm-constant");
		bRemoveConstant.addActionListener(this);

		tfTitle = new JTextField("", 6);
		tfFunction = new JTextField("", 6);

		cbStyle = new StyleComboBox();

		int row = 0;
		jp.add(new JLabel("Function"), 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(tfFunction, 1, row, 1, 1, GridBagConstraints.HORIZONTAL);

		row += 1;
		jp.add(new JLabel("Title"), 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(tfTitle, 1, row, 3, 1, GridBagConstraints.HORIZONTAL);

		row += 1;
		jp.add(new JLabel("Style"), 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(cbStyle, 1, row, 3, 1, GridBagConstraints.HORIZONTAL);

		row += 1;
		jp.add(new JLabel("Constants"), 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(bAddConstant, 1, row, 1, 1, GridBagConstraints.CENTER);
		jp.add(bRemoveConstant, 2, row, 2, 1, GridBagConstraints.CENTER);

		row += 1;
		constantsTableModel = new ConstantsTableModel(constantController, tempFunction);
		constantsTable = new JTable(constantsTableModel);
		constantsTable.setPreferredScrollableViewportSize(new Dimension(200, 50));
		JScrollPane scrollPane = new JScrollPane(constantsTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jp.add(scrollPane, 0, row, 3, 1, GridBagConstraints.HORIZONTAL);

		row += 1;
		jp.add(bOk, 1, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(bCancel, 2, row, 1, 1, GridBagConstraints.HORIZONTAL);

		return jp;
	}

	/**
	 * This method allows to manage actions performed on the FUnctionDialog.
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("ok")) {
			// TODO check if function is empty
			acApply();
			this.setVisible(false);
		} else if (e.getActionCommand().equals("cancel")) {
			this.setVisible(false);
		} else if (e.getActionCommand().equals("add-constant")) {

			try {
				constantController.addConstant(tempFunction, "c", "0.0");
			} catch (IOException e1) {
				LOG.error(e1.getMessage());
			}

		} else if (e.getActionCommand().equals("rm-constant")) {
			int selectedRow = constantsTable.getSelectedRow();
			if (selectedRow >= 0) {
				constantController.deleteConstant(tempFunction, constantsTableModel.getConstant(selectedRow));
			}
		}
	}

	/**
	 * This method allows to create a new Function or update an existing one.
	 */
	private void acApply() {
		Function function;
		if (plottableObject != null) {
			/* Updates function */
			function = controller.updateFunction((Function) plottableObject, tfFunction.getText(), tfTitle.getText(),
					(PlottingStyle) cbStyle.getSelectedItem(), constantController.getConstants(tempFunction));
		} else {
			/* Adds new function */
			function = controller.addFunction(tfFunction.getText(), tfTitle.getText(),
					(PlottingStyle) cbStyle.getSelectedItem(), constantController.getConstants(tempFunction));
		}

		this.setVisible(false);
	}
}
