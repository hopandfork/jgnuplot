package org.hopandfork.jgnuplot.gui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;
import org.hopandfork.jgnuplot.control.LabelController;
import org.hopandfork.jgnuplot.control.PlotController;
import org.hopandfork.jgnuplot.control.PlottableDataController;
import org.hopandfork.jgnuplot.gui.dialog.ConsoleDialog;
import org.hopandfork.jgnuplot.gui.dialog.DataFileDialog;
import org.hopandfork.jgnuplot.gui.dialog.FunctionDialog;
import org.hopandfork.jgnuplot.gui.dialog.LabelDialog;
import org.hopandfork.jgnuplot.gui.dialog.PlotDialog;
import org.hopandfork.jgnuplot.model.DataFile;
import org.hopandfork.jgnuplot.model.Function;
import org.hopandfork.jgnuplot.model.Label;
import org.hopandfork.jgnuplot.model.Plot;
import org.hopandfork.jgnuplot.model.PlottableData;
import org.hopandfork.jgnuplot.model.Project;

public class BottomPanel extends JGPPanel implements ActionListener, ChangeListener, BottomInterface, Observer {

	private static final long serialVersionUID = -7381866132142192657L;

	private static Logger LOG = Logger.getLogger(BottomPanel.class);

	private JButton bEdit, bDelete, bAdd, bClear, bMoveUp, bMoveDown, bPlot, bPlotPs, bPrint, bPlotString;

	private JTextField tfTitle;

	private JRadioButton rb2D, rb3D;

	private JTextField tfMaxX, tfMinX, tfMaxY, tfMinY, tfMaxZ, tfMinZ;

	private JTextField tfXLabel, tfYLabel, tfZLabel;

	private JCheckBox cbLogScaleX, cbLogScaleY, cbLogScaleZ;

	private OverviewInterface overview;

	private PlottableDataController plottableDataController;

	private LabelController labelController;

	private PlotController plotController;

	private PlotDialog plotDialog;

	private ConsoleDialog consoleDialog;

	public BottomPanel(OverviewInterface overview, PlottableDataController plottableDataController,
			LabelController labelController, PlotController plotController) {
		this.overview = overview;
		this.plottableDataController = plottableDataController;
		this.labelController = labelController;
		this.plotController = plotController;
		plotController.addObserver(this);

		createButtonPanel();
	}

	private void createButtonPanel() {

		this.setPreferredSize(new Dimension(700, 400));
		this.setBackground(new Color(0xf0f0f0));
		// Set the default panel layout.
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);

		// Create the panel border.
		TitledBorder border = new TitledBorder(new EtchedBorder(), "");
		border.setTitleColor(Color.blue);
		this.setBorder(border);

		// Create the buttons.
		bEdit = new JButton("edit");
		bEdit.setPreferredSize(new Dimension(80, 20));
		bEdit.setActionCommand("edit");
		bEdit.addActionListener(this);

		bDelete = new JButton("delete");
		bDelete.setPreferredSize(new Dimension(80, 20));
		bDelete.setActionCommand("delete");
		bDelete.addActionListener(this);

		bAdd = new JButton("add");
		bAdd.setPreferredSize(new Dimension(80, 20));
		bAdd.setActionCommand("add_datafile");
		bAdd.addActionListener(this);

		bClear = new JButton("clear");
		bClear.setPreferredSize(new Dimension(80, 20));
		bClear.setActionCommand("clear");
		bClear.addActionListener(this);

		bMoveUp = new JButton("up");
		bMoveUp.setPreferredSize(new Dimension(80, 20));
		bMoveUp.setActionCommand("moveup");
		bMoveUp.addActionListener(this);

		bMoveDown = new JButton("down");
		bMoveDown.setPreferredSize(new Dimension(80, 20));
		bMoveDown.setActionCommand("movedown");
		bMoveDown.addActionListener(this);

		rb2D = new JRadioButton("2D plot", true);
		rb3D = new JRadioButton("3D plot");
		rb2D.addChangeListener(this);
		rb3D.addChangeListener(this);
		rb3D.setActionCommand("2dplot");
		rb3D.setActionCommand("3dplot");

		ButtonGroup group = new ButtonGroup();
		group.add(rb2D);
		group.add(rb3D);

		tfTitle = new JTextField("", 16);

		tfMaxX = new JTextField("", 8);

		tfMaxY = new JTextField("", 8);

		tfXLabel = new JTextField("", 10);

		tfMinX = new JTextField("", 8);

		tfMinY = new JTextField("", 8);

		tfYLabel = new JTextField("", 10);

		tfMaxZ = new JTextField("", 8);

		tfMinZ = new JTextField("", 8);

		tfZLabel = new JTextField("", 10);

		cbLogScaleX = new JCheckBox();
		cbLogScaleY = new JCheckBox();
		cbLogScaleZ = new JCheckBox();

		// disable 3d until user selects 3d plot
		tfMaxZ.setEnabled(false);
		tfMinZ.setEnabled(false);
		tfZLabel.setEnabled(false);
		cbLogScaleZ.setEnabled(false);

		int row = 0;
		this.add(bMoveUp, 0, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(bMoveDown, 1, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		row += 1;
		this.add(bEdit, 0, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(bAdd, 1, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(bDelete, 3, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(bClear, 4, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		row += 1;
		row += 3;
		this.add(new JLabel("Title"), 0, row, 1, 1, GridBagConstraints.WEST);
		this.add(tfTitle, 1, row, 4, 1, GridBagConstraints.BOTH);
		row += 1;
		this.add(new JLabel("Plottype:"), 0, row, 1, 1, GridBagConstraints.WEST);
		this.add(rb2D, 1, row, 1, 1, GridBagConstraints.NONE);
		this.add(rb3D, 2, row, 1, 1, GridBagConstraints.NONE);
		row += 1;
		this.add(new JLabel("min X"), 0, row, 1, 1, GridBagConstraints.WEST);
		this.add(tfMinX, 1, row, 1, 1, GridBagConstraints.NONE);
		this.add(new JLabel("x axis label"), 2, row, 1, 1, GridBagConstraints.WEST);
		this.add(tfXLabel, 3, row, 2, 1, GridBagConstraints.BOTH);
		row += 1;
		this.add(new JLabel("max X"), 0, row, 1, 1, GridBagConstraints.WEST);
		this.add(tfMaxX, 1, row, 1, 1, GridBagConstraints.NONE);
		this.add(new JLabel("logscale x axis"), 2, row, 1, 1, GridBagConstraints.WEST);
		this.add(cbLogScaleX, 3, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		row += 1;
		this.add(new JLabel("min Y"), 0, row, 1, 1, GridBagConstraints.WEST);
		this.add(tfMinY, 1, row, 1, 1, GridBagConstraints.NONE);
		this.add(new JLabel("y axis label"), 2, row, 1, 1, GridBagConstraints.WEST);
		this.add(tfYLabel, 3, row, 2, 1, GridBagConstraints.BOTH);
		row += 1;
		this.add(new JLabel("max Y"), 0, row, 1, 1, GridBagConstraints.WEST);
		this.add(tfMaxY, 1, row, 1, 1, GridBagConstraints.NONE);
		this.add(new JLabel("logscale y axis"), 2, row, 1, 1, GridBagConstraints.WEST);
		this.add(cbLogScaleY, 3, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		row += 1;
		this.add(new JLabel("min Z"), 0, row, 1, 1, GridBagConstraints.WEST);
		this.add(tfMinZ, 1, row, 1, 1, GridBagConstraints.NONE);
		this.add(new JLabel("z axis label"), 2, row, 1, 1, GridBagConstraints.WEST);
		this.add(tfZLabel, 3, row, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		row += 1;
		this.add(new JLabel("max Z"), 0, row, 1, 1, GridBagConstraints.WEST);
		this.add(tfMaxZ, 1, row, 1, 1, GridBagConstraints.NONE);
		this.add(new JLabel("logscale z axis"), 2, row, 1, 1, GridBagConstraints.WEST);
		this.add(cbLogScaleZ, 3, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		row += 1;

		// Create the buttons.
		plotDialog = new PlotDialog(plotController);
		JButton bPlot = new JButton("plot");
		bPlot.setPreferredSize(new Dimension(60, 20));
		bPlot.setActionCommand("plot");
		bPlot.addActionListener(this);

		// Create the buttons.
		JButton bPlotPs = new JButton("save to file");
		bPlotPs.setPreferredSize(new Dimension(160, 20));
		bPlotPs.setActionCommand("plotps");
		bPlotPs.addActionListener(this);

		// Create the buttons.
		JButton bPrint = new JButton("print");
		bPrint.setPreferredSize(new Dimension(80, 20));
		bPrint.setActionCommand("print");
		bPrint.addActionListener(this);

		JButton bPlotString = new JButton("preview plotstring");
		bPlotString.setPreferredSize(new Dimension(160, 20));
		bPlotString.setActionCommand("genplotcmds");
		bPlotString.addActionListener(this);

		this.add(bPlot, 0, row, 1, 1, GridBagConstraints.NONE);
		this.add(bPlotPs, 1, row, 1, 1, GridBagConstraints.NONE);
		this.add(bPrint, 2, row, 1, 1, GridBagConstraints.NONE);
		this.add(bPlotString, 3, row, 1, 1, GridBagConstraints.NONE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("add_datafile")) {
			DataFileDialog dataFileDialog = new DataFileDialog(plottableDataController);
			dataFileDialog.setVisible(true);
		} else if (e.getActionCommand().equals("add_function")) {
			FunctionDialog addFunctionDialog = new FunctionDialog(plottableDataController);
			addFunctionDialog.setVisible(true);
		} else if (e.getActionCommand().equals("edit")) {
			acEdit();
		} else if (e.getActionCommand().equals("delete")) {
			acDelete();
		} else if (e.getActionCommand().equals("clear")) {
			acClear();
		} else if (e.getActionCommand().equals("moveup")) {
			acMoveUp();
		} else if (e.getActionCommand().equals("movedown")) {
			acMoveDown();
		} else if (e.getActionCommand().equals("plot")) {
			try {
				acPlot();
			} catch (IOException e1) {
				LOG.error("Plot failed:\n" + e1.getMessage());
			} catch (InterruptedException e1) {
				LOG.error("Plot failed:\n" + e1.getMessage());
			}
		} else if (e.getActionCommand().equals("plotps")) {
			acPlotPS();
		} else if (e.getActionCommand().equals("print")) {
			acPrint();
		} else if (e.getActionCommand().equals("genplotcmds")) {
			acGenPlotCmds();
		} else if (e.getActionCommand().equals("plot")) {
			try {
				acPlot();
			} catch (IOException e1) {
				LOG.error("Plot failed:\n" + e1.getMessage());
			} catch (InterruptedException e1) {
				LOG.error("Plot failed:\n" + e1.getMessage());
			}
		}

	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource().equals(rb2D))
			cl2Dor3D();
	}

	/**
	 * This method allows to edit the PlottableData or Lable object selected.
	 */
	public void acEdit() {
		if (overview.isPlottableDataSelected()) {
			PlottableData plottableData = overview.getSelectedPlottableData();
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
		} else if (overview.isLabelSelected()) {
			/* Labels. */
			Label label = overview.getSelectedLabel();
			if (label == null)
				return;

			LabelDialog labelDialog = new LabelDialog(label, labelController);
			labelDialog.setVisible(true);
		}
	}

	/**
	 * Moves the currently selected datasets one position further up in the
	 * list.
	 */
	public void acMoveUp() {

		if (overview.isPlottableDataSelected()) {
			List<PlottableData> selectedData = overview.getSelectedPlottableDatas();
			for (PlottableData plottableData : selectedData)
				plottableDataController.moveUp(plottableData);
		}
	}

	/**
	 * Moves the currently selected datasets one position further down in the
	 * list.
	 */
	public void acMoveDown() {
		if (overview.isPlottableDataSelected()) {
			List<PlottableData> selectedData = overview.getSelectedPlottableDatas();

			for (PlottableData plottableData : selectedData)
				plottableDataController.moveDown(plottableData);
		}
	}

	public void acDelete() {
		if (overview.isPlottableDataSelected()) {
			List<PlottableData> selectedData = overview.getSelectedPlottableDatas();
			for (PlottableData plottableData : selectedData) {
				plottableDataController.delete(plottableData);
			}
		} else if (overview.isLabelSelected()) {
			List<Label> selectedLabels = overview.getSelectedLabels();
			for (Label l : selectedLabels) {
				labelController.delete(l);
			}
		}
	}

	public void acPlotPS() {
		plotDialog.setVisible(true);
	}

	public void acPrint() {
		Plot gp = getGNUplot();
		try {

			gp.generatePostscriptFile("", "");
		} catch (IOException e) {
			LOG.error("Error printing: " + e.getMessage());
		} catch (InterruptedException e) {
			LOG.error("Error printing: " + e.getMessage());
		}
	}

	public void acAdd() {
		if (overview.isPlottableDataSelected()) {
			/* Plottable data. */
			DataFileDialog addDataFileDialog = new DataFileDialog(plottableDataController);
			addDataFileDialog.setVisible(true);
		} else if (overview.isLabelSelected()) {
			/* Labels. */
			LabelDialog addLabelDialog = new LabelDialog(labelController);
			addLabelDialog.setVisible(true);
		}
	}

	public void cl2Dor3D() {
		tfMaxZ.setEnabled(!rb2D.isSelected());
		tfMinZ.setEnabled(!rb2D.isSelected());
		tfZLabel.setEnabled(!rb2D.isSelected());
		cbLogScaleZ.setEnabled(!rb2D.isSelected());

	}

	public Plot getGNUplot() {
		Plot gp = Project.currentProject().getPlot();

		if (rb2D.isSelected())
			gp.setMode(Plot.Mode.PLOT_2D);
		else
			gp.setMode(Plot.Mode.PLOT_3D);

		gp.setTitle(tfTitle.getText());
		try {
			if (!tfMaxX.getText().trim().equals(""))
				gp.setXmax(Double.parseDouble(tfMaxX.getText()));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Check max x value!" + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		try {
			if (!tfMaxY.getText().trim().equals(""))
				gp.setYmax(Double.parseDouble(tfMaxY.getText()));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Check max y value!" + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		try {
			if (!tfMinX.getText().trim().equals(""))
				gp.setXmin(Double.parseDouble(tfMinX.getText()));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Check min x value!" + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		try {
			if (!tfMinY.getText().trim().equals(""))
				gp.setYmin(Double.parseDouble(tfMinY.getText()));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Check min y value!" + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		try {
			if (!tfMaxZ.getText().trim().equals(""))
				gp.setZmax(Double.parseDouble(tfMaxZ.getText()));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Check max z value!" + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		try {
			if (!tfMinZ.getText().trim().equals(""))
				gp.setXmin(Double.parseDouble(tfMinZ.getText()));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Check min z value!" + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}

		gp.setLogScaleX(cbLogScaleX.isSelected());
		gp.setLogScaleY(cbLogScaleY.isSelected());
		gp.setLogScaleZ(cbLogScaleZ.isSelected());
		gp.setXlabel(tfXLabel.getText());
		gp.setYlabel(tfYLabel.getText());
		gp.setZlabel(tfZLabel.getText());

		gp.setPrePlotString(overview.getPrePlotString().getText() + "\n");

		return gp;
	}

	public void acGenPlotCmds() {
		Plot gp = getGNUplot();
		String plotString = gp.toPlotString();
		// taShell.setText( plotString );

		showConsole(plotString, false);
	}

	/**
	 * Shows a console dialog. Calls showConsole(String text, boolean append,
	 * boolean makeVisible) with makeVisible == true.
	 *
	 * @param text
	 *            Text to display in the console.
	 */
	public void showConsole(String text, boolean append) {
		showConsole(text, append, true);
	}

	/**
	 * Shows a console dialog.
	 *
	 * @param text
	 *            Text to display in the console.
	 * @param makeVisible
	 *            Tells whether the console should be made visibile if not
	 *            visible already.
	 */
	public void showConsole(String text, boolean append, boolean makeVisible) {

		if (consoleDialog == null)
			consoleDialog = new ConsoleDialog();
		if (makeVisible)
			consoleDialog.setVisible(true);

		if (append)
			text = consoleDialog.getText() + "\n" + text;
		consoleDialog.setText(text);

	}

	public void acPlot() throws IOException, InterruptedException {
		clearShell();
		LOG.info("calling GNUplot...");
		Plot gp = getGNUplot();
		gp.plotAndPreview();
	}

	public void clearShell() {
		showConsole("", false, false);
	}

	public void acClear() {
		if (overview.isPlottableDataSelected()) {
			clearPlottableData();
		} else if (overview.isLabelSelected()) {
			clearLabelTable();
		} else {
			clearPrePlotString();
		}
	}

	public void clearPlottableData() {
		plottableDataController.deleteAll();
	}

	public void clearLabelTable() {
		labelController.deleteAll();
	}

	public void clearPrePlotString() {

	}

	@Override
	public void reset() {
		tfTitle.setText("");
		tfMaxX.setText("");
		tfMinX.setText("");
		tfMaxY.setText("");
		tfMinY.setText("");
		tfXLabel.setText("");
		tfYLabel.setText("");
		cbLogScaleX.setSelected(false);
		cbLogScaleX.setSelected(false);
	}

	@Override
	public JButton getbEdit() {
		return bEdit;
	}

	@Override
	public JButton getbDelete() {
		return bDelete;
	}

	@Override
	public JButton getbAdd() {
		return bAdd;
	}

	@Override
	public JButton getbMoveUp() {
		return bMoveUp;
	}

	@Override
	public JButton getbMoveDown() {
		return bMoveDown;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO has to be updated

	}
}
