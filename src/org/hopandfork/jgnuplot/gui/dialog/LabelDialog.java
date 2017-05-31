package org.hopandfork.jgnuplot.gui.dialog;

import org.apache.log4j.Logger;
import org.hopandfork.jgnuplot.control.ConstantController;
import org.hopandfork.jgnuplot.control.LabelController;
import org.hopandfork.jgnuplot.gui.JGPPanel;
import org.hopandfork.jgnuplot.model.Label;
import org.hopandfork.jgnuplot.model.RelativePosition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LabelDialog extends JGPDialog implements ActionListener {

	private static Logger LOG = Logger.getLogger(LabelDialog.class);
	private LabelController controller;
	private Label currentLabel = null;

	private JTextField txtLabelText;
	private JTextField txtX, txtY;
	private JComboBox<RelativePosition> comboRelativePosition;


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

	public LabelDialog(Label label, LabelController controller) {
		this(controller);
		this.currentLabel = label;
		initFields();
	}

	/**
	 * This method allows to set all field in the dialog to apply some changes.
	 */
	private void initFields() {
		if (currentLabel != null) {
			txtLabelText.setText(currentLabel.getText());
			txtX.setText("" + currentLabel.getX());
			txtY.setText("" + currentLabel.getY());
			comboRelativePosition.setSelectedItem(currentLabel.getRelativePos());
		}
	}

	private JPanel createButtonsPanel(){
		JGPPanel panel = new JGPPanel();
		GridBagLayout gbl = new GridBagLayout();
		panel.setLayout(gbl);

		// Create the buttons.
		JButton bOk = new JButton("ok");
		bOk.setActionCommand("ok");
		bOk.addActionListener(this);

		JButton bCancel = new JButton("cancel");
		bCancel.setActionCommand("cancel");
		bCancel.addActionListener(this);

		panel.add(bOk, 0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.SOUTHEAST);
		panel.add(bCancel, 1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.SOUTHEAST);

		return panel;
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

		txtLabelText = new JTextField("", 6);
		txtX = new JTextField("", 6);
		txtY = new JTextField("", 6);

		comboRelativePosition = new JComboBox<>();
		comboRelativePosition.setEditable(false);
		for (RelativePosition relativePosition : RelativePosition.values())
			comboRelativePosition.addItem(relativePosition);

		int row = 0;
		jp.add(new JLabel("Text"), 0, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.EAST);
		jp.add(txtLabelText, 1, row, 1, 1, 100, 0, GridBagConstraints.HORIZONTAL);

		row += 1;
		jp.add(new JLabel("X:"), 0, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.EAST);
		jp.add(txtX, 1, row, 1, 1, 100, 0,  GridBagConstraints.HORIZONTAL);

		row += 1;
		jp.add(new JLabel("Y:"), 0, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.EAST);
		jp.add(txtY, 1, row, 1, 1, 100, 0, GridBagConstraints.HORIZONTAL);

		row += 1;
		jp.add(new JLabel("Position:"), 0, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.EAST);
		jp.add(comboRelativePosition, 1, row, 1, 1, 100, 0, GridBagConstraints.HORIZONTAL);

		row += 1;
		jp.add(createButtonsPanel(), 1, row, 1, 1, 100, 100, GridBagConstraints.NONE, GridBagConstraints.SOUTHEAST);

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
		double x = Double.parseDouble(txtX.getText());
		double y = Double.parseDouble(txtY.getText());

		if (currentLabel != null) {
			controller.updateLabel(currentLabel, txtLabelText.getText(), x, y,
					(RelativePosition)comboRelativePosition.getSelectedItem());
		} else {
			controller.addLabel(txtLabelText.getText(), x, y,
					(RelativePosition)comboRelativePosition.getSelectedItem());
		}

		this.setVisible(false);
	}


}
