package org.hopandfork.jgnuplot.gui.presenter.panel;

import org.hopandfork.jgnuplot.control.LabelController;
import org.hopandfork.jgnuplot.control.PlotController;
import org.hopandfork.jgnuplot.control.PlottableDataController;
import org.hopandfork.jgnuplot.gui.bar.Menu;
import org.hopandfork.jgnuplot.gui.panel.BottomPanel;
import org.hopandfork.jgnuplot.gui.panel.OverviewPanel;
import org.hopandfork.jgnuplot.gui.panel.PreviewPanel;
import org.hopandfork.jgnuplot.gui.presenter.bar.MenuInterface;
import org.hopandfork.jgnuplot.gui.presenter.bar.MenuPresenter;
import org.hopandfork.jgnuplot.model.Plot;

public class MainPresenter {
	private MainInterface mainPanel = null;
	private PlotController plotController = null;
	private PlottableDataController plottableDataController = null;
	private LabelController labelController = null;

	private MenuPresenter menuPresenter = null;

	private OverviewPresenter overviewPresenter;

	private BottomPresenter bottomPresenter;

	public MainPresenter(MainInterface mainPanel, PlotController plotController,
			PlottableDataController plottableDataController, LabelController labelController) {
		this.mainPanel = mainPanel;
		this.plotController = plotController;
		this.plottableDataController = plottableDataController;
		this.labelController = labelController;

		init();
	}

	private void init() {
		
		// Create the menu bar and add it to the dialog box.
		MenuInterface menu = new Menu();
		menuPresenter = new MenuPresenter(this, menu, plottableDataController);
		mainPanel.setMenuBar(menu.toJMenuBar());
		
		/* Creates panels. */
		OverviewInterface overviewPanel = new OverviewPanel();
		BottomInterface bottomPanel = new BottomPanel();
		PreviewPanel previewPanel = new PreviewPanel(plotController, plottableDataController, labelController);

		/* Adds Presenter to manage the presentation logic */
		bottomPresenter = new BottomPresenter(bottomPanel, plotController);
		overviewPresenter = new OverviewPresenter(overviewPanel, plottableDataController, labelController,
				plotController);

		/* Inits with default values */
		Plot plot = plotController.getCurrent();
		if (plot != null)
			bottomPresenter.initialize(plot);
		
		mainPanel.setOverviewPanel(overviewPanel);
		mainPanel.setPreviewPanel(previewPanel);
		mainPanel.setBottomPanel(bottomPanel);
		mainPanel.setWindowTitle("JGNUplot");
		
		mainPanel.display();
	}

	public void reset() {
		bottomPresenter.reset();
		labelController.deleteAll();
		plottableDataController.deleteAll();
	}

	public void delete() {
		overviewPresenter.delete();
	}

	public void edit() {
		overviewPresenter.edit();
	}

	public void exit() {
		System.exit(0);
	}
}
