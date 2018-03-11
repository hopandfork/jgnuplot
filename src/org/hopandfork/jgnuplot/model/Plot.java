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

package org.hopandfork.jgnuplot.model;

import java.util.ArrayList;
import java.util.List;

public class Plot implements Plottable {

    public List<PlottableData> getPlottableData() {
        return plottableData;
    }

    private List<PlottableData> plottableData;
    private List<Label> labels;

    private String prePlotString;

    private String title;

    private String xlabel;
    private String ylabel;
    private String zlabel;

    private Double xmin;
    private Double xmax;
    private Double ymin;
    private Double ymax;
    private Double zmin;
    private Double zmax;

    private boolean logScaleX = false;
    private boolean logScaleY = false;
    private boolean logScaleZ = false;

    public enum Mode {
        PLOT_2D, PLOT_3D;
    }

    /**
     * Plot mode (2D/3D).
     */
    private Mode mode = Mode.PLOT_2D;

    public Plot() {
        super();
        plottableData = new ArrayList<PlottableData>();
        labels = new ArrayList<Label>();
    }

    public String toPlotString() {
        StringBuilder sb = new StringBuilder();

		/* Adds pre-plot commands. */
		if (prePlotString != null && prePlotString.length() > 0)
            sb.append(prePlotString + "\n");

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

		/* Adds the constants for the function */
        for (PlottableData pd : plottableData) {
            if (pd instanceof Function) {
                for (Constant constant : ((Function) pd).getConstants()) {
                    sb.append(constant.toPlotString() + "\n");
                }
            }
        }

		/* Adds the plot command. */
        String plotCommand;
        if (mode.equals(Mode.PLOT_3D)) {
            plotCommand = "splot";
        } else {
            plotCommand = "plot";
        }
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

        String s = sb.toString();

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

        s += "[";
        if (zmin != null)
            s += zmin;
        s += ":";
        if (zmax != null)
            s += zmax;
        s += "] ";

        return s;
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

    public void setXmax(Double xmax) {
        this.xmax = xmax;
    }

    public Double getXmin() {
        return xmin;
    }

    public void setXmin(Double xmin) {
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

    public void setYmax(Double ymax) {
        this.ymax = ymax;
    }

    public Double getYmin() {
        return ymin;
    }

    public void setYmin(Double ymin) {
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

    public void deletePlottableData(PlottableData data) {
        if (plottableData.contains(data))
            plottableData.remove(data);
    }

    public void deleteAllPlottableData() {
        plottableData.clear();
    }

    public void addLabel(Label label) {
        labels.add(label);
    }

    public void deleteLabel(Label label) {
        if (labels.contains(label))
            labels.remove(label);
    }

    public void deleteAllLabels() {
        labels.clear();
    }


    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public List<Label> getLabels() {
        return labels;
    }

	public Double getZmin() {
		return zmin;
	}

	public Double getZmax() {
		return zmax;
	}

	public String getZlabel() {
		return zlabel;
	}

	public boolean isLogScaleZ() {
		return logScaleZ;
	}
    
}
