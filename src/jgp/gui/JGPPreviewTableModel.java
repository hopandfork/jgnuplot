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

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.StringTokenizer;

import javax.swing.table.AbstractTableModel;

public class JGPPreviewTableModel extends AbstractTableModel {

	private JGPTwoDimArrayList<String> data;
	
	private String[] columnNames;
	
	public JGPPreviewTableModel(String fileName) throws IOException {
			parse(fileName);
			
			columnNames = new String[data.getNcols()];
			for (int i = 0; i < data.getNcols(); i++){
				columnNames[i] = "$" + (i+1);
			}
	}
    
	public String getColumnName(int col) {
        return columnNames[col];
    }
    

	    
	    
	private void parse(String fileName) throws IOException{
		data = new JGPTwoDimArrayList<String>();
		
		RandomAccessFile inFile;

		// Open the file and output.
		inFile = new RandomAccessFile(fileName, "r");

	
		String line;
		
		
		//commented lines
		line = inFile.readLine();
		int n = 0;

		while (line != null) {
			//StringTokenizer st = new StringTokenizer(line, ",");
			parseLine(n, line);
			line = inFile.readLine();
			n++;
		}
	}
	
	public JGPTwoDimArrayList<String> getData() {
		return data;
	}

	private void parseLine(int n, String line){
		StringTokenizer st = new StringTokenizer(line, ",;|\t");
		int col = 0;
		String s;
		while(st.hasMoreElements()){
			s = st.nextToken();
			if (!s.trim().equals("")){
				data.set(n, col, s);
				col++;
			}
		}
	}
	
	
	public int getColumnCount() {
		// TODO Auto-generated method stub
		//System.out.println("PreviewTableModel.getColumnCount(): " + data.getNcols());
		return data.getNcols();
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		//System.out.println("PreviewTableModel.getRowCount(): " + data.getNrows());
		return data.getNrows();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		//System.out.println("PreviewTableModel.getValueAt(" + rowIndex + "," + columnIndex + "): " + data.getNrows());
		return data.get(rowIndex, columnIndex);
	}
	
    public static void main(String[] args) throws IOException {
    	JGPPreviewTableModel ptm = new JGPPreviewTableModel("test.dat");
      	System.out.println(ptm.getData().toString());
    }
}
