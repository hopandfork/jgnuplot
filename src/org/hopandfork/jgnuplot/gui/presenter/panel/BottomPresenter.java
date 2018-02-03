package org.hopandfork.jgnuplot.gui.presenter.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.hopandfork.jgnuplot.control.PlotController;
import org.hopandfork.jgnuplot.gui.dialog.ConsoleDialog;
import org.hopandfork.jgnuplot.gui.dialog.PlotDialog;
import org.hopandfork.jgnuplot.model.Plot;
import org.hopandfork.jgnuplot.model.Plot.Mode;

public class BottomPresenter implements ActionListener, KeyListener, ItemListener, ChangeListener, Observer {

	private BottomInterface bottomPanel = null;

	private PlotController plotController = null;

	public BottomPresenter(BottomInterface bottomPanel, PlotController plotController) {
		this.bottomPanel = bottomPanel;
		this.plotController = plotController;
		
		bottomPanel.getTitle().addKeyListener(this);
		bottomPanel.get2D().addItemListener(this);
		bottomPanel.getXLabel().addKeyListener(this);
		bottomPanel.getMinX().addKeyListener(this);
		bottomPanel.getMaxX().addKeyListener(this);
		bottomPanel.getLogScaleX().addChangeListener(this);
		bottomPanel.getYLabel().addKeyListener(this);
		bottomPanel.getMinY().addKeyListener(this);
		bottomPanel.getMaxY().addKeyListener(this);
		bottomPanel.getLogScaleY().addChangeListener(this);
		bottomPanel.getZLabel().addKeyListener(this);
		bottomPanel.getMinZ().addKeyListener(this);
		bottomPanel.getMaxZ().addKeyListener(this);
		bottomPanel.getLogScaleZ().addChangeListener(this);
		bottomPanel.getPlotPs().addActionListener(this);
		bottomPanel.getPlotString().addActionListener(this);
		
		this.plotController.addObserver(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("plotps")) {
			new PlotDialog(plotController).setVisible(true);
		} else if (e.getActionCommand().equals("genplotcmds")) {
			acGenPlotCmds();
		}
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
		boolean selected = bottomPanel.get2D().isSelected();
		bottomPanel.getMaxZ().setEnabled(!selected);
		bottomPanel.getMinZ().setEnabled(!selected);
		bottomPanel.getZLabel().setEnabled(!selected);
		bottomPanel.getLogScaleZ().setEnabled(!selected);

		updatePlot();
	}

	@Override
	public void stateChanged(ChangeEvent changeEvent) {
		updatePlot();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Updates if concurrent changes are allowed
	}
	
	public void initialize (Plot plot)
	{
		bottomPanel.get2D().setSelected(plot.getMode().equals(Mode.PLOT_2D));
		bottomPanel.get3D().setSelected(plot.getMode().equals(Mode.PLOT_3D));

		bottomPanel.getTitle().setText(str(plot.getTitle()));

		bottomPanel.getMaxX().setText(str(plot.getXmax()));
		bottomPanel.getMaxY().setText(str(plot.getYmax()));
		bottomPanel.getMinX().setText(str(plot.getXmin()));
		bottomPanel.getMinY().setText(str(plot.getYmin()));

		bottomPanel.getXLabel().setText(str(plot.getXlabel()));
		bottomPanel.getYLabel().setText(str(plot.getYlabel()));
		bottomPanel.getZLabel().setText(str(plot.getZlabel()));

		bottomPanel.getLogScaleX().setSelected(plot.isLogScaleX());
		bottomPanel.getLogScaleY().setSelected(plot.isLogScaleY());
		bottomPanel.getLogScaleZ().setSelected(plot.isLogScaleZ());
	}

	public void reset() {
		bottomPanel.get2D().setSelected(true);
		bottomPanel.get3D().setSelected(false);

		bottomPanel.getTitle().setText("");

		bottomPanel.getMaxX().setText("");
		bottomPanel.getMinX().setText("");
		bottomPanel.getMaxY().setText("");
		bottomPanel.getMinY().setText("");

		bottomPanel.getXLabel().setText("");
		bottomPanel.getYLabel().setText("");
		bottomPanel.getZLabel().setText("");

		bottomPanel.getLogScaleX().setSelected(false);
		bottomPanel.getLogScaleX().setSelected(false);
		bottomPanel.getLogScaleZ().setSelected(false);
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
	
	private void updatePlot() {
		Mode mode = bottomPanel.get2D().isSelected() ? Mode.PLOT_2D : Mode.PLOT_3D;
		plotController.updatePlot(mode, bottomPanel.getTitle().getText(), bottomPanel.getMaxX().getText(),
				bottomPanel.getMinX().getText(), bottomPanel.getMaxY().getText(),
				bottomPanel.getMinY().getText(), bottomPanel.getMaxZ().getText(),
				bottomPanel.getMinZ().getText(), bottomPanel.getXLabel().getText(),
				bottomPanel.getYLabel().getText(), bottomPanel.getZLabel().getText(),
				bottomPanel.getLogScaleX().isSelected(), bottomPanel.getLogScaleY().isSelected(),
				bottomPanel.getLogScaleZ().isSelected());
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
		ConsoleDialog consoleDialog = bottomPanel.getConsoleDialog();
		
		if (consoleDialog == null)
			consoleDialog = new ConsoleDialog();
		if (makeVisible)
			consoleDialog.setVisible(true);

		if (append)
			text = consoleDialog.getText() + "\n" + text;
		consoleDialog.setText(text);
	}
}
