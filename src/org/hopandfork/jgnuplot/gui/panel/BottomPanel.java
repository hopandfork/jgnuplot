package org.hopandfork.jgnuplot.gui.panel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.hopandfork.jgnuplot.control.PlotController;
import org.hopandfork.jgnuplot.gui.dialog.ConsoleDialog;
import org.hopandfork.jgnuplot.gui.dialog.PlotDialog;
import org.hopandfork.jgnuplot.model.Plot;
import org.hopandfork.jgnuplot.model.Plot.Mode;
import org.hopandfork.jgnuplot.model.Project;

public class BottomPanel extends JGPPanel
		implements ActionListener, KeyListener, ItemListener, BottomInterface, Observer {

	private static final long serialVersionUID = -7381866132142192657L;

//	private static Logger LOG = Logger.getLogger(BottomPanel.class);

	private JTextField tfTitle;

	private JRadioButton rb2D, rb3D;

	private JTextField tfMaxX, tfMinX, tfMaxY, tfMinY, tfMaxZ, tfMinZ;

	private JTextField tfXLabel, tfYLabel, tfZLabel;

	private JCheckBox cbLogScaleX, cbLogScaleY, cbLogScaleZ;

	private OverviewInterface overview;

	private PlotController plotController;

	private ConsoleDialog consoleDialog;

	public BottomPanel(OverviewInterface overview, PlotController plotController) {
		this.overview = overview;
		this.plotController = plotController;
		plotController.addObserver(this);

		createButtonPanel();
	}

	private void createButtonPanel() {

		this.setBackground(new Color(0xf0f0f0));
		// Set the default panel layout.
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);

		rb2D = new JRadioButton("2D plot");
		rb3D = new JRadioButton("3D plot");

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


		int row = 0;
		this.add(new JLabel("Title"), 0, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(tfTitle, 1, row, 4, 1, GridBagConstraints.HORIZONTAL);
		tfTitle.addKeyListener(this);

		row += 1;
		this.add(new JLabel("Plottype:"), 0, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(rb2D, 1, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(rb3D, 2, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		rb2D.addItemListener(this);

		row += 1;
		this.add(new JLabel("min X"), 0, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(tfMinX, 1, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		tfMinX.addKeyListener(this);
		this.add(new JLabel("x axis label"), 2, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(tfXLabel, 3, row, 2, 1, GridBagConstraints.BOTH);
		tfXLabel.addKeyListener(this);

		row += 1;
		this.add(new JLabel("max X"), 0, row, 1, 1, GridBagConstraints.WEST);
		this.add(tfMaxX, 1, row, 1, 1, GridBagConstraints.NONE);
		tfMaxX.addKeyListener(this);
		this.add(new JLabel("logscale x axis"), 2, row, 1, 1, GridBagConstraints.WEST);
		this.add(cbLogScaleX, 3, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		row += 1;
		this.add(new JLabel("min Y"), 0, row, 1, 1, GridBagConstraints.WEST);
		this.add(tfMinY, 1, row, 1, 1, GridBagConstraints.NONE);
		tfMinY.addKeyListener(this);
		this.add(new JLabel("y axis label"), 2, row, 1, 1, GridBagConstraints.WEST);
		this.add(tfYLabel, 3, row, 2, 1, GridBagConstraints.BOTH);
		tfYLabel.addKeyListener(this);
		row += 1;
		this.add(new JLabel("max Y"), 0, row, 1, 1, GridBagConstraints.WEST);
		this.add(tfMaxY, 1, row, 1, 1, GridBagConstraints.NONE);
		tfMaxY.addKeyListener(this);
		this.add(new JLabel("logscale y axis"), 2, row, 1, 1, GridBagConstraints.WEST);
		this.add(cbLogScaleY, 3, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		row += 1;
		this.add(new JLabel("min Z"), 0, row, 1, 1, GridBagConstraints.WEST);
		this.add(tfMinZ, 1, row, 1, 1, GridBagConstraints.NONE);
		tfMinZ.addKeyListener(this);
		this.add(new JLabel("z axis label"), 2, row, 1, 1, GridBagConstraints.WEST);
		this.add(tfZLabel, 3, row, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		tfZLabel.addKeyListener(this);
		row += 1;
		this.add(new JLabel("max Z"), 0, row, 1, 1, GridBagConstraints.WEST);
		this.add(tfMaxZ, 1, row, 1, 1, GridBagConstraints.NONE);
		tfMaxZ.addKeyListener(this);
		this.add(new JLabel("logscale z axis"), 2, row, 1, 1, GridBagConstraints.WEST);
		this.add(cbLogScaleZ, 3, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		row += 1;

		// Create the buttons.
		JButton bPlotPs = new JButton("Export");
		bPlotPs.setActionCommand("plotps");
		bPlotPs.addActionListener(this);

		JButton bPlotString = new JButton("View script");
		bPlotString.setActionCommand("genplotcmds");
		bPlotString.addActionListener(this);

		this.add(bPlotPs, 0, row, 1, 1, GridBagConstraints.NONE);
		this.add(bPlotString, 1, row, 1, 1, GridBagConstraints.NONE);

		/* Inits with default values */
		Plot plot = plotController.getCurrent();
		if (plot != null)
			initialize(plot);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("plotps")) {
			new PlotDialog(plotController).setVisible(true);
		} else if (e.getActionCommand().equals("genplotcmds")) {
			acGenPlotCmds();
		}
	}


	private void acGenPlotCmds() {
		Plot gp = plotController.getCurrent();
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
	private void showConsole(String text, boolean append) {
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
	private void showConsole(String text, boolean append, boolean makeVisible) {

		if (consoleDialog == null)
			consoleDialog = new ConsoleDialog();
		if (makeVisible)
			consoleDialog.setVisible(true);

		if (append)
			text = consoleDialog.getText() + "\n" + text;
		consoleDialog.setText(text);

	}

	/**
	 * Utility function which returns a string representation of an object, if not null.
	 */
	static private String str (Object value) {
		if (value != null) {
			return "" + value.toString();
		} else {
			return "";
		}
	}

	@Override
	public void initialize (Plot plot)
	{
		rb2D.setSelected(plot.getMode().equals(Mode.PLOT_2D));
		rb3D.setSelected(plot.getMode().equals(Mode.PLOT_3D));

		tfTitle.setText(str(plot.getTitle()));

		tfMaxX.setText(str(plot.getXmax()));
		tfMaxY.setText(str(plot.getYmax()));
		tfMinX.setText(str(plot.getXmin()));
		tfMinY.setText(str(plot.getYmin()));

		tfXLabel.setText(str(plot.getXlabel()));
		tfYLabel.setText(str(plot.getYlabel()));
		tfZLabel.setText(str(plot.getZlabel()));

		cbLogScaleX.setSelected(plot.isLogScaleX());
		cbLogScaleY.setSelected(plot.isLogScaleY());
		cbLogScaleZ.setSelected(plot.isLogScaleZ());
	}

	@Override
	public void reset() {
		rb2D.setSelected(true);
		rb3D.setSelected(false);

		tfTitle.setText("");

		tfMaxX.setText("");
		tfMinX.setText("");
		tfMaxY.setText("");
		tfMinY.setText("");

		tfXLabel.setText("");
		tfYLabel.setText("");
		tfZLabel.setText("");

		cbLogScaleX.setSelected(false);
		cbLogScaleX.setSelected(false);
		cbLogScaleZ.setSelected(false);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO has to be updated

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		updatePlot();
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		tfMaxZ.setEnabled(!rb2D.isSelected());
		tfMinZ.setEnabled(!rb2D.isSelected());
		tfZLabel.setEnabled(!rb2D.isSelected());
		cbLogScaleZ.setEnabled(!rb2D.isSelected());

		updatePlot();
	}

	private void updatePlot()
	{
		Mode mode = rb2D.isSelected() ? Mode.PLOT_2D : Mode.PLOT_3D;
		plotController.updatePlot(mode, tfTitle.getText(), tfMaxX.getText(), tfMinX.getText(), tfMaxY.getText(),
				tfMinY.getText(), tfMaxZ.getText(), tfMinZ.getText(), tfXLabel.getText(), tfYLabel.getText(),
				tfZLabel.getText(), cbLogScaleX.isSelected(), cbLogScaleY.isSelected(), cbLogScaleZ.isSelected());
	}
}
