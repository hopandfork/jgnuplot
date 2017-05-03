package org.hopandfork.jgnuplot.model;

public class Project {

	/** Created plot. */
	private Plot plot;

	/** Current project. */
	static private Project currentProject = new Project();

	/**
	 * Returns the current project, if any.
	 * @return Current project.
	 */
	static public Project currentProject()
	{
		return currentProject;
	}

	public Project()
	{
		this.plot = new Plot2D(); // TODO how to switch to Plot3D when needed?
	}

	public Plot getPlot() {
		return plot;
	}

	public void setPlot(Plot plot) {
		this.plot = plot;
	}
}
