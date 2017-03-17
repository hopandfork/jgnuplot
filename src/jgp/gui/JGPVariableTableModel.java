/*
 * JGNUplot is a GUI for gnuplot (http://www.gnuplot.info/)
 * The GUI is build on JAVA wrappers for gnuplot alos provided in this package.
 * 
 * Copyright (C) 2006  Maximilian H. Fabricius 
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package jgp.gui;


import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import jgp.JGPGnuplotVariable;
import jgp.JGPStringVariable;
import jgp.JGPVariable;

public class JGPVariableTableModel  extends AbstractTableModel  {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -2543760974372040136L;

	private String[] columnNames = {"Type",
               "Name",
               "value",
               "active"};

	public ArrayList<JGPVariable> variables = new ArrayList<JGPVariable> ();
	
	public void addRow(JGPVariable lbl){
		variables.add(lbl);
		fireTableDataChanged();
	}
	
	public int getColumnCount() {
		return columnNames.length;
	}
	
	public int getRowCount() {
		return variables.size();
	}
	
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	public Object getValueAt(int row, int col) {
		return variables.get(row).getData()[col];
	}
	
	/*
	* Don't need to implement this method unless your table's
	* data can change.
	*/
	public void setValueAt(Object value, int row, int col) {
		boolean DEBUG = true;
		
		if (DEBUG) {
		System.out.println("Setting value at " + row + "," + col
		          + " to " + value
		          + " (an instance of "
		          + value.getClass() + ")");
		}
		
		if (col == 0){ 
			//uh, now we have to replace the Variable with a variable of different
			// class
			JGPVariable v = null;
			if (value.equals(JGPVariable.Type.STRING))
				v = new JGPStringVariable();
			else 
				v = new JGPGnuplotVariable();
			
			for (int i = 0; i < 4; i++){
				v.setData(i, variables.get(row).getData()[i]);
			}
			
			variables.set(row, v);
		}
		else {
			variables.get(row).getData()[col] = value;
			variables.get(row).setData(col, value);
		}
		fireTableCellUpdated(row, col);
	}
	
	
	/*
	* JTable uses this method to determine the default renderer/
	* editor for each cell.  If we didn't implement this method,
	* then the last column would contain text ("true"/"false"),
	* rather than a check box.
	*/
	@SuppressWarnings("unchecked")
	public Class getColumnClass(int c) {
		Object o = getValueAt(0, c);
		Class cl;
		if (o != null) cl = o.getClass();
		else cl = String.class;
		
		//System.out.println(cl);
		return cl;
	}
	
	/*
	* Don't need to implement this method unless your table's
	* editable.
	*/
	public boolean isCellEditable(int row, int col) {
		//Note that the data/cell address is constant,
		//no matter where the cell appears onscreen.
		//if (col < 2) {
		//return false;
		//} else {
		//return true;
		//}
		return true;   
	}

}
