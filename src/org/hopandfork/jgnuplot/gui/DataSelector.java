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

package org.hopandfork.jgnuplot.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;
import org.hopandfork.jgnuplot.gui.panel.JGPPanel;
import org.hopandfork.jgnuplot.gui.utility.TableUtils;
import org.hopandfork.jgnuplot.model.DataSelection;
import org.hopandfork.jgnuplot.model.DataSelection2D;
import org.hopandfork.jgnuplot.model.DataSelection3D;
import org.hopandfork.jgnuplot.utility.FileColumnsParser;

public class DataSelector extends JGPPanel {

	private static Logger LOG = Logger.getLogger(DataSelector.class);

	private static final long serialVersionUID = 853945391719595729L;

	private String fileName;

	private JTable jTable;
	private JScrollPane jScrollPane;

	private JButton bX, bY, bZ, bLabels, bCurrent;

	private JTextField tfX, tfY, tfZ, tfLabels, tfCurrent;

	private DataSelection dataSelection;

	public DataSelector() {
		super();
		createMainPanel();
	}

	/**
	 * This methods allows to render the component elements.
	 */
	private void createMainPanel() {
		// Set the default panel layout.
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);

		bX = new JButton("x");
		bCurrent = bX;
		bCurrent.setSelected(true);
		bX.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseClicked(MouseEvent e) {
				tfCurrent = tfX;
				bCurrent.setSelected(false);
				bCurrent = bX;
				bX.setSelected(true);
			}
		});
		tfX = new JTextField("0");
		tfX.setEditable(false);
		tfCurrent = tfX;

		bY = new JButton("y");
		bY.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseClicked(MouseEvent e) {
				tfCurrent = tfY;
				bCurrent.setSelected(false);
				bCurrent = bY;
				bY.setSelected(true);
			}
		});
		tfY = new JTextField("0");
		tfY.setEditable(false);

		bZ = new JButton("z");
		bZ.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseClicked(MouseEvent e) {
				tfCurrent = tfZ;
				bCurrent.setSelected(false);
				bCurrent = bZ;
				bZ.setSelected(true);
			}
		});
		tfZ = new JTextField("0");
		tfZ.setEditable(false);

		bLabels = new JButton("labels");
		bLabels.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseClicked(MouseEvent e) {
				tfCurrent = tfLabels;
				bCurrent.setSelected(false);
				bCurrent = bLabels;
				bLabels.setSelected(true);
			}
		});
		tfLabels = new JTextField("0");
		tfLabels.setEditable(false);

		/* Adds component to the JScrollPane */
		this.add(bX, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL);
		this.add(tfX, 1, 0, 1, 1, GridBagConstraints.HORIZONTAL);
		this.add(bY, 2, 0, 1, 1, GridBagConstraints.HORIZONTAL);
		this.add(tfY, 3, 0, 1, 1, GridBagConstraints.HORIZONTAL);
		this.add(bZ, 4, 0, 1, 1, GridBagConstraints.HORIZONTAL);
		this.add(tfZ, 5, 0, 1, 1, GridBagConstraints.HORIZONTAL);
		this.add(bLabels, 6, 0, 1, 1, GridBagConstraints.HORIZONTAL);
		this.add(tfLabels, 7, 0, 1, 1, GridBagConstraints.HORIZONTAL);
		jScrollPane = new JScrollPane(jTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.add(jScrollPane, 0, 1, 9, 1, GridBagConstraints.HORIZONTAL);
	}

	/**
	 * 
	 * @return
	 */
	public DataSelection getDataSelection() {
		int x = Integer.parseInt(tfX.getText());
		int y = Integer.parseInt(tfY.getText());
		int z = Integer.parseInt(tfZ.getText());
		int labels = Integer.parseInt(tfLabels.getText());

		if (z != 0) {
			try {
				dataSelection = new DataSelection3D(x, y, z);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			if (labels != 0) {
				try {
					dataSelection = new DataSelection2D(x, y, labels);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				try {
					dataSelection = new DataSelection2D(x, y);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		return dataSelection;
	}

	/**
	 * This method allows to update the data selected.
	 * 
	 * @param dataSelection
	 */
	public void updateDataSelection(DataSelection dataSelection) {
		tfX.setText("" + dataSelection.getX());
		tfY.setText("" + dataSelection.getY());
		
		if (dataSelection instanceof DataSelection2D) {
			tfLabels.setText("" + ((DataSelection2D) dataSelection).getLabels());
			bZ.setVisible(false);
			tfZ.setVisible(false);
		} else {
			tfZ.setText(""+((DataSelection3D)dataSelection).getZ());
			bLabels.setVisible(false);
			tfLabels.setVisible(false);
		}
	}

	private void initTable(Object data[][], String columns[]) {
		jTable = new JTable(data, columns);

		jTable.setPreferredScrollableViewportSize(new Dimension(500, 200));
		jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jTable.setColumnSelectionAllowed(true);
		jTable.setRowSelectionAllowed(false);
		jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		// react to column selection
		ListSelectionModel selectionModel = jTable.getColumnModel().getSelectionModel();
		selectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {

				if (e.getValueIsAdjusting()) {
					tfCurrent.setText("" + (jTable.getSelectedColumn() + 1));

					if (tfCurrent.equals(tfLabels)) {
						bZ.setVisible(false);
						tfZ.setVisible(false);
					} else if (tfCurrent.equals(tfZ)) {
						bLabels.setVisible(false);
						tfLabels.setVisible(false);
					}
				}
			}
		});

		TableUtils.packColumns(jTable);
	}

	public void update(String fileName) {
		this.fileName = fileName;
		LOG.debug("Updating from file: " + fileName);

		try {
			List<String[]> parsedLines = new FileColumnsParser(fileName).parse();
			LOG.debug(String.format("Found %d lines in the file.", parsedLines.size()));

			/*
			 * Prepares data for the new table.
			 */
			String data[][] = new String[parsedLines.size()][];
			int columns = 0;
			for (int i = 0; i < parsedLines.size(); i++) {
				columns = Math.max(columns, parsedLines.get(i).length);
			}
			for (int i = 0; i < parsedLines.size(); i++) {
				if (parsedLines.get(i).length == columns) {
					data[i] = parsedLines.get(i);
				} else {
					data[i] = new String[columns];
					for (int j = 0; j < parsedLines.get(i).length; j++)
						data[i][j] = parsedLines.get(i)[j];
					for (int j = parsedLines.get(i).length; j < columns; j++)
						data[i][j] = "";
				}
			}

			/* Creates column headers. */
			String columnHeaders[] = new String[columns];
			for (int i = 0; i < columns; i++)
				columnHeaders[i] = "#" + (i + 1);

			initTable(data, columnHeaders);

			/*
			 * Replaces table in the UI.
			 */
			if (jScrollPane.getViewport().getComponentCount() > 0)
				jScrollPane.getViewport().remove(0);
			jScrollPane.getViewport().add(jTable);
		} catch (IOException e) {
			LOG.warn("Could not parse file: " + fileName);
		}

	}
}
