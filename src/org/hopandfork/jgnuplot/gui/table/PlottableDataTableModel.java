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


import java.awt.Color;
import java.util.*;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;
import org.hopandfork.jgnuplot.control.PlottableDataController;
import org.hopandfork.jgnuplot.model.DataFile;
import org.hopandfork.jgnuplot.model.Function;
import org.hopandfork.jgnuplot.model.PlottableData;
import org.hopandfork.jgnuplot.model.style.PlottingStyle;


/**
 * Table model used for displaying PlottableData in a table.
 */
public class PlottableDataTableModel extends AbstractTableModel implements Observer {


	private static final long serialVersionUID = -6078450755616561995L;

	/**
	 * Logger.
	 */
	static private final Logger LOG = Logger.getLogger(PlottableDataTableModel.class);

	/**
	 * Controller for PlottableData management.
	 */
	private PlottableDataController controller;

	public PlottableDataTableModel(PlottableDataController controller) {
		this.controller = controller;
		controller.addObserver(this);
	}

	/*
	 * Column names.
	 */
	static private final String COL_TITLE = "Title";
	static private final String COL_COLOR = "Color";
	static private final String COL_STYLE = "Style";
	static private final String COL_ENABLED = "Enabled";
	final static private String[] columnNames = {
			COL_TITLE,
			COL_COLOR,
			COL_STYLE,
			COL_ENABLED
	};

	/*
	 * Column data type Class.
	 */
	final static private Class[] columnClasses = {
			String.class,
			Color.class,
			PlottingStyle.class,
			Boolean.class
	};


	/**
	 * List of PlottableData.
	 */
	private List<PlottableData> data = null;


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

		PlottableData plottableData = data.get(row);
		String columnName = columnNames[col];

		if (columnName.equals(COL_TITLE)) {
			String title = plottableData.getTitle();
			if (title == null || title.length() < 1) {
				if (plottableData instanceof Function)
					title = ((Function) plottableData).getFunctionString();
				else if (plottableData instanceof DataFile)
					title = ((DataFile) plottableData).getFileName();
			}
			return title;
		} else if (columnName.equals(COL_COLOR)) {
			return plottableData.getColor();
		} else if (columnName.equals(COL_STYLE)) {
			return plottableData.getStyle();
		} else {
			return plottableData.isEnabled();
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
		data = controller.getPlottableData();
		fireTableDataChanged();
	}

	public List<PlottableData> getSelectedPlottableData(int selectedRows[]) {
		List<PlottableData> selectedPlottableData = new ArrayList<>();
		for (int index : selectedRows) {
			if (index >= 0 && index < data.size())
				selectedPlottableData.add(data.get(index));
		}

		return selectedPlottableData;
	}

	public PlottableData getSelectedPlottableData(int index) {
		PlottableData selectedPlottableData = null;
		if (index >= 0 && index < data.size())
			selectedPlottableData = data.get(index);
		return selectedPlottableData;
	}
}
