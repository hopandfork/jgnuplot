package org.hopandfork.jgnuplot.gui.presenter.panel;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.hopandfork.jgnuplot.gui.dialog.ConsoleDialog;
import org.hopandfork.jgnuplot.model.Plot;

public interface BottomInterface {

	public JTextField getTitle();

	public JTextField getMaxX();

	public JTextField getMinX();

	public JTextField getMaxY();

	public JTextField getMinY();

	public JTextField getMaxZ();

	public JTextField getMinZ();

	public JRadioButton get2D();

	public JRadioButton get3D();

	public JTextField getXLabel();

	public JTextField getYLabel();

	public JTextField getZLabel();

	public JCheckBox getLogScaleX();

	public JCheckBox getLogScaleY();

	public JCheckBox getLogScaleZ();
	
	public JButton getPlotPs();
	
	public JButton getPlotString();
	
	public ConsoleDialog getConsoleDialog();
}
