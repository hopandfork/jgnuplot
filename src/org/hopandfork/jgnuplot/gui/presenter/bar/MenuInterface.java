package org.hopandfork.jgnuplot.gui.presenter.bar;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public interface MenuInterface {
	
	public JMenuItem getNew_menu_item();
	
	public JMenuItem getExit_menu_item();
	
	public JMenuItem getAdd_datafile_menu_item();
	
	public JMenuItem getAdd_function_menu_item();
	
	public JMenuItem getEdit_menu_item();

	public JMenuItem getDelete_menu_item();

	public JMenuItem getClear_menu_item();
	
	public JMenuItem getAbout_menu_item();
	
	public JMenuBar toJMenuBar();
}
