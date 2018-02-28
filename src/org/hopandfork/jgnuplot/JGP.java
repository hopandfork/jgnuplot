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

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hopandfork.jgnuplot.control.LabelController;
import org.hopandfork.jgnuplot.control.PlotController;
import org.hopandfork.jgnuplot.control.PlottableDataController;
import org.hopandfork.jgnuplot.gui.panel.MainPanel;
import org.hopandfork.jgnuplot.gui.presenter.panel.MainInterface;
import org.hopandfork.jgnuplot.gui.presenter.panel.MainPresenter;

public class JGP {

	private static Logger LOG = Logger.getLogger(JGP.class);

	@Deprecated
	public static String getVersion() {
		return "0.1.2"; // TODO
	}

	private static void initializeLookAndFeel() {
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
					MainInterface mainPanel = new MainPanel();
					MainPresenter mainPresenter = new MainPresenter(mainPanel, new PlotController(),
							new PlottableDataController(), new LabelController());
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
}
