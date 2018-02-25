package org.hopandfork.jgnuplot.gui.presenter.panel;

import javax.swing.JMenuBar;

import org.hopandfork.jgnuplot.gui.panel.PreviewPanel;

public interface MainInterface {

	public void setMainPresenter(MainPresenter mainPresenter);

	public void setPreviewPanel(PreviewPanel previewPanel);

	public void setOverviewPanel(OverviewInterface overviewPanel);

	public void setBottomPanel(BottomInterface bottomPanel);

	public void setMenuBar(JMenuBar jMenuBar);

	public void setWindowTitle(String title);

	public void delete();

	public void edit();

	public void reset();

	public void display();
}
