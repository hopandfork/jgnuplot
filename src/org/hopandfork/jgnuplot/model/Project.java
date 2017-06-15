package org.hopandfork.jgnuplot.model;

public class Project {

	/** Created plot. */
	private Plot plot;

	/** File name for this project on disk. */
	private String filename;

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
		this.plot = new Plot();
	}

	public Plot getPlot() {
		return plot;
	}

	public void setPlot(Plot plot) {
		this.plot = plot;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
