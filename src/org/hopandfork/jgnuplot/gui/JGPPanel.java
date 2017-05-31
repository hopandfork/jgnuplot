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

package org.hopandfork.jgnuplot.gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class JGPPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JGPPanel() {
		super();
	}

	public JGPPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public JGPPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	public JGPPanel(LayoutManager layout) {
		super(layout);
	}

	public void add(Component comp, int gridx, int gridy, int gridwidth, int gridheight, int fill) {
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
