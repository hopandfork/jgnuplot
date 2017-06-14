package org.hopandfork.jgnuplot.gui;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;

@Deprecated
public class RecentProjectMenuItem extends JMenuItem {
	
	private String recentProject;

	public RecentProjectMenuItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RecentProjectMenuItem(Action a) {
		super(a);
		// TODO Auto-generated constructor stub
	}

	public RecentProjectMenuItem(Icon icon) {
		super(icon);
		// TODO Auto-generated constructor stub
	}

	public RecentProjectMenuItem(String text, Icon icon) {
		super(text, icon);
		recentProject = text;
		// TODO Auto-generated constructor stub
	}

	public RecentProjectMenuItem(String text, int mnemonic) {
		super(text, mnemonic);
		recentProject = text;
		// TODO Auto-generated constructor stub
	}

	public RecentProjectMenuItem(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	public String getRecentProject() {
		// TODO Auto-generated method stub
		return this.recentProject;
	}

}
