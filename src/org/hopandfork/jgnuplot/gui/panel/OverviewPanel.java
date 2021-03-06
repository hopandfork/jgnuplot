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

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import org.apache.log4j.Logger;
import org.hopandfork.jgnuplot.gui.presenter.panel.OverviewInterface;
import org.hopandfork.jgnuplot.gui.utility.GridBagConstraintsFactory;

public class OverviewPanel extends JGPPanel implements OverviewInterface {

	private static final long serialVersionUID = 3708875306419236700L;

	static final Logger LOG = Logger.getLogger(OverviewPanel.class);

	private JTabbedPane tp;

	private JTable plottableDataTable;

	private JButton buttonAddLabel, buttonEditLabel, buttonDeleteLabel, buttonAddDataFile, buttonAddFunction,
			buttonEdit, buttonDelete, buttonMoveUp, buttonMoveDown;

	private JTable labelTable;

	private JTextArea prePlotString;

	public OverviewPanel() {
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

		labelTable = new JTable();
		labelTable.setPreferredScrollableViewportSize(new Dimension(500, 200));
		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(labelTable);

		buttonAddLabel = new JButton("Add");
		gbc = GridBagConstraintsFactory.create(0, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		jp.add(buttonAddLabel, gbc);

		buttonEditLabel = new JButton("Edit");
		gbc = GridBagConstraintsFactory.create(1, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		jp.add(buttonEditLabel, gbc);

		buttonDeleteLabel = new JButton("Delete");
		gbc = GridBagConstraintsFactory.create(2, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		jp.add(buttonDeleteLabel, gbc);

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

		plottableDataTable = new JTable();
		plottableDataTable.setPreferredScrollableViewportSize(new Dimension(500, 200));

		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(plottableDataTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		buttonAddDataFile = new JButton("Add datafile");
		gbc = GridBagConstraintsFactory.create(0, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		jp.add(buttonAddDataFile, gbc);

		buttonAddFunction = new JButton("Add function");
		gbc = GridBagConstraintsFactory.create(1, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		jp.add(buttonAddFunction, gbc);

		buttonEdit = new JButton("Edit");
		gbc = GridBagConstraintsFactory.create(2, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		jp.add(buttonEdit, gbc);

		buttonDelete = new JButton("Delete");
		gbc = GridBagConstraintsFactory.create(3, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		jp.add(buttonDelete, gbc);

		buttonMoveUp = new JButton("Up");
		gbc = GridBagConstraintsFactory.create(4, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		jp.add(buttonMoveUp, gbc);

		buttonMoveDown = new JButton("Down");
		gbc = GridBagConstraintsFactory.create(5, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
		jp.add(buttonMoveDown, gbc);

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
	public Dimension getMinimumSize() {
		int height = 100;
		int width = 100 + tp.getMinimumSize().width;
		return new Dimension(width, height);
	}

	@Override
	public JPanel toJPanel() {
		return this;
	}

	@Override
	public JButton getButtonAddLabel() {
		return buttonAddLabel;
	}

	@Override
	public JButton getButtonEditLabel() {
		return buttonEditLabel;
	}

	@Override
	public JButton getButtonDeleteLabel() {
		return buttonDeleteLabel;
	}

	@Override
	public JButton getButtonAddDataFile() {
		return buttonAddDataFile;
	}

	@Override
	public JButton getButtonAddFunction() {
		return buttonAddFunction;
	}

	@Override
	public JButton getButtonEdit() {
		return buttonEdit;
	}

	@Override
	public JButton getButtonDelete() {
		return buttonDelete;
	}

	@Override
	public JButton getButtonMoveUp() {
		return buttonMoveUp;
	}

	@Override
	public JButton getButtonMoveDown() {
		return buttonMoveDown;
	}

	@Override
	public JTable getPlottableDataTable() {
		return plottableDataTable;
	}

	@Override
	public JTable getLabelTable() {
		return labelTable;
	}

	@Override
	public JTextArea getPrePlotString() {
		return prePlotString;
	}

	@Override
	public boolean isLabelShown() {
		int index = this.tp.getSelectedIndex();
		if (index == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isPlottableDataShown() {
		int index = this.tp.getSelectedIndex();
		if (index == 0) {
			return true;
		}
		return false;
	}
}
