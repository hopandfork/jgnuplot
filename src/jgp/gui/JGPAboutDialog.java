package jgp.gui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;


public class JGPAboutDialog extends JGPTextDialog implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JGPAboutDialog() throws HeadlessException {
		super();

		setTitle("About JGNUplot");
		textArea.setText(getGnuText());
	}
	
	public String getGnuText() {
		String s = "";
		s += "";
		 s += " JGNUplot Ver." + JGP.getVersion() + "\n";
		 s += " http://sourceforge.net/projects/jgp/\n";
		 s += " ----------------------------------------\n";
		 s += " JGNUplot is a GUI for gnuplot (http://www.gnuplot.info/)\n";
		 s += " The GUI is build on JAVA wrappers for gnuplot alos provided in this package.\n";
		 s += " \n";
		 s += " Copyright (C) 2006  Maximilian H. Fabricius \n";
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
		 s += " along with this program; if not, write to the Free Software\n";
		 s += " Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.\n";
		return s;
	}

	public static void showAboutDialog(){
		JGPTextDialog ad = new JGPAboutDialog();
		ad.setModal(true);
		ad.setVisible(true);
		ad.dispose();
		
	}

}
