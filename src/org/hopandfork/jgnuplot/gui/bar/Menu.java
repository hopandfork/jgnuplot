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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.hopandfork.jgnuplot.gui.presenter.bar.MenuInterface;
import org.hopandfork.jgnuplot.gui.presenter.bar.MenuPresenter;

public class Menu extends JMenuBar implements MenuInterface,ActionListener {

	private JMenu file_menu;
	private MenuPresenter menuPresenter;

	private JMenuItem addDataFileMenuItem, addFunctionMenuItem, deleteMenuItem, clearMenuItem, editMenuItem,
			exitMenuItem, newMenuItem, aboutMenuItem;

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

		newMenuItem = new JMenuItem("New");
		newMenuItem.addActionListener(this);
		newMenuItem.setActionCommand("new");
		file_menu.add(newMenuItem);

		exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(this);
		exitMenuItem.setActionCommand("exit");
		file_menu.add(exitMenuItem);

		// Add the edit menu the menu bar.
		JMenu edit_menu = new JMenu("Edit");
		edit_menu.setBorderPainted(false);
		this.add(edit_menu);

		addDataFileMenuItem = new JMenuItem("Add DataFile");
		addDataFileMenuItem.addActionListener(this);
		addDataFileMenuItem.setActionCommand("add_datafile");
		edit_menu.add(addDataFileMenuItem);

		addFunctionMenuItem = new JMenuItem("Add Function");
		addFunctionMenuItem.addActionListener(this);
		addFunctionMenuItem.setActionCommand("add_function");
		edit_menu.add(addFunctionMenuItem);

		editMenuItem = new JMenuItem("Edit");
		editMenuItem.addActionListener(this);
		editMenuItem.setActionCommand("edit");
		edit_menu.add(editMenuItem);

		deleteMenuItem = new JMenuItem("Delete");
		deleteMenuItem.addActionListener(this);
		deleteMenuItem.setActionCommand("delete");
		edit_menu.add(deleteMenuItem);

		clearMenuItem = new JMenuItem("Clear");
		clearMenuItem.addActionListener(this);
		clearMenuItem.setActionCommand("clear");
		edit_menu.add(clearMenuItem);

		// Add the file menu the menu bar.
		JMenu help_menu = new JMenu("Help");
		help_menu.setBorderPainted(false);
		this.add(help_menu);

		aboutMenuItem = new JMenuItem("About");
		aboutMenuItem.addActionListener(this);
		aboutMenuItem.setActionCommand("about");
		help_menu.add(aboutMenuItem);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("new")) {
			menuPresenter.newPlot();
		} else if (e.getActionCommand().equals("exit")) {
			menuPresenter.exit();
		} else if (e.getActionCommand().equals("add_datafile")) {
			menuPresenter.showDataFileDialog();
		} else if (e.getActionCommand().equals("add_function")) {
			menuPresenter.showFunctionDialog();
		} else if (e.getActionCommand().equals("edit")) {
			menuPresenter.edit();
		} else if (e.getActionCommand().equals("delete")) {
			menuPresenter.delete();
		} else if (e.getActionCommand().equals("clear")) {
			menuPresenter.clear();
		} else if (e.getActionCommand().equals("about")) {
			menuPresenter.showAboutDialog();
		}
	}

	@Override
	public JMenuBar getJMenuBar() {
		return this;
	}

	@Override
	public void setPresenter(MenuPresenter menuPresenter) {
		this.menuPresenter = menuPresenter;
	}
	
}
