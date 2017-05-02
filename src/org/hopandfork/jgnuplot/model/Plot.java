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

package org.hopandfork.jgnuplot.model;

import java.io.IOException;
import java.util.ArrayList;

import org.hopandfork.jgnuplot.runtime.GnuplotRunner;
import org.hopandfork.jgnuplot.utility.JGPPrintWriter;

public abstract class Plot implements Plottable {

	private JGPPrintWriter out = null;

	private ArrayList<PlottableData> plottableData;
	private ArrayList<Label> labels;
	private ArrayList<Variable> variables;

	private String prePlotString;

	private String title;

	private String xlabel;
	private String ylabel;
	private String zlabel;

	private Double xmin;
	private Double xmax;
	private Double ymin;
	private Double ymax;
	protected Double zmin;
	protected Double zmax;

	private boolean logScaleX = false;
	private boolean logScaleY = false;
	private boolean logScaleZ = false;

	private boolean psColor = false;
	private int psFontSize = 18;

	private String psFontName = "";
	protected String plotCommand = "plot";

	public Plot() {
		super();
		plottableData = new ArrayList<PlottableData>();
		labels = new ArrayList<Label>();
		variables = new ArrayList<Variable>();
	}

	protected String getVariablesPlotString() {
		String s = "";
		for (Variable var : variables) {
			if (var.getType().equals(Variable.Type.GNUPLOT)) {
				if (var.isActive())
					s += ((GnuplotVariable) var).getPlotString() + "\n";
			}
		}
		return s;
	}

	public String toPlotString() {
		StringBuilder sb = new StringBuilder();

		/* Adds pre-plot commands. */
		sb.append(prePlotString);

		/* Adds variables. */
		sb.append(getVariablesPlotString());

		/* Adds various settings. */
		if (title == null)
			sb.append("unset title \n");
		else
			sb.append("set title \"" + title + "\" \n");

		if (xlabel == null)
			sb.append("unset xlabel \n");
		else
			sb.append("set xlabel \"" + xlabel + "\" \n");

		if (ylabel == null)
			sb.append("unset ylabel \n");
		else
			sb.append("set ylabel \"" + ylabel + "\" \n");

		if (zlabel == null)
			sb.append("unset zlabel \n");
		else
			sb.append("set zlabel \"" + zlabel + "\" \n");

		if (logScaleX)
			sb.append("set logscale x \n");
		else
			sb.append("unset logscale x \n");

		if (logScaleY)
			sb.append("set logscale y \n");
		else
			sb.append("unset logscale y \n");

		if (logScaleZ)
			sb.append("set logscale z \n");
		else
			sb.append("unset logscale z \n");

		/* Adds labels */
		for (Label l : labels) {
			if (l.isEnabled()) {
				sb.append(l.toPlotString());
			}
		}

		/* Adds the plot command. */
		if (plottableData.size() > 0) {
			sb.append(plotCommand + " ");
			sb.append(getRangePlotString());

			for (PlottableData pd : plottableData) {
				if (pd.isEnabled()) {
					sb.append(pd.toPlotString() + ", ");
				}
			}
			/* Strips last comma */
			sb.deleteCharAt(sb.length() - 2);
			sb.append(" \n");
		}

		/* Now replaces all string variables with their value. */
		String s = sb.toString();
		for (Variable var : variables) {
			if (var.getType() == Variable.Type.STRING) {
				if (var.isActive())
					s = ((StringVariable) var).apply(s);
			}
		}

		return s;
	}

	protected String getRangePlotString() {
		String s = "";

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

		return s;
	}

	public void plotAndPreview() throws IOException, InterruptedException {
		String s = "";
		s += "set terminal X11 \n";
		s += toPlotString();
		// we have to add this to keep the plot window open
		s += ("pause -1\n");

		System.out.println("Calling GnuplotRunner...");
		GnuplotRunner pr = new GnuplotRunner(s);
		new Thread(pr).start();

	}

	public void generatePostscriptFile(String printCmd, String printFile) throws IOException, InterruptedException {
		String s = "";

		s += "set output '" + printFile + ".ps' \n";
		s += "set terminal postscript \n";

		s += toPlotString();

		s += "set output '|" + printCmd + " " + printFile + "' \n";

		s += ("pause -1 'Press ENTER to continue...' \n");

		System.out.println("Calling GnuplotRunner...");
		GnuplotRunner pr = new GnuplotRunner(s);
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

		s += toPlotString();
		s += "set terminal X11 \n";

		System.out.println("Calling GnuplotRunner...");
		GnuplotRunner pr = new GnuplotRunner(s);
		new Thread(pr).start();
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

	public boolean isPsColor() {
		return psColor;
	}

	public void setPsColor(boolean psColor) {
		this.psColor = psColor;
	}

	public String getPsFontName() {
		return psFontName;
	}

	public void setPsFontName(String psFontName) {
		this.psFontName = psFontName;
	}

	public void setPsFontSize(int psFontSize) {
		this.psFontSize = psFontSize;
	}

	public int getPsFontSize() {
		return psFontSize;
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

	public void addPlottableData(PlottableData data) {
		plottableData.add(data);
	}

	public void addVariable(Variable variable) {
		variables.add(variable);
	}

	public void addLabel(Label label) {
		labels.add(label);
	}

}
