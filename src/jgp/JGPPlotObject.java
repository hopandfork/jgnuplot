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


public abstract class JGPPlotObject  implements JGPPlotable {

	public String title;
	public boolean doPlot = true;
	public jgp.JGPStyle style;
	
	public JGPColor color;
	
	//additional style options like linecolor, pointsize a.s.o.
	public String addStyleOpt;

	public boolean getDoPlot() {
		return this.doPlot;
	}

	public String getTitle() {
		return title;
	}

	public JGPStyle getStyle() {
		return this.style;
	}

	public void setDoPlot(boolean doPlot){
		this.doPlot = doPlot;
	}
	
	public abstract String getDataString() ;

	public abstract String getPlotString();

	public void setStyle(JGPStyle s) {
		this.style = s;
	}

	public abstract String getFileName();

	public abstract  Object[] getData();

	public abstract  void setData(int i, Object value);

	public abstract  void setFileName(String s);

	public abstract  void setDataString(String function);

	public void setTitle(String title){
		this.title = title;
	}

	public String getAddStyleOpt() {
		return addStyleOpt;
	}

	public void setAddStyleOpt(String addStyleOpt) {
		this.addStyleOpt = addStyleOpt;
	}

	public JGPColor getColor() {
		return color;
	}

	public void setColor(JGPColor color) {
		this.color = color;
	}


}
