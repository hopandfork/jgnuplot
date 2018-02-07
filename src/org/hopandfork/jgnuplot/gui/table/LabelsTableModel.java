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

package org.hopandfork.jgnuplot.gui.table;


import org.apache.log4j.Logger;
import org.hopandfork.jgnuplot.control.LabelController;
import org.hopandfork.jgnuplot.model.Label;
import org.hopandfork.jgnuplot.model.RelativePosition;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


/**
 * Table model used for displaying Labels in a table.
 */
public class LabelsTableModel extends AbstractTableModel implements Observer {


	private static final long serialVersionUID = -6078450755616561995L;

	/**
	 * Logger.
	 */
	static private final Logger LOG = Logger.getLogger(LabelsTableModel.class);

	/**
	 * Controller for Label management.
	 */
	private LabelController controller;

	public LabelsTableModel(LabelController controller) {
		this.controller = controller;
		controller.addObserver(this);
	}

	/*
	 * Column names.
	 */
	static private final String COL_TITLE = "Text";
	static private final String COL_POSITION = "Position";
	static private final String COL_X = "X";
	static private final String COL_Y = "Y";
	final static private String[] columnNames = {
			COL_TITLE,
			COL_POSITION,
			COL_X,
			COL_Y
	};

	/*
	 * Column data type Class.
	 */
	final static private Class[] columnClasses = {
			String.class,
			RelativePosition.class,
			Double.class,
			Double.class
	};


	/**
	 * List of Label.
	 */
	private List<Label> data = null;


	/**
	 * Returns the count of available columns.
	 *
	 * @return Count of available columns.
	 */
	public int getColumnCount() {
		return columnNames.length;
	}

	/**
	 * Returns the count of existing rows.
	 *
	 * @return Count of existing rows.
	 */
	public int getRowCount() {
		if (data == null)
			return 0;

		return data.size();
	}

	/**
	 * Returns the name of i-th column.
	 *
	 * @param i Index of the column.
	 * @return Name of the column.
	 */
	public String getColumnName(int i) {
		return columnNames[i];
	}

	/**
	 * Returns the content of a table cell.
	 *
	 * @param row Index of the row.
	 * @param col Index of the column.
	 * @return Content of the cell.
	 */
	public Object getValueAt(int row, int col) {
		if (data == null)
			return null;

		Label label = data.get(row);
		String columnName = columnNames[col];

		if (columnName.equals(COL_TITLE)) {
			return label.getText();
		} else if (columnName.equals(COL_POSITION)) {
			return label.getRelativePos();
		} else if (columnName.equals(COL_X)) {
			return label.getX();
		} else {
			return label.getY();
		}
	}


	/**
	 * Returns the type of a column data.
	 *
	 * @param c Column index.
	 * @return Type of the column data.
	 */
	public Class getColumnClass(int c) {
		return columnClasses[c];
	}

	/**
	 * Returns true if the given cell can be edited.
	 *
	 * @param row Row index.
	 * @param col Column index.
	 * @return true if the cell can be edited.
	 */
	public boolean isCellEditable(int row, int col) {
		return false; /* at the moment, we don't allow editing */
	}

	/**
	 * Updates the table on change in the model.
	 *
	 * @param observable Model subject to change.
	 * @param o          Optional object associated to the notification.
	 */
	public void update(Observable observable, Object o) {
		data = controller.getLabels();
		fireTableDataChanged();
	}

	public List<Label> getSelectedLabels(int selectedRows[]) {
		List<Label> selectedLabels = new ArrayList<>();
		for (int index : selectedRows) {
			if (index >= 0 && index < data.size())
				selectedLabels.add(data.get(index));
		}

		return selectedLabels;
	}

	public Label getSelectedLabel(int index) {
		Label label = null;
		if (index >= 0 && index < data.size())
			label = data.get(index);
		return label;
	}
}
