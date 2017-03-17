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

package jgp;


public interface JGPPlotable {
	
	public boolean getDoPlot();

	public void setDoPlot(boolean doPLot);
	
	public String getDataString();
	
	public String getPlotString();
	
	public String getPreProcessProgram();

	public String getTitle();

	public JGPStyle getStyle();

	public void  setStyle(JGPStyle s);

	public String getAddStyleOpt();

	public void  setAddStyleOpt(String s);

	public String getFileName();

	public Object[] getData();
	
	public void setData(int i, Object value);
	
	public void setFileName(String s);
	
	public void setDataString(String function);

	public void setTitle(String title);
	
	public JGPPlotable getClone();

	public JGPColor getColor();

	public void setColor(JGPColor color);

	public void setPreProcessProgram(String textContent);

	
}
