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

package org.hopandfork.jgnuplot;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

import javax.swing.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.spi.LoggerFactory;
import org.hopandfork.jgnuplot.control.LabelController;
import org.hopandfork.jgnuplot.control.PlotController;
import org.hopandfork.jgnuplot.control.PlottableDataController;
import org.hopandfork.jgnuplot.gui.bar.Menu;
import org.hopandfork.jgnuplot.gui.panel.BottomPanel;
import org.hopandfork.jgnuplot.gui.panel.JGPPanel;
import org.hopandfork.jgnuplot.gui.panel.MainInterface;
import org.hopandfork.jgnuplot.gui.panel.OverviewPanel;
import org.hopandfork.jgnuplot.gui.panel.PreviewPanel;

public class JGP extends JFrame implements MainInterface {

	private static Logger LOG = Logger.getLogger(JGP.class);

	private Menu menu;

	private OverviewPanel overviewPanel;

	private JPanel previewPanel;

	private BottomPanel bottomPanel;

	private static final long serialVersionUID = 1L;

	/**
	 * Controller for PlottableData management.
	 */
	private PlottableDataController plottableDataController = new PlottableDataController();

	/**
	 * Controller for Label management.
	 */
	private LabelController labelController = new LabelController();

	private PlotController plotController = new PlotController();

	public JGP() {
		this.setTitle("JGNUplot");
		this.setLocationByPlatform(true);

		// Create the menu bar and add it to the dialog box.
		menu = new Menu(this, plotController, plottableDataController);
		this.setJMenuBar(menu);

		JGPPanel content_pane = new JGPPanel();
		this.add(content_pane);

		// Set the default panel layout.
		GridBagLayout gbl = new GridBagLayout();
		content_pane.setLayout(gbl);

		/* Creates panels. */
		overviewPanel = new OverviewPanel(menu, plotController, plottableDataController, labelController);
		bottomPanel = new BottomPanel(overviewPanel, plotController);
		previewPanel = new PreviewPanel(plotController, plottableDataController, labelController);

		/* Creates split pane. */
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, overviewPanel, previewPanel);
		splitPane.setOneTouchExpandable(true);
		splitPane.setResizeWeight(0.0);

		content_pane.add(splitPane, 0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH);
		content_pane.add(bottomPanel, 0, 1, 1, 1, 1, 0, GridBagConstraints.NONE, GridBagConstraints.SOUTHWEST);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exit();
			}
		}); // ignore event itself

		int width = Math.max(overviewPanel.getMinimumSize().width + previewPanel.getMinimumSize().width, bottomPanel.getMinimumSize().width)+10;
		int height = overviewPanel.getMinimumSize().height + bottomPanel.getMinimumSize().height;
		setMinimumSize(new Dimension(width, height));
	}


	private void exit() {
		System.exit(0);
	}

	@Deprecated
	public static String getVersion() {
		return "0.1.2"; // TODO
	}

	private static void initializeLookAndFeel()
	{
		try {
			UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceBusinessLookAndFeel");
		} catch (Exception e) {
		    LOG.error("Failed to load Substance look and feel!");
		}
	}

	public static void main(String[] args) throws MalformedURLException {
		/* Log4j initialization */
		PropertyConfigurator.configure(
				System.getProperty("user.dir") + System.getProperty("file.separator") + "config/log4j2.properties");

		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					initializeLookAndFeel();
					JGP m = new JGP();
					m.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
					m.pack();
					m.setVisible(true);
				}
			});
		} catch (InterruptedException e) {
			// Ignore: If this exception occurs, we return too early, which
			// makes the splash window go away too early.
			// Nothing to worry about. Maybe we should write a log message.
		} catch (InvocationTargetException e) {
			// Error: Startup has failed badly.
			// We can not continue running our application.
			InternalError error = new InternalError();
			error.initCause(e);
			throw error;
		}
	}

	@Override
	public void reset() {
		bottomPanel.reset();
	}
}