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

	private BottomInterface bottomInterface = null;

	private PlotController plotController = null;

	public BottomPresenter(BottomInterface bottomInterface, PlotController plotController) {
		this.bottomInterface = bottomInterface;
		this.plotController = plotController;
		
		this.bottomInterface.getTitle().addKeyListener(this);
		this.bottomInterface.get2D().addItemListener(this);
		this.bottomInterface.getXLabel().addKeyListener(this);
		this.bottomInterface.getMinX().addKeyListener(this);
		this.bottomInterface.getMaxX().addKeyListener(this);
		this.bottomInterface.getLogScaleX().addChangeListener(this);
		this.bottomInterface.getYLabel().addKeyListener(this);
		this.bottomInterface.getMinY().addKeyListener(this);
		this.bottomInterface.getMaxY().addKeyListener(this);
		this.bottomInterface.getLogScaleY().addChangeListener(this);
		this.bottomInterface.getZLabel().addKeyListener(this);
		this.bottomInterface.getMinZ().addKeyListener(this);
		this.bottomInterface.getMaxZ().addKeyListener(this);
		this.bottomInterface.getLogScaleZ().addChangeListener(this);
		this.bottomInterface.getPlotPs().addActionListener(this);
		this.bottomInterface.getPlotString().addActionListener(this);
		
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
		boolean selected = bottomInterface.get2D().isSelected();
		bottomInterface.getMaxZ().setEnabled(!selected);
		bottomInterface.getMinZ().setEnabled(!selected);
		bottomInterface.getZLabel().setEnabled(!selected);
		bottomInterface.getLogScaleZ().setEnabled(!selected);

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
		bottomInterface.get2D().setSelected(plot.getMode().equals(Mode.PLOT_2D));
		bottomInterface.get3D().setSelected(plot.getMode().equals(Mode.PLOT_3D));

		bottomInterface.getTitle().setText(str(plot.getTitle()));

		bottomInterface.getMaxX().setText(str(plot.getXmax()));
		bottomInterface.getMaxY().setText(str(plot.getYmax()));
		bottomInterface.getMinX().setText(str(plot.getXmin()));
		bottomInterface.getMinY().setText(str(plot.getYmin()));

		bottomInterface.getXLabel().setText(str(plot.getXlabel()));
		bottomInterface.getYLabel().setText(str(plot.getYlabel()));
		bottomInterface.getZLabel().setText(str(plot.getZlabel()));

		bottomInterface.getLogScaleX().setSelected(plot.isLogScaleX());
		bottomInterface.getLogScaleY().setSelected(plot.isLogScaleY());
		bottomInterface.getLogScaleZ().setSelected(plot.isLogScaleZ());
	}

	public void reset() {
		bottomInterface.get2D().setSelected(true);
		bottomInterface.get3D().setSelected(false);

		bottomInterface.getTitle().setText("");

		bottomInterface.getMaxX().setText("");
		bottomInterface.getMinX().setText("");
		bottomInterface.getMaxY().setText("");
		bottomInterface.getMinY().setText("");

		bottomInterface.getXLabel().setText("");
		bottomInterface.getYLabel().setText("");
		bottomInterface.getZLabel().setText("");

		bottomInterface.getLogScaleX().setSelected(false);
		bottomInterface.getLogScaleX().setSelected(false);
		bottomInterface.getLogScaleZ().setSelected(false);
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
		Mode mode = bottomInterface.get2D().isSelected() ? Mode.PLOT_2D : Mode.PLOT_3D;
		plotController.updatePlot(mode, bottomInterface.getTitle().getText(), bottomInterface.getMaxX().getText(),
				bottomInterface.getMinX().getText(), bottomInterface.getMaxY().getText(),
				bottomInterface.getMinY().getText(), bottomInterface.getMaxZ().getText(),
				bottomInterface.getMinZ().getText(), bottomInterface.getXLabel().getText(),
				bottomInterface.getYLabel().getText(), bottomInterface.getZLabel().getText(),
				bottomInterface.getLogScaleX().isSelected(), bottomInterface.getLogScaleY().isSelected(),
				bottomInterface.getLogScaleZ().isSelected());
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
		ConsoleDialog consoleDialog = bottomInterface.getConsoleDialog();
		
		if (consoleDialog == null)
			consoleDialog = new ConsoleDialog();
		if (makeVisible)
			consoleDialog.setVisible(true);

		if (append)
			text = consoleDialog.getText() + "\n" + text;
		consoleDialog.setText(text);
	}
}
