package org.hopandfork.jgnuplot.model.gnuplot;

public class Plot3D extends Plot {

	public Plot3D() {
		this.plotCommand = "splot";
	}

	@Override
	protected String getRangePlotString() {
		String s = "";
		s += "[";
		if (zmin != null)
			s += zmin;
		s += ":";
		if (zmax != null)
			s += zmax;
		s += "] ";
		return s;
	}
}
