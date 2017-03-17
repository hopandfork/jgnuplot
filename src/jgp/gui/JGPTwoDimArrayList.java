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

public class JGPTwoDimArrayList<DataType> {
	
	private ArrayList<ArrayList<DataType>> rows;
	
	private int ncols = 0;
	
	private int nrows = 0;
	
	
	public JGPTwoDimArrayList() {
		rows = new ArrayList<ArrayList<DataType>>();
	}

	public DataType get(int row, int col){
		return rows.get(row).get(col); 
	}
	
	public void addRow(){
		ArrayList<DataType> r = new ArrayList<DataType>();
		for (int i = 0; i < ncols; i++)
			r.add(null);
		rows.add(r);
		nrows++;
	}

	public void addCol(){
		for (int i = 0; i < nrows; i++)
			rows.get(i).add(null);
		ncols++;
	}

	public void set(int row, int col, DataType value){
		while (row >= nrows) addRow();
		while (col >= ncols) addCol();
		
		rows.get(row).set(col, value);
	}
	
	
    public static void main(String[] args) {
    	JGPTwoDimArrayList<String> a = new JGPTwoDimArrayList<String>();
    	a.set(0, 0, "zero,zero");
    	a.set(0, 1, "zero,one");
    	a.set(1, 0, "one,zero");
    	a.set(3, 3, "two,two");
    	System.out.println(a);
    }

	@Override
	public String toString() {
		String s = "";
		for (int row = 0; row < nrows; row++){
			for (int col = 0; col < ncols; col++){
				if (get(row,col) == null) s += ";";
				else s += get(row,col) + ";";
			}
			s += "\n";
		}
		
		return s;
	}

	public int getNcols() {
		return ncols;
	}

	public int getNrows() {
		return nrows;
	}

}
