/*
 * Copyright 2006, 2017 Maximilian H Fabricius, Hop and Fork.
 * 
 * This file is part of JGNUplot.
 * 
 * JGNUplot is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * JGNUplot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JGNUplot.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.hopandfork.jgnuplot.gui.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.hopandfork.jgnuplot.gui.dialog.ConsoleDialog;
import org.hopandfork.jgnuplot.gui.presenter.panel.BottomInterface;
import org.hopandfork.jgnuplot.gui.utility.GridBagConstraintsFactory;

public class BottomPanel extends JGPPanel
		implements BottomInterface {

	private static final long serialVersionUID = -7381866132142192657L;

//	private static Logger LOG = Logger.getLogger(BottomPanel.class);

	private JTextField tfTitle;

	private JRadioButton rb2D, rb3D;

	private JTextField tfMaxX, tfMinX, tfMaxY, tfMinY, tfMaxZ, tfMinZ;

	private JTextField tfXLabel, tfYLabel, tfZLabel;

	private JCheckBox cbLogScaleX, cbLogScaleY, cbLogScaleZ;
	
	private JButton bPlotPs, bPlotString;

	private ConsoleDialog consoleDialog;

	public BottomPanel() {
		createButtonPanel();
	}

	private void createButtonPanel() {

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

		GridBagConstraints gbc;

		int row = 0;
		gbc = GridBagConstraintsFactory.create(0, row, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(new JLabel("Title:"), gbc);
		gbc = GridBagConstraintsFactory.create(1, row, 4, 1, 1, 0, GridBagConstraints.HORIZONTAL);
		this.add(tfTitle, gbc);

		row += 1;
		gbc = GridBagConstraintsFactory.create(0, row, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(new JLabel("Type:"), gbc);
		gbc = GridBagConstraintsFactory.create(1, row, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(rb2D, gbc);
		gbc = GridBagConstraintsFactory.create(2, row, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(rb3D, gbc);

		row += 1;
		gbc = GridBagConstraintsFactory.create(0, row, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(new JLabel("X label"), gbc);
		gbc = GridBagConstraintsFactory.create(1, row, 2, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		this.add(tfXLabel, gbc);

		gbc = GridBagConstraintsFactory.create(3, row, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(new JLabel("min X"), gbc);
		gbc = GridBagConstraintsFactory.create(4, row, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(tfMinX, gbc);

		gbc = GridBagConstraintsFactory.create(5, row, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(new JLabel("max X"), gbc);
		gbc = GridBagConstraintsFactory.create(6, row, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(tfMaxX, gbc);

		gbc = GridBagConstraintsFactory.create(7, row, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(new JLabel("logscale X"), gbc);
		gbc = GridBagConstraintsFactory.create(8, row, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(cbLogScaleX, gbc);

		row += 1;
		gbc = GridBagConstraintsFactory.create(0, row, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(new JLabel("Y label"), gbc);
		gbc = GridBagConstraintsFactory.create(1, row, 2, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		this.add(tfYLabel, gbc);

		gbc = GridBagConstraintsFactory.create(3, row, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(new JLabel("min Y"), gbc);
		gbc = GridBagConstraintsFactory.create(4, row, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(tfMinY, gbc);

		gbc = GridBagConstraintsFactory.create(5, row, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(new JLabel("max Y"), gbc);
		gbc = GridBagConstraintsFactory.create(6, row, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(tfMaxY, gbc);

		gbc = GridBagConstraintsFactory.create(7, row, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(new JLabel("logscale Y"), gbc);
		gbc = GridBagConstraintsFactory.create(8, row, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(cbLogScaleY, gbc);

		row += 1;
		gbc = GridBagConstraintsFactory.create(0, row, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(new JLabel("Z label"), gbc);
		gbc = GridBagConstraintsFactory.create(1, row, 2, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		this.add(tfZLabel, gbc);

		gbc = GridBagConstraintsFactory.create(3, row, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(new JLabel("min Z"), gbc);
		gbc = GridBagConstraintsFactory.create(4, row, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(tfMinZ, gbc);

		gbc = GridBagConstraintsFactory.create(5, row, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(new JLabel("max Z"), gbc);
		gbc = GridBagConstraintsFactory.create(6, row, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(tfMaxZ, gbc);

		gbc = GridBagConstraintsFactory.create(7, row, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(new JLabel("logscale Z"), gbc);
		gbc = GridBagConstraintsFactory.create(8, row, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		this.add(cbLogScaleZ, gbc);

		row += 1;

		// Create the buttons.
		bPlotPs = new JButton("Export");
		bPlotPs.setActionCommand("plotps");

		bPlotString = new JButton("View script");
		bPlotString.setActionCommand("genplotcmds");

		this.add(bPlotPs, 0, row, 1, 1, GridBagConstraints.NONE);
		this.add(bPlotString, 1, row, 1, 1, GridBagConstraints.NONE);
	}

	@Override
	public JTextField getTitle() {
		return tfTitle;
	}

	@Override
	public JTextField getMaxX() {
		return tfMaxX;
	}

	@Override
	public JTextField getMinX() {
		return tfMinX;
	}

	@Override
	public JTextField getMaxY() {
		return tfMaxY;
	}

	@Override
	public JTextField getMinY() {
		return tfMinY;
	}

	@Override
	public JTextField getMaxZ() {
		return tfMaxZ;
	}

	@Override
	public JTextField getMinZ() {
		return tfMinZ;
	}

	@Override
	public JRadioButton get2D() {
		return rb2D;
	}

	@Override
	public JRadioButton get3D() {
		return rb3D;
	}

	@Override
	public JTextField getXLabel() {
		return tfXLabel;
	}

	@Override
	public JTextField getYLabel() {
		return tfYLabel;
	}

	@Override
	public JTextField getZLabel() {
		return tfZLabel;
	}

	@Override
	public JCheckBox getLogScaleX() {
		return cbLogScaleX;
	}

	@Override
	public JCheckBox getLogScaleY() {
		return cbLogScaleY;
	}

	@Override
	public JCheckBox getLogScaleZ() {
		return cbLogScaleZ;
	}

	@Override
	public ConsoleDialog getConsoleDialog() {
		return consoleDialog;
	}

	@Override
	public JButton getPlotPs() {
		return bPlotPs;
	}

	@Override
	public JButton getPlotString() {
		return bPlotString;
	}

	@Override
	public JPanel toJPanel() {
		return this;
	}
}
