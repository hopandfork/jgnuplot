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

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;
import org.hopandfork.jgnuplot.control.ConstantController;
import org.hopandfork.jgnuplot.model.Constant;
import org.hopandfork.jgnuplot.model.Constant;
import org.hopandfork.jgnuplot.model.Function;

public class ConstantsTableModel extends AbstractTableModel implements Observer {

	private static final long serialVersionUID = -2543760974372040136L;

	private static Logger LOG = Logger.getLogger(ConstantsTableModel.class);

	private String[] columnNames = { "Name", "Value" };

	private List<Constant> constants;

	private ConstantController constantController;

	/**
	 * This constructor allows to create a Model for the table of Constants
	 * related to a Function. To initialize the object a Function and
	 * ConstantController is needed.
	 * 
	 * @param constantController
	 * @param function could be a clean instance or an old to be modified.
	 */
	public ConstantsTableModel(ConstantController constantController, Function function) {
		this.constantController = constantController;
		this.constants = function.getConstants();
		constantController.addObserver(this);
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return constants.size();
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Constant getConstant(int rowIndex){
		return constants.get(rowIndex);
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Constant constant = constants.get(rowIndex);
		if (columnIndex == 0) {
			constant.setName((String) aValue);
		} else {
			constant.setValue((String) aValue);
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Constant constant = constants.get(rowIndex);
		if (columnIndex == 0) {
			return constant.getName();
		} else {
			return constant.getValue();
		}
	}

	public Class getColumnClass(int c) {
		return String.class;
	}

	/*
	 * Don't need to implement this method unless your table's editable.
	 */
	public boolean isCellEditable(int row, int col) {
		return true;
	}

	@Override
	public void update(Observable o, Object arg) {
		constants = (ArrayList<Constant>) constantController.getConstants((Function) arg);
		fireTableDataChanged();
	}
}
