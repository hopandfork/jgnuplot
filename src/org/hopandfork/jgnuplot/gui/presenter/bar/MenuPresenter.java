package org.hopandfork.jgnuplot.gui.presenter.bar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.log4j.Logger;
import org.hopandfork.jgnuplot.control.PlottableDataController;
import org.hopandfork.jgnuplot.gui.dialog.AboutDialog;
import org.hopandfork.jgnuplot.gui.dialog.DataFileDialog;
import org.hopandfork.jgnuplot.gui.dialog.FunctionDialog;
import org.hopandfork.jgnuplot.gui.presenter.panel.MainInterface;
import org.hopandfork.jgnuplot.model.Plot;
import org.hopandfork.jgnuplot.model.Project;

/**
 * This class allows to manage the presentation logic behind the menu bar.
 * 
 * @author lucafulgieri
 *
 */
public class MenuPresenter implements ActionListener {
	private static final Logger LOG = Logger.getLogger(MenuPresenter.class);
	
	private PlottableDataController plottableDataController = null;

	private MainInterface mainPanel = null;

	private MenuInterface menuBar = null;

	public MenuPresenter(MainInterface mainPanel, MenuInterface menuBar,
			PlottableDataController plottableDataController) {
		
		this.mainPanel = mainPanel;
		
		this.menuBar = menuBar;
		
		this.plottableDataController = plottableDataController;

		menuBar.getNew_menu_item().addActionListener(this);
		menuBar.getNew_menu_item().setActionCommand("new");

		menuBar.getExit_menu_item().addActionListener(this);
		menuBar.getExit_menu_item().setActionCommand("exit");

		menuBar.getAdd_datafile_menu_item().addActionListener(this);
		menuBar.getAdd_datafile_menu_item().setActionCommand("add_datafile");

		menuBar.getAdd_function_menu_item().addActionListener(this);
		menuBar.getAdd_function_menu_item().setActionCommand("add_function");

		menuBar.getEdit_menu_item().addActionListener(this);
		menuBar.getEdit_menu_item().setActionCommand("edit");

		menuBar.getDelete_menu_item().addActionListener(this);
		menuBar.getDelete_menu_item().setActionCommand("delete");

		menuBar.getClear_menu_item().addActionListener(this);
		menuBar.getClear_menu_item().setActionCommand("clear");

		menuBar.getAbout_menu_item().addActionListener(this);
		menuBar.getAbout_menu_item().setActionCommand("about");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("new")) {
			acNew();
		} else if (e.getActionCommand().equals("exit")) {
			exit();
		} else if (e.getActionCommand().equals("add_datafile")) {
			DataFileDialog dataFileDialog = new DataFileDialog(plottableDataController);
			dataFileDialog.setVisible(true);
		} else if (e.getActionCommand().equals("add_function")) {
			FunctionDialog addFunctionDialog = new FunctionDialog(plottableDataController);
			addFunctionDialog.setVisible(true);
		} else if (e.getActionCommand().equals("edit")) {
			mainPanel.edit();
		} else if (e.getActionCommand().equals("delete")) {
			mainPanel.delete();
		} else if (e.getActionCommand().equals("clear")) {
			//TODO Implements clear
			LOG.info("Implements clear");
		} else if (e.getActionCommand().equals("about")) {
			AboutDialog.showAboutDialog();
		}
	}

	private void acNew() {
		mainPanel.reset();
	}

	private void exit() {
		System.exit(0);
	}

}
