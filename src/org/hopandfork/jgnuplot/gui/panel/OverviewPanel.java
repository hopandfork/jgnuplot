package org.hopandfork.jgnuplot.gui.panel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;


import org.apache.log4j.Logger;
import org.hopandfork.jgnuplot.control.LabelController;
import org.hopandfork.jgnuplot.control.PlotController;
import org.hopandfork.jgnuplot.control.PlottableDataController;
import org.hopandfork.jgnuplot.gui.dialog.DataFileDialog;
import org.hopandfork.jgnuplot.gui.dialog.FunctionDialog;
import org.hopandfork.jgnuplot.gui.dialog.LabelDialog;
import org.hopandfork.jgnuplot.gui.table.LabelsTableModel;
import org.hopandfork.jgnuplot.gui.table.PlottableDataTableModel;
import org.hopandfork.jgnuplot.gui.utility.GridBagConstraintsFactory;
import org.hopandfork.jgnuplot.model.DataFile;
import org.hopandfork.jgnuplot.model.Function;
import org.hopandfork.jgnuplot.model.Label;
import org.hopandfork.jgnuplot.model.PlottableData;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class OverviewPanel extends JGPPanel implements OverviewInterface, ActionListener {

	private static final long serialVersionUID = 3708875306419236700L;

	static final Logger LOG = Logger.getLogger(OverviewPanel.class);

	private JTabbedPane tp;

	private PlottableDataTableModel plottableDataTableModel;

	private JTable plottableDataTable;

	private LabelsTableModel labelTableModel;

	private JTable labelTable;

	private JTextArea prePlotString;

	private PlottableDataController plottableDataController;

	private PlotController plotController;

	private LabelController labelController;

	private MenuInterface menu;


	/*
	 * Actions.
	 */
	static private final String ACTION_ADD_DATAFILE = "add_datafile";
	static private final String ACTION_ADD_FUNCTION = "add_function";
	static private final String ACTION_EDIT_DATA = "edit_data";
	static private final String ACTION_DELETE_DATA = "delete_data";
	static private final String ACTION_MOVEUP_DATA = "up_data";
	static private final String ACTION_MOVEDOWN_DATA = "down_data";
	static private final String ACTION_ADD_LABEL = "add_label";
	static private final String ACTION_EDIT_LABEL = "edit_label";
	static private final String ACTION_DELETE_LABEL = "delete_label";

	public OverviewPanel(MenuInterface menu, PlotController plotController, PlottableDataController plottableDataController,
						 LabelController labelController) {
		this.menu = menu;
		this.plotController = plotController;
		this.plottableDataController = plottableDataController;
		this.labelController = labelController;

		createCenterPanel();
	}

	private void createCenterPanel() {
		// Set the default panel layout.
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 5;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;

		this.add(create_tabbed_pane(), gbc);
	}

	private JTabbedPane create_tabbed_pane() {
		tp = new JTabbedPane(JTabbedPane.TOP);
		tp.addTab("Data", createDataSetPanel());
		tp.addTab("Labels", createLabelSetPanel());
		tp.addTab("Additional gnuplot commands", createPrePlotStringPanel());

		return tp;
	}

	private JPanel createLabelSetPanel() {

		// Create the panel.
		JGPPanel jp = new JGPPanel();
		// Set the default panel layout.
		GridBagLayout gbl = new GridBagLayout();
		jp.setLayout(gbl);
		GridBagConstraints gbc;

		labelTableModel = new LabelsTableModel(labelController);
		labelTable = new JTable(labelTableModel);
		labelTable.setPreferredScrollableViewportSize(new Dimension(500, 200));
		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(labelTable);

		JButton btnAdd = new JButton("Add");
		btnAdd.setActionCommand(ACTION_ADD_LABEL);
		btnAdd.addActionListener(this);
		gbc = GridBagConstraintsFactory.create(0, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		jp.add(btnAdd, gbc);

		JButton btnEdit = new JButton("Edit");
		btnEdit.setActionCommand(ACTION_EDIT_LABEL);
		btnEdit.addActionListener(this);
		gbc = GridBagConstraintsFactory.create(1, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		jp.add(btnEdit, gbc);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setActionCommand(ACTION_DELETE_LABEL);
		btnDelete.addActionListener(this);
		gbc = GridBagConstraintsFactory.create(2, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		jp.add(btnDelete, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		jp.add(scrollPane, gbc);

		return jp;
	}

	private JPanel createDataSetPanel() {

		JGPPanel jp = new JGPPanel();
		GridBagLayout gbl = new GridBagLayout();
		jp.setLayout(gbl);
		GridBagConstraints gbc;

		plottableDataTableModel = new PlottableDataTableModel(plottableDataController);
		plottableDataTable = new JTable(plottableDataTableModel);
		plottableDataTable.setPreferredScrollableViewportSize(new Dimension(500, 200));

		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(plottableDataTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


		JButton btnAdd = new JButton("Add datafile");
		btnAdd.setActionCommand(ACTION_ADD_DATAFILE);
		btnAdd.addActionListener(this);
		gbc = GridBagConstraintsFactory.create(0, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		jp.add(btnAdd, gbc);

		JButton btnAddFunction = new JButton("Add function");
		btnAddFunction.setActionCommand(ACTION_ADD_FUNCTION);
		btnAddFunction.addActionListener(this);
		gbc = GridBagConstraintsFactory.create(1, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		jp.add(btnAddFunction, gbc);

		JButton btnEdit = new JButton("Edit");
		btnEdit.setActionCommand(ACTION_EDIT_DATA);
		btnEdit.addActionListener(this);
		gbc = GridBagConstraintsFactory.create(2, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		jp.add(btnEdit, gbc);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setActionCommand(ACTION_DELETE_DATA);
		btnDelete.addActionListener(this);
		gbc = GridBagConstraintsFactory.create(3, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		jp.add(btnDelete, gbc);

		JButton btnMoveUp = new JButton("Up");
		btnMoveUp.setActionCommand(ACTION_MOVEUP_DATA);
		btnMoveUp.addActionListener(this);
		gbc = GridBagConstraintsFactory.create(4, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		jp.add(btnMoveUp, gbc);

		JButton btnMoveDown = new JButton("Down");
		btnMoveDown.setActionCommand(ACTION_MOVEDOWN_DATA);
		btnMoveDown.addActionListener(this);
		gbc = GridBagConstraintsFactory.create(5, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		jp.add(btnMoveDown, gbc);

		gbc = GridBagConstraintsFactory.create(0, 1, 6, 1, 1, 1, GridBagConstraints.BOTH);
		jp.add(scrollPane, gbc);

		return jp;
	}

	private JPanel createPrePlotStringPanel() {
		// Create the panel.
		JGPPanel jp = new JGPPanel();
		// Set the default panel layout.
		GridBagLayout gbl = new GridBagLayout();
		jp.setLayout(gbl);

		prePlotString = new JTextArea(100, 20);
		prePlotString.setWrapStyleWord(true);
		prePlotString.setLineWrap(true);
		prePlotString.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent documentEvent) {
				plotController.updatePreplotScript(prePlotString.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent documentEvent) {
				plotController.updatePreplotScript(prePlotString.getText());
			}

			@Override
			public void changedUpdate(DocumentEvent documentEvent) {
				plotController.updatePreplotScript(prePlotString.getText());
			}
		});

		JScrollPane scrollPane = new JScrollPane(prePlotString);
		scrollPane.setPreferredSize(new Dimension(520, 240));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		jp.add(scrollPane, gbc);

		return jp;

	}


	@Override
	public PlottableData getSelectedPlottableData() {
		return plottableDataTableModel.getSelectedPlottableData(plottableDataTable.getSelectedRow());
	}

	@Override
	public Label getSelectedLabel() {
		return labelTableModel.getSelectedLabel(labelTable.getSelectedRow());
	}

	@Override
	public List<PlottableData> getAllSelectedPlottableData() {
		return plottableDataTableModel.getSelectedPlottableData(plottableDataTable.getSelectedRows());
	}

	@Override
	public List<Label> getSelectedLabels() {
		return labelTableModel.getSelectedLabels(labelTable.getSelectedRows());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

		if (cmd.equals(ACTION_ADD_DATAFILE)) {
			DataFileDialog dataFileDialog = new DataFileDialog(plottableDataController);
			dataFileDialog.setVisible(true);
		} else if (cmd.equals(ACTION_ADD_FUNCTION)) {
			FunctionDialog addFunctionDialog = new FunctionDialog(plottableDataController);
			addFunctionDialog.setVisible(true);
		} else if (cmd.equals(ACTION_EDIT_DATA)) {
			editPlottableData();
		} else if (cmd.equals(ACTION_MOVEUP_DATA)) {
			moveUpPlottableData();
		} else if (cmd.equals(ACTION_MOVEDOWN_DATA)) {
			moveDownPlottableData();
		} else if (cmd.equals(ACTION_DELETE_DATA)) {
			deletePlottableData();
		} else if (cmd.equals(ACTION_ADD_LABEL)) {
			addLabel();
		} else if (cmd.equals(ACTION_EDIT_LABEL)) {
			editLabel();
		} else if (cmd.equals(ACTION_DELETE_LABEL)) {
			deleteLabel();
		}
	}

	private void editPlottableData() {
		PlottableData plottableData = getSelectedPlottableData();
		if (plottableData == null) {
			LOG.info("Nothing to edit");
			return;
		}

		if (plottableData instanceof Function) {
			try {
				FunctionDialog functionDialog = new FunctionDialog((Function) plottableData,
						plottableDataController);
				functionDialog.setVisible(true);
			} catch (IOException e) {
				LOG.error(e.getMessage());
			}
		} else {
			try {
				DataFileDialog dataFileDialog = new DataFileDialog((DataFile) plottableData,
						plottableDataController);
				dataFileDialog.setVisible(true);
			} catch (IOException e) {
				LOG.error(e.getMessage());
			}
		}
	}

	/**
	 * Moves the currently selected datasets one position further up in the
	 * list.
	 */
	public void moveUpPlottableData() {
		List<PlottableData> selectedData = getAllSelectedPlottableData();
		for (PlottableData plottableData : selectedData)
			plottableDataController.moveUp(plottableData);
	}

	/**
	 * Moves the currently selected datasets one position further down in the
	 * list.
	 */
	public void moveDownPlottableData() {
		List<PlottableData> selectedData = getAllSelectedPlottableData();
		for (PlottableData plottableData : selectedData)
			plottableDataController.moveDown(plottableData);
	}

	public void deletePlottableData() {
		List<PlottableData> selectedData = getAllSelectedPlottableData();
		for (PlottableData plottableData : selectedData) {
			plottableDataController.delete(plottableData);
		}
	}

	public void addLabel()
	{
		LabelDialog labelDialog = new LabelDialog(labelController);
		labelDialog.setVisible(true);
	}

	public void editLabel() {
		Label label = getSelectedLabel();
		if (label == null)
			return;

		LabelDialog labelDialog = new LabelDialog(label, labelController);
		labelDialog.setVisible(true);
	}


	public void deleteLabel() {
		List<Label> selectedLabels = getSelectedLabels();
		for (Label l : selectedLabels) {
			labelController.delete(l);
		}
	}

	@Override
	public Dimension getMinimumSize() {
		int height = 100;
		int width = 100 + tp.getMinimumSize().width;
		return new Dimension(width,height);
	}
}
