package org.hopandfork.jgnuplot.gui.bar;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;
import org.hopandfork.jgnuplot.control.PlotController;
import org.hopandfork.jgnuplot.control.PlottableDataController;
import org.hopandfork.jgnuplot.gui.dialog.AboutDialog;
import org.hopandfork.jgnuplot.gui.dialog.DataFileDialog;
import org.hopandfork.jgnuplot.gui.dialog.FunctionDialog;
import org.hopandfork.jgnuplot.gui.panel.MainInterface;
import org.hopandfork.jgnuplot.gui.panel.MenuInterface;

public class Menu extends JMenuBar implements MenuInterface, ActionListener {

	private static Logger LOG = Logger.getLogger(Menu.class);

	private JMenu file_menu;

	private JMenuItem add_datafile_menu_item, add_function_menu_item, delete_menu_item, clear_menu_item, edit_menu_item;

	private MainInterface main;

	private PlottableDataController plottableDataController;

	private PlotController plotController;

	public Menu(MainInterface main, PlotController plotController, PlottableDataController plottableDataController) {
		this.plotController = plotController;
		this.plottableDataController = plottableDataController;
		this.main = main;
		create();
	}

	private void create() {

		// Set the panel layout.
		this.setLayout(new FlowLayout(FlowLayout.LEFT));

		// Set the menubar's border.
		this.setBorder(new BevelBorder(BevelBorder.RAISED));
		this.setBorderPainted(true);

		// Add the file menu the menu bar.
		file_menu = new JMenu("File");
		file_menu.setBorderPainted(false);
		this.add(file_menu);

		JMenuItem new_menu_item = new JMenuItem("New");
		new_menu_item.addActionListener(this);
		new_menu_item.setActionCommand("new");
		file_menu.add(new_menu_item);

//		JMenuItem save_menu_item = new JMenuItem("Save");
//		save_menu_item.addActionListener(this);
//		save_menu_item.setActionCommand("Save");
//		file_menu.add(save_menu_item);
//
//		JMenuItem save_to_menu_item = new JMenuItem("Save project to...");
//		save_to_menu_item.addActionListener(this);
//		save_to_menu_item.setActionCommand("Save project to...");
//		file_menu.add(save_to_menu_item);
//
//		file_menu.addSeparator();
//
//		JMenuItem load_menu_item = new JMenuItem("Load project...");
//		load_menu_item.addActionListener(this);
//		load_menu_item.setActionCommand("Load project...");
//		file_menu.add(load_menu_item);
//
//		file_menu.addSeparator();

		JMenuItem exit_menu_item = new JMenuItem("Exit");
		exit_menu_item.addActionListener(this);
		exit_menu_item.setActionCommand("Exit");
		file_menu.add(exit_menu_item);

		// Add the edit menu the menu bar.
		JMenu edit_menu = new JMenu("Edit");
		edit_menu.setBorderPainted(false);
		this.add(edit_menu);

		edit_menu.addSeparator();

		add_datafile_menu_item = new JMenuItem("Add DataFile");
		add_datafile_menu_item.addActionListener(this);
		add_datafile_menu_item.setActionCommand("add_datafile");
		edit_menu.add(add_datafile_menu_item);

		add_function_menu_item = new JMenuItem("Add Function");
		add_function_menu_item.addActionListener(this);
		add_function_menu_item.setActionCommand("add_function");
		edit_menu.add(add_function_menu_item);

		edit_menu_item = new JMenuItem("Edit");
		edit_menu_item.addActionListener(this);
		edit_menu_item.setActionCommand("edit");
		edit_menu.add(edit_menu_item);

		delete_menu_item = new JMenuItem("Delete");
		delete_menu_item.addActionListener(this);
		delete_menu_item.setActionCommand("delete");
		edit_menu.add(delete_menu_item);

		clear_menu_item = new JMenuItem("Clear");
		clear_menu_item.addActionListener(this);
		clear_menu_item.setActionCommand("clear");
		edit_menu.add(clear_menu_item);

		// Add the file menu the menu bar.
		JMenu help_menu = new JMenu("Help");
		help_menu.setBorderPainted(false);
		this.add(help_menu);

		JMenuItem about_menu_item = new JMenuItem("About");
		about_menu_item.addActionListener(this);
		about_menu_item.setActionCommand("about");
		help_menu.add(about_menu_item);
	}



	private void acSaveProjectTo() {
		// TODO
	}

	private void acSaveProject() {
		// TODO
	}

	private void acLoadProject() {
		// TODO
	}


	private void acNew() {
		main.reset();
		main.setTitle("<New>");

		// TODO interact with Project
	}


	private void exit() {
		System.exit(0);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("add_datafile")) {
			DataFileDialog dataFileDialog = new DataFileDialog(plottableDataController);
			dataFileDialog.setVisible(true);
		} else if (e.getActionCommand().equals("add_function")) {
			FunctionDialog addFunctionDialog = new FunctionDialog(plottableDataController);
			addFunctionDialog.setVisible(true);
		} else if (e.getActionCommand().equals("Exit")) {
			exit();
		} else if (e.getActionCommand().equals("new")) {
			acNew();
		} else if (e.getActionCommand().equals("about")) {
			AboutDialog.showAboutDialog();
		} else if (e.getActionCommand().equals("Save project to..."))
			acSaveProjectTo();
		else if (e.getActionCommand().equals("Save"))
			acSaveProject();
		else if (e.getActionCommand().equals("Load project..."))
			acLoadProject();

	}

	@Override
	public JMenuItem getEdit_menu_item() {
		return edit_menu_item;
	}

	@Override
	public JMenuItem getDelete_menu_item() {
		return delete_menu_item;
	}

}
