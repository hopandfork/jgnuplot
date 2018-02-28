package org.hopandfork.jgnuplot.gui.presenter.bar;

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
public class MenuPresenter {
	private static final Logger LOG = Logger.getLogger(MenuPresenter.class);

	private PlottableDataController plottableDataController = null;

	private MainPresenter mainPresenter = null;

	private MenuInterface menuBar = null;

	public MenuPresenter(MainPresenter mainPresenter, MenuInterface menuBar,
			PlottableDataController plottableDataController) {

		this.mainPresenter = mainPresenter;

		this.menuBar = menuBar;

		this.plottableDataController = plottableDataController;

		this.menuBar.setPresenter(this);
	}

	public void newPlot() {
		mainPresenter.reset();
	}

	public void exit() {
		System.exit(0);
	}

	public void showDataFileDialog() {
		DataFileDialog dataFileDialog = new DataFileDialog(plottableDataController);
		dataFileDialog.setVisible(true);
	}

	public void showFunctionDialog() {
		FunctionDialog addFunctionDialog = new FunctionDialog(plottableDataController);
		addFunctionDialog.setVisible(true);
	}

	public void edit() {
		mainPresenter.edit();
	}

	public void delete() {
		mainPresenter.delete();
	}

	public void clear(){
		// TODO Implements clear
		LOG.info("Implements clear");
	}
	
	public void showAboutDialog() {
		AboutDialog.showAboutDialog();
	}
}
