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

package jgp.gui;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridBagConstraints;
import java.awt.HeadlessException;
import java.awt.Insets;

import javax.swing.JDialog;

public class JGPDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JGPDialog() throws HeadlessException {
		super();
		this.setLocationByPlatform(true);

		// TODO Auto-generated constructor stub
	}

	public JGPDialog(Dialog owner, boolean modal) throws HeadlessException {
		super(owner, modal);
		this.setLocationByPlatform(true);
		// TODO Auto-generated constructor stub
	}

	public JGPDialog(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) throws HeadlessException {
		super(owner, title, modal, gc);
		this.setLocationByPlatform(true);
		// TODO Auto-generated constructor stub
	}

	public JGPDialog(Dialog owner, String title, boolean modal) throws HeadlessException {
		super(owner, title, modal);
		this.setLocationByPlatform(true);
		// TODO Auto-generated constructor stub
	}

	public JGPDialog(Dialog owner, String title) throws HeadlessException {
		super(owner, title);
		this.setLocationByPlatform(true);
		// TODO Auto-generated constructor stub
	}

	public JGPDialog(Dialog owner) throws HeadlessException {
		super(owner);
		this.setLocationByPlatform(true);
		// TODO Auto-generated constructor stub
	}

	public JGPDialog(Frame owner, boolean modal) throws HeadlessException {
		super(owner, modal);
		this.setLocationByPlatform(true);
		// TODO Auto-generated constructor stub
	}

	public JGPDialog(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		this.setLocationByPlatform(true);
		// TODO Auto-generated constructor stub
	}

	public JGPDialog(Frame owner, String title, boolean modal) throws HeadlessException {
		super(owner, title, modal);
		this.setLocationByPlatform(true);
		// TODO Auto-generated constructor stub
	}

	public JGPDialog(Frame owner, String title) throws HeadlessException {
		super(owner, title);
		this.setLocationByPlatform(true);
		// TODO Auto-generated constructor stub
	}

	public JGPDialog(Frame owner) throws HeadlessException {
		super(owner);
		this.setLocationByPlatform(true);
		// TODO Auto-generated constructor stub
	}

	public void add(Component comp, int gridx, int gridy, int gridwidth, int gridheight, int fill) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = gridx;		
		gbc.gridy = gridy;		
		gbc.gridwidth = gridwidth;		
		gbc.gridheight = gridheight;		
		gbc.fill = fill;
		gbc.insets = new Insets(2,2,2,2);
		super.add(comp, gbc);
	}

	
	
}
