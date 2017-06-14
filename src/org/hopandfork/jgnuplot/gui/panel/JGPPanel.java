/*
 * JGNUplot is a GUI for gnuplot (http://www.gnuplot.info/)
 * The GUI is build on JAVA wrappers for gnuplot alos provided in this package.
 * 
 * Copyright (C) 2006  Maximilian H. Fabricius 
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.hopandfork.jgnuplot.gui.panel;

import org.apache.log4j.Logger;

import java.awt.*;

import javax.swing.JPanel;

/**
 * A JPanel with a default GridBagLayout.
 *
 * This class provides helper methods for using GBL, but
 * it is possible to use a different layout anyway.
 */
public class JGPPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;

	private static Logger LOG = Logger.getLogger(JGPPanel.class);

	public JGPPanel() {
	    this.setLayout(new GridBagLayout());
	}

	private boolean hasGridBagLayout()
	{
		if (this.getLayout() instanceof GridBagLayout)
			return true;

		LOG.error("Trying to use GridBagConstraints on a layout which is not GridBaglayout!");
		return false;
	}

	public void add(Component comp, int gridx, int gridy, int gridwidth, int gridheight, int fill) {
		if (!hasGridBagLayout())
			return;

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = gridx;		
		gbc.gridy = gridy;		
		gbc.gridwidth = gridwidth;		
		gbc.gridheight = gridheight;		
		gbc.fill = fill;
		gbc.insets = new Insets(2,2,2,2);

		add(comp, gbc);
	}
	
	public void add(Component comp, int gridx, int gridy, int gridwidth, int gridheight, int fill, int anchor) {
		if (!hasGridBagLayout())
			return;

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = gridx;		
		gbc.gridy = gridy;		
		gbc.gridwidth = gridwidth;		
		gbc.gridheight = gridheight;
		gbc.fill = fill;
		gbc.anchor = anchor;
		gbc.insets = new Insets(2,2,2,2);

		add(comp, gbc);
	}

	public void add(Component comp, int gridx, int gridy, int gridwidth, int gridheight, int weightx, int weighty, int fill) {
		if (!hasGridBagLayout())
			return;

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.gridwidth = gridwidth;
		gbc.gridheight = gridheight;
		gbc.fill = fill;
		gbc.weighty = weighty;
		gbc.weightx = weightx;
		gbc.insets = new Insets(2,2,2,2);

		add(comp, gbc);
	}

	public void add(Component comp, int gridx, int gridy, int gridwidth, int gridheight, int weightx, int weighty, int fill, int anchor) {
		if (!hasGridBagLayout())
			return;

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.gridwidth = gridwidth;
		gbc.gridheight = gridheight;
		gbc.fill = fill;
		gbc.weighty = weighty;
		gbc.weightx = weightx;
		gbc.anchor = anchor;
		gbc.insets = new Insets(2,2,2,2);

		add(comp, gbc);
	}
}
