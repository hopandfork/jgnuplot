package org.hopandfork.jgnuplot.gui.presenter.bar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.log4j.Logger;
import org.hopandfork.jgnuplot.control.PlottableDataController;
import org.hopandfork.jgnuplot.gui.dialog.AboutDialog;
import org.hopandfork.jgnuplot.gui.dialog.DataFileDialog;
import org.hopandfork.jgnuplot.gui.dialog.FunctionDialog;
import org.hopandfork.jgnuplot.gui.presenter.panel.MainPresenter;

/**
 * This class allows to manage the presentation logic behind the menu bar.
 * 
 * @author lucafulgieri
 *
 */
public class MenuPresenter implements ActionListener {
	private static final Logger LOG = Logger.getLogger(MenuPresenter.class);
	
	private PlottableDataController plottableDataController = null;

	private MainPresenter mainPresenter = null;

	private MenuInterface menuBar = null;

	public MenuPresenter(MainPresenter mainPresenter, MenuInterface menuBar,
			PlottableDataController plottableDataController) {
		
		this.mainPresenter = mainPresenter;
		
		this.menuBar = menuBar;
		
		this.plottableDataController = plottableDataController;
		
		init();
	}

	private void init(){
		this.menuBar.getNew_menu_item().addActionListener(this);
		this.menuBar.getNew_menu_item().setActionCommand("new");

		this.menuBar.getExit_menu_item().addActionListener(this);
		this.menuBar.getExit_menu_item().setActionCommand("exit");

		this.menuBar.getAdd_datafile_menu_item().addActionListener(this);
		this.menuBar.getAdd_datafile_menu_item().setActionCommand("add_datafile");

		this.menuBar.getAdd_function_menu_item().addActionListener(this);
		this.menuBar.getAdd_function_menu_item().setActionCommand("add_function");

		this.menuBar.getEdit_menu_item().addActionListener(this);
		this.menuBar.getEdit_menu_item().setActionCommand("edit");

		this.menuBar.getDelete_menu_item().addActionListener(this);
		this.menuBar.getDelete_menu_item().setActionCommand("delete");

		this.menuBar.getClear_menu_item().addActionListener(this);
		this.menuBar.getClear_menu_item().setActionCommand("clear");

		this.menuBar.getAbout_menu_item().addActionListener(this);
		this.menuBar.getAbout_menu_item().setActionCommand("about");
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
			mainPresenter.edit();
		} else if (e.getActionCommand().equals("delete")) {
			mainPresenter.delete();
		} else if (e.getActionCommand().equals("clear")) {
			//TODO Implements clear
			LOG.info("Implements clear");
		} else if (e.getActionCommand().equals("about")) {
			AboutDialog.showAboutDialog();
		}
	}

	private void acNew() {
		mainPresenter.reset();
	}

	private void exit() {
		System.exit(0);
	}

}
