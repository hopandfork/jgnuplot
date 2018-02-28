package org.hopandfork.jgnuplot.gui.panel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import org.hopandfork.jgnuplot.gui.presenter.panel.BottomInterface;
import org.hopandfork.jgnuplot.gui.presenter.panel.MainInterface;
import org.hopandfork.jgnuplot.gui.presenter.panel.MainPresenter;
import org.hopandfork.jgnuplot.gui.presenter.panel.OverviewInterface;

public class MainPanel extends JFrame implements MainInterface {

	private static final long serialVersionUID = -6741125764568646574L;

	private JTextArea taShell;

	private JTextArea prePlotString;

	private JPanel previewPanel;

	private MainPresenter mainPresenter = null;
	
	private OverviewInterface overviewPanel;

	private BottomInterface bottomPanel;

	public MainPanel() {

	}

	@Override
	public void delete() {
		this.mainPresenter.delete();
	}

	@Override
	public void edit() {
		this.mainPresenter.edit();
	}

	@Override
	public void reset() {
		this.mainPresenter.reset();
	}

	@Override
	public void setMenuBar(JMenuBar jMenuBar) {
		this.setJMenuBar(jMenuBar);
	}

	@Override
	public void display() {
		this.setLocationByPlatform(true);

		JGPPanel content_pane = new JGPPanel();
		this.add(content_pane);

		// Set the default panel layout.
		GridBagLayout gbl = new GridBagLayout();
		content_pane.setLayout(gbl);


		/* Creates split pane. */
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, overviewPanel.toJPanel(), previewPanel);
		splitPane.setOneTouchExpandable(true);
		splitPane.setResizeWeight(0.0);

		content_pane.add(splitPane, 0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH);
		content_pane.add(bottomPanel.toJPanel(), 0, 1, 1, 1, 1, 0, GridBagConstraints.NONE,
				GridBagConstraints.SOUTHWEST);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				mainPresenter.exit();
			}
		}); // ignore event itself

		int width = Math.max(overviewPanel.toJPanel().getMinimumSize().width + previewPanel.getMinimumSize().width,
				bottomPanel.toJPanel().getMinimumSize().width) + 10;
		int height = overviewPanel.toJPanel().getMinimumSize().height + bottomPanel.toJPanel().getMinimumSize().height;
		setMinimumSize(new Dimension(width, height));
		
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void setMainPresenter(MainPresenter mainPresenter) {
		this.mainPresenter = mainPresenter;
	}

	@Override
	public void setPreviewPanel(PreviewPanel previewPanel) {
		this.previewPanel = previewPanel;
	}

	@Override
	public void setOverviewPanel(OverviewInterface overviewPanel) {
		this.overviewPanel = overviewPanel;
	}

	@Override
	public void setBottomPanel(BottomInterface bottomPanel) {
		this.bottomPanel = bottomPanel;
	}

	@Override
	public void setWindowTitle(String title) {
		this.setTitle("JGNUplot");
	}

}
