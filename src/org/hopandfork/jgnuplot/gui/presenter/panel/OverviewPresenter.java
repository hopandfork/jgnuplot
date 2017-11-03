package org.hopandfork.jgnuplot.gui.presenter.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;
import org.hopandfork.jgnuplot.control.LabelController;
import org.hopandfork.jgnuplot.control.PlotController;
import org.hopandfork.jgnuplot.control.PlottableDataController;
import org.hopandfork.jgnuplot.gui.dialog.DataFileDialog;
import org.hopandfork.jgnuplot.gui.dialog.FunctionDialog;
import org.hopandfork.jgnuplot.gui.dialog.LabelDialog;
import org.hopandfork.jgnuplot.gui.table.LabelsTableModel;
import org.hopandfork.jgnuplot.gui.table.PlottableDataTableModel;
import org.hopandfork.jgnuplot.model.DataFile;
import org.hopandfork.jgnuplot.model.Function;
import org.hopandfork.jgnuplot.model.Label;
import org.hopandfork.jgnuplot.model.PlottableData;

public class OverviewPresenter implements ActionListener, DocumentListener {

	private static final Logger LOG = Logger.getLogger(OverviewPresenter.class);

	private static final String ACTION_ADD_DATAFILE = "add_datafile";
	private static final String ACTION_ADD_FUNCTION = "add_function";
	private static final String ACTION_EDIT_DATA = "edit_data";
	private static final String ACTION_DELETE_DATA = "delete_data";
	private static final String ACTION_MOVEUP_DATA = "up_data";
	private static final String ACTION_MOVEDOWN_DATA = "down_data";
	private static final String ACTION_ADD_LABEL = "add_label";
	private static final String ACTION_EDIT_LABEL = "edit_label";
	private static final String ACTION_DELETE_LABEL = "delete_label";

	private OverviewInterface overviewPanel = null;

	private PlotController plotController = null;
	
	private PlottableDataController plottableDataController = null;

	private LabelController labelController = null;

	private LabelsTableModel labelTableModel = null;

	private PlottableDataTableModel plottableDataTableModel = null;

	public OverviewPresenter(OverviewInterface overviewPanel, PlottableDataController plottableDataController,
			LabelController labelController, PlotController plotController) {
		this.overviewPanel = overviewPanel;

		this.plotController = plotController;
		
		this.plottableDataController = plottableDataController;

		this.labelController = labelController;

		this.labelTableModel = new LabelsTableModel(labelController);

		this.plottableDataTableModel = new PlottableDataTableModel(plottableDataController);

		overviewPanel.getLabelTable().setModel(labelTableModel);
		overviewPanel.getPlottableDataTable().setModel(plottableDataTableModel);

		overviewPanel.getButtonAddLabel().setActionCommand(ACTION_ADD_LABEL);
		overviewPanel.getButtonAddLabel().addActionListener(this);

		overviewPanel.getButtonEditLabel().setActionCommand(ACTION_EDIT_LABEL);
		overviewPanel.getButtonEditLabel().addActionListener(this);

		overviewPanel.getButtonDeleteLabel().setActionCommand(ACTION_DELETE_LABEL);
		overviewPanel.getButtonDeleteLabel().addActionListener(this);

		overviewPanel.getButtonAddDataFile().setActionCommand(ACTION_ADD_DATAFILE);
		overviewPanel.getButtonAddDataFile().addActionListener(this);

		overviewPanel.getButtonAddFunction().setActionCommand(ACTION_ADD_FUNCTION);
		overviewPanel.getButtonAddFunction().addActionListener(this);

		overviewPanel.getButtonEdit().setActionCommand(ACTION_EDIT_DATA);
		overviewPanel.getButtonEdit().addActionListener(this);

		overviewPanel.getButtonDelete().setActionCommand(ACTION_DELETE_DATA);
		overviewPanel.getButtonDelete().addActionListener(this);

		overviewPanel.getButtonMoveUp().setActionCommand(ACTION_MOVEUP_DATA);
		overviewPanel.getButtonMoveUp().addActionListener(this);

		overviewPanel.getButtonMoveDown().setActionCommand(ACTION_MOVEDOWN_DATA);
		overviewPanel.getButtonMoveDown().addActionListener(this);
		
		overviewPanel.getPrePlotString().getDocument().addDocumentListener(this);
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
		PlottableData plottableData = plottableDataTableModel
				.getSelectedPlottableData(overviewPanel.getPlottableDataTable().getSelectedRow());
		if (plottableData == null) {
			LOG.info("Nothing to edit");
			return;
		}

		if (plottableData instanceof Function) {
			try {
				FunctionDialog functionDialog = new FunctionDialog((Function) plottableData, plottableDataController);
				functionDialog.setVisible(true);
			} catch (IOException e) {
				LOG.error(e.getMessage());
			}
		} else {
			try {
				DataFileDialog dataFileDialog = new DataFileDialog((DataFile) plottableData, plottableDataController);
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
		List<PlottableData> selectedData = plottableDataTableModel
				.getSelectedPlottableData(overviewPanel.getPlottableDataTable().getSelectedRows());
		for (PlottableData plottableData : selectedData)
			plottableDataController.moveUp(plottableData);
	}

	/**
	 * Moves the currently selected datasets one position further down in the
	 * list.
	 */
	public void moveDownPlottableData() {
		List<PlottableData> selectedData = plottableDataTableModel
				.getSelectedPlottableData(overviewPanel.getPlottableDataTable().getSelectedRows());
		for (PlottableData plottableData : selectedData)
			plottableDataController.moveDown(plottableData);
	}

	public void deletePlottableData() {
		List<PlottableData> selectedData = plottableDataTableModel
				.getSelectedPlottableData(overviewPanel.getPlottableDataTable().getSelectedRows());
		for (PlottableData plottableData : selectedData) {
			plottableDataController.delete(plottableData);
		}
	}

	public void addLabel() {
		LabelDialog labelDialog = new LabelDialog(labelController);
		labelDialog.setVisible(true);
	}

	public void editLabel() {
		Label label = labelTableModel.getSelectedLabel(overviewPanel.getLabelTable().getSelectedRow());
		if (label == null)
			return;

		LabelDialog labelDialog = new LabelDialog(label, labelController);
		labelDialog.setVisible(true);
	}

	public void deleteLabel() {
		List<Label> selectedLabels = labelTableModel.getSelectedLabels(overviewPanel.getLabelTable().getSelectedRows());
		for (Label l : selectedLabels) {
			labelController.delete(l);
		}
	}

	@Override
	public void insertUpdate(DocumentEvent documentEvent) {
		plotController.updatePreplotScript(overviewPanel.getPrePlotString().getText());
	}

	@Override
	public void removeUpdate(DocumentEvent documentEvent) {
		plotController.updatePreplotScript(overviewPanel.getPrePlotString().getText());
	}

	@Override
	public void changedUpdate(DocumentEvent documentEvent) {
		plotController.updatePreplotScript(overviewPanel.getPrePlotString().getText());
	}

}