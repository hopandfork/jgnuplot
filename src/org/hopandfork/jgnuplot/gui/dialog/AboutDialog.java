package org.hopandfork.jgnuplot.gui.dialog;

import java.awt.HeadlessException;
import java.awt.event.ActionListener;

import org.hopandfork.jgnuplot.JGP;

public class AboutDialog extends TextDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AboutDialog() throws HeadlessException {
		super();

		setTitle("About JGNUplot");
		textArea.setText(getGnuText());
	}

	public String getGnuText() {
		String s = "";
		s += "";
		s += " JGNUplot Ver." + JGP.getVersion() + "\n";
		s += " https://github.com/hopandfork/jgnuplot\n";
		s += " ----------------------------------------\n";
		s += " JGNUplot is a GUI for gnuplot (http://www.gnuplot.info/)\n";
		s += " The GUI is build on JAVA wrappers for gnuplot alos provided in\n";
		s += " this package.\n";
		s += " \n";
		s += " Copyright (C) 2006, 2017 Maximilian H. Fabricius, Hop and Fork. \n";
		s += " \n";
		s += " This program is free software; you can redistribute it and/or\n";
		s += " modify it under the terms of the GNU General Public License\n";
		s += " as published by the Free Software Foundation; either version 2\n";
		s += " of the License, or (at your option) any later version.\n";
		s += " \n";
		s += " This program is distributed in the hope that it will be useful,\n";
		s += " but WITHOUT ANY WARRANTY; without even the implied warranty of\n";
		s += " MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n";
		s += " GNU General Public License for more details.\n";
		s += " \n";
		s += " You should have received a copy of the GNU General Public License\n";
		s += " along with this program. If not, see <http://www.gnu.org/licenses/>.\n";
		return s;
	}

	public static void showAboutDialog() {
		TextDialog ad = new AboutDialog();
		ad.setModal(true);
		ad.setVisible(true);
		ad.dispose();

	}

}
