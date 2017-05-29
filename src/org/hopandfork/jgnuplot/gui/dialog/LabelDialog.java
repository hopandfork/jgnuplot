package org.hopandfork.jgnuplot.gui.dialog;

import org.apache.log4j.Logger;
import org.hopandfork.jgnuplot.control.ConstantController;
import org.hopandfork.jgnuplot.control.LabelController;
import org.hopandfork.jgnuplot.control.PlottableDataController;
import org.hopandfork.jgnuplot.gui.JGPPanel;
import org.hopandfork.jgnuplot.gui.buttons.MinusButton;
import org.hopandfork.jgnuplot.gui.buttons.PlusButton;
import org.hopandfork.jgnuplot.gui.combobox.StyleComboBox;
import org.hopandfork.jgnuplot.gui.table.ConstantsTableModel;
import org.hopandfork.jgnuplot.model.Function;
import org.hopandfork.jgnuplot.model.style.PlottingStyle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LabelDialog extends JGPDialog implements ActionListener {

	private static Logger LOG = Logger.getLogger(LabelDialog.class);
	private LabelController controller;
	private Label currentLabel = null;


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
	public LabelDialog(LabelController controller) {
		this.controller = controller;

		add(createMainPanel());

		this.setTitle("Label");
		this.pack();
		this.setModal(true);
	}

	public LabelDialog(Label label, LabelController controller) throws IOException {
		this(controller);
		this.currentLabel = label;
		initFields();
	}

	/**
	 * This method allows to set all field in the dialog to apply some changes.
	 */
	private void initFields() {
		// TODO
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

	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("ok")) {
			acApply();
			this.setVisible(false);
		} else if (e.getActionCommand().equals("cancel")) {
			this.setVisible(false);
		}
	}

	private void acApply() {
		if (currentLabel != null) {
			controller.updateLabel(currentLabel, tfFunction.getText(), tfTitle.getText(),
					(PlottingStyle) cbStyle.getSelectedItem(), constantController.getConstants(tempFunction));
		} else {
			controller.addLabel(tfFunction.getText(), tfTitle.getText(),
					(PlottingStyle) cbStyle.getSelectedItem(), constantController.getConstants(tempFunction));
		}

		this.setVisible(false);
	}
}
