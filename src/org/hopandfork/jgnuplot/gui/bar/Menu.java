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

package org.hopandfork.jgnuplot.gui.bar;

import java.awt.FlowLayout;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;
import org.hopandfork.jgnuplot.control.PlottableDataController;
import org.hopandfork.jgnuplot.gui.presenter.bar.MenuInterface;

public class Menu extends JMenuBar implements MenuInterface {

	private static Logger LOG = Logger.getLogger(Menu.class);

	private JMenu file_menu;

	private JMenuItem add_datafile_menu_item, add_function_menu_item, delete_menu_item, clear_menu_item, edit_menu_item,
			exit_menu_item, new_menu_item, about_menu_item;

	private PlottableDataController plottableDataController;

	public Menu() {
		create();
	}

	private void create() {

		// Set the panel layout.
		this.setLayout(new FlowLayout(FlowLayout.LEFT));

		// Add the file menu the menu bar.
		file_menu = new JMenu("File");
		file_menu.setBorderPainted(false);
		this.add(file_menu);

		new_menu_item = new JMenuItem("New");
		file_menu.add(new_menu_item);

		exit_menu_item = new JMenuItem("Exit");
		file_menu.add(exit_menu_item);

		// Add the edit menu the menu bar.
		JMenu edit_menu = new JMenu("Edit");
		edit_menu.setBorderPainted(false);
		this.add(edit_menu);

		add_datafile_menu_item = new JMenuItem("Add DataFile");
		edit_menu.add(add_datafile_menu_item);

		add_function_menu_item = new JMenuItem("Add Function");
		edit_menu.add(add_function_menu_item);

		edit_menu_item = new JMenuItem("Edit");
		edit_menu.add(edit_menu_item);

		delete_menu_item = new JMenuItem("Delete");
		edit_menu.add(delete_menu_item);

		clear_menu_item = new JMenuItem("Clear");
		edit_menu.add(clear_menu_item);

		// Add the file menu the menu bar.
		JMenu help_menu = new JMenu("Help");
		help_menu.setBorderPainted(false);
		this.add(help_menu);

		about_menu_item = new JMenuItem("About");
		help_menu.add(about_menu_item);
	}

	@Override
	public JMenuItem getEdit_menu_item() {
		return edit_menu_item;
	}

	@Override
	public JMenuItem getDelete_menu_item() {
		return delete_menu_item;
	}

	@Override
	public JMenuItem getNew_menu_item() {
		return new_menu_item;
	}

	@Override
	public JMenuItem getAdd_datafile_menu_item() {
		return add_datafile_menu_item;
	}

	@Override
	public JMenuItem getAdd_function_menu_item() {
		return add_function_menu_item;
	}

	@Override
	public JMenuBar toJMenuBar() {
		return this;
	}

	@Override
	public JMenuItem getExit_menu_item() {
		return exit_menu_item;
	}

	@Override
	public JMenuItem getClear_menu_item() {
		return clear_menu_item;
	}

	@Override
	public JMenuItem getAbout_menu_item() {
		return about_menu_item;
	}

}
