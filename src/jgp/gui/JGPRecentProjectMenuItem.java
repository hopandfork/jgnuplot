package jgp.gui;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;

public class JGPRecentProjectMenuItem extends JMenuItem {
	
	private String recentProject;

	public JGPRecentProjectMenuItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JGPRecentProjectMenuItem(Action a) {
		super(a);
		// TODO Auto-generated constructor stub
	}

	public JGPRecentProjectMenuItem(Icon icon) {
		super(icon);
		// TODO Auto-generated constructor stub
	}

	public JGPRecentProjectMenuItem(String text, Icon icon) {
		super(text, icon);
		recentProject = text;
		// TODO Auto-generated constructor stub
	}

	public JGPRecentProjectMenuItem(String text, int mnemonic) {
		super(text, mnemonic);
		recentProject = text;
		// TODO Auto-generated constructor stub
	}

	public JGPRecentProjectMenuItem(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	public String getRecentProject() {
		// TODO Auto-generated method stub
		return this.recentProject;
	}

}
