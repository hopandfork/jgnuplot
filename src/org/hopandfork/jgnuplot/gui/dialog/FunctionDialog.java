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
import javax.swing.JTextField;

import org.hopandfork.jgnuplot.control.PlottableDataController;
import org.hopandfork.jgnuplot.gui.JGPPanel;
import org.hopandfork.jgnuplot.gui.StyleComboBox;
import org.hopandfork.jgnuplot.model.Function;
import org.hopandfork.jgnuplot.model.style.PlottingStyle;

public class FunctionDialog extends PlottableDataDialog implements ActionListener {
	private static final long serialVersionUID = 7768154692608038103L;

	public static final String TITLE = "Function";

	private JTextField tfTitle;

	private JTextField tfFunction;

	private StyleComboBox cbStyle;

	/**
	 * This constructor allows to create an empty FunctionDialog.
	 * 
	 * @param controller
	 *            allows to add a new Function.
	 */
	public FunctionDialog(PlottableDataController controller) {
		this.controller = controller;

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
	 *
	 * @param plottableObject
	 *            could be a Function or DataFile to be modified.
	 * 
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
		bOk.setPreferredSize(new Dimension(60, 20));
		bOk.setActionCommand("ok");
		bOk.addActionListener(this);

		JButton bCancel = new JButton("cancel");
		bCancel.setPreferredSize(new Dimension(100, 20));
		bCancel.setActionCommand("cancel");
		bCancel.addActionListener(this);

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
		jp.add(bOk, 1, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(bCancel, 2, row, 1, 1, GridBagConstraints.HORIZONTAL);

		return jp;
	}

	/**
	 * This method allows to manage actions performed on the FUnctionDialog.
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("ok")) {
			// TODO checks if function is empty
			acApply();
			this.setVisible(false);
		} else if (e.getActionCommand().equals("cancel"))
			this.setVisible(false);
	}

	/**
	 * This method allows to create a new Function or update an existing one.
	 */
	private void acApply() {
		if (plottableObject != null) {
			/* Updates function */
			controller.updateFunction((Function)plottableObject, tfFunction.getText(), tfTitle.getText(), (PlottingStyle) cbStyle.getSelectedItem());
		} else {
			/* Adds new function */
			controller.addFunction(tfFunction.getText(), tfTitle.getText(), (PlottingStyle) cbStyle.getSelectedItem());
		}
		this.setVisible(false);
	}
}
