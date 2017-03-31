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

package org.hopandfork.jgnuplot.runtime;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.hopandfork.jgnuplot.data.PlottableItem;
import org.hopandfork.jgnuplot.plot.GnuplotVariable;
import org.hopandfork.jgnuplot.plot.Label;
import org.hopandfork.jgnuplot.plot.OutputFileFormat;
import org.hopandfork.jgnuplot.plot.StringVariable;
import org.hopandfork.jgnuplot.plot.Variable;

public class GnuplotExecutor {

	public enum PlotType {
		TWO_DIM, THREE_DIM
	}

	private JGPPrintWriter out = null;

	private static final String TEMP_DIR = "/tmp";

	public String plotFileName = "work.gnuplot";

	public ArrayList<PlottableItem> dataSets;

	public ArrayList<Label> labels;

	public ArrayList<Variable> variables;

	public String prePlotString;

	public String title;

	public String xlabel;

	public String ylabel;

	public String zlabel;

	public Double xmin;

	public Double xmax;

	public Double ymin;

	public Double ymax;

	public Double zmin;

	public Double zmax;

	public boolean logScaleX = false;

	public boolean logScaleY = false;

	public boolean logScaleZ = false;

	public boolean psColor = false;

	public int psFontSize = 18;

	public String psFontName = "";

	public static int tmpFilnameCounter = 0;

	public PlotType plotType;

	public GnuplotExecutor() {
		super();
		dataSets = new ArrayList<PlottableItem>();
		labels = new ArrayList<Label>();
		variables = new ArrayList<Variable>();
	}

	public String getPlotString() {
		String s = "";

		s += prePlotString;

		// Add gnuplot variables to plot string
		for (int i = 0; i < variables.size(); i++) {
			if (variables.get(i).getType().equals(Variable.Type.GNUPLOT)) {
				if (variables.get(i).isActive())
					s += ((GnuplotVariable) variables.get(i)).getPlotString() + "\n";
			}
		}

		if (title == null)
			s += "unset title \n";
		else
			s += ("set title \"" + title + "\" \n");

		if (xlabel == null)
			s += ("unset xlabel \n");
		else
			s += ("set xlabel \"" + xlabel + "\" \n");

		if (ylabel == null)
			s += ("unset ylabel \n");
		else
			s += ("set ylabel \"" + ylabel + "\" \n");

		if (zlabel == null)
			s += ("unset zlabel \n");
		else
			s += ("set zlabel \"" + zlabel + "\" \n");

		if (logScaleX)
			s += ("set logscale x \n");
		else
			s += ("unset logscale x \n");

		if (logScaleY)
			s += ("set logscale y \n");
		else
			s += ("unset logscale y \n");

		if (logScaleZ)
			s += ("set logscale z \n");
		else
			s += ("unset logscale z \n");

		for (int i = 0; i < labels.size(); i++) {
			if (labels.get(i).getDoPlot()) {
				s += (labels.get(i).getPlotString());
				s += ("\n");
			}

		}

		if (plotType == PlotType.TWO_DIM)
			s += ("plot ");
		else
			s += ("splot ");

		s += "[";
		if (xmin != null)
			s += xmin;
		s += ":";
		if (xmax != null)
			s += xmax;
		s += "] ";

		s += "[";
		if (ymin != null)
			s += ymin;
		s += ":";
		if (ymax != null)
			s += ymax;
		s += "] ";

		if (plotType == PlotType.TWO_DIM) {
			s += "[";
			if (zmin != null)
				s += zmin;
			s += ":";
			if (zmax != null)
				s += zmax;
			s += "] ";
		}

		for (int i = 0; i < dataSets.size(); i++) {
			if (dataSets.get(i).isEnabled()) {
				s += (dataSets.get(i).getPlotString());
				s += (", ");
			}

		}

		s = s.trim();
		// is the a ',' to much at the end?
		if (s.lastIndexOf(",") == s.length() - 1)
			s = s.substring(0, s.length() - 1);
		s += (" \n");

		// Now replace all string variables with their value
		for (int i = 0; i < variables.size(); i++) {
			if (variables.get(i).getType() == Variable.Type.STRING) {
				if (variables.get(i).isActive())
					s = ((StringVariable) variables.get(i)).apply(s);
			}
		}

		return s;
	}

	public void plotThreaded() throws IOException, InterruptedException {
		String s = "";
		s += "set terminal X11 \n";
		s += getPlotString();
		// this we have to add to keep the plot window open
		// s += ("pause -1 'plotting done' \n");
		s += ("pause -1\n");

		System.out.println("Calling GNUPlotRunner...");
		GNUPlotRunner pr = new GNUPlotRunner(s,out);
		new Thread(pr).start();

	}

	public void printThreaded(String printCmd, String printFile) throws IOException, InterruptedException {
		String s = "";

		s += "set output '" + printFile + ".ps' \n";
		s += "set terminal postscript \n";

		s += getPlotString();

		s += "set output '|" + printCmd + " " + printFile + "' \n";

		s += ("pause -1 'Press ENTER to continue...' \n");

		System.out.println("Calling GNUPlotRunner...");
		GNUPlotRunner pr = new GNUPlotRunner(s,out);
		new Thread(pr).start();
	}

	public void plotToFile(String psFileName, OutputFileFormat format) throws IOException, InterruptedException {

		String s = "";
		s += "set output '" + psFileName + "' \n";

		if (format == OutputFileFormat.POSTSCRIPT) {
			s += "set terminal " + format;
			s += " enhanced ";
			if (psColor)
				s += " color ";
			s += "solid defaultplex ";
			s += "'" + psFontName + "' ";
			s += psFontSize + " ";
			s += " \n";
		} else if (format == OutputFileFormat.SVG) {
			s += "set terminal " + format;
			s += " \n";
		} else {
			s += "set terminal " + format;
			s += " \n";
		}

		s += getPlotString();
		s += "set terminal X11 \n";

		System.out.println("Calling GNUPlotRunner...");
		GNUPlotRunner pr = new GNUPlotRunner(s,out);
		new Thread(pr).start();
	}

	/**
	 * Generates a filename that can be use for temporary files. The file will
	 * be located in the TEMP_DIR directory.
	 * 
	 * @return
	 */
	public static String getTempFileName() {
		String s = TEMP_DIR + "/jGNUplot";
		String fn = s + tmpFilnameCounter + ".tmp";

		while (new File(fn).exists()) {
			tmpFilnameCounter++;
			fn = s + tmpFilnameCounter + ".tmp";
		}

		return fn;
	}

	public String getPlotFileName() {
		return plotFileName;
	}

	public void setPlotFileName(String plotFileName) {
		this.plotFileName = plotFileName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getXlabel() {
		return xlabel;
	}

	public void setXlabel(String xlabel) {
		this.xlabel = xlabel;
	}

	public Double getXmax() {
		return xmax;
	}

	public void setXmax(double xmax) {
		this.xmax = xmax;
	}

	public Double getXmin() {
		return xmin;
	}

	public void setXmin(double xmin) {
		this.xmin = xmin;
	}

	public String getYlabel() {
		return ylabel;
	}

	public void setYlabel(String ylabel) {
		this.ylabel = ylabel;
	}

	public Double getYmax() {
		return ymax;
	}

	public void setYmax(double ymax) {
		this.ymax = ymax;
	}

	public Double getYmin() {
		return ymin;
	}

	public void setYmin(double ymin) {
		this.ymin = ymin;
	}

	public boolean isLogScaleX() {
		return logScaleX;
	}

	public void setLogScaleX(boolean logScaleX) {
		this.logScaleX = logScaleX;
	}

	public boolean isLogScaleY() {
		return logScaleY;
	}

	public void setLogScaleY(boolean logScaleY) {
		this.logScaleY = logScaleY;
	}

	public JGPPrintWriter getOut() {
		return out;
	}

	public void setOut(JGPPrintWriter out) {
		this.out = out;
	}

	public String getPrePlotString() {
		return prePlotString;
	}

	public void setPrePlotString(String perPlotString) {
		this.prePlotString = perPlotString;
	}

	public PlotType getPlotType() {
		return plotType;
	}

	public void setPlotType(PlotType plotType) {
		this.plotType = plotType;
	}

	public void setLogScaleZ(boolean logScaleZ) {
		this.logScaleZ = logScaleZ;
	}

	public void setZlabel(String zlabel) {
		this.zlabel = zlabel;
	}

	public void setZmax(Double zmax) {
		this.zmax = zmax;
	}

	public void setZmin(Double zmin) {
		this.zmin = zmin;
	}

}
