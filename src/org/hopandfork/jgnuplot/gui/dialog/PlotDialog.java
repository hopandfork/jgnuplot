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

package org.hopandfork.jgnuplot.gui.dialog;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;
import org.hopandfork.jgnuplot.control.PlotController;
import org.hopandfork.jgnuplot.gui.combobox.FontComboBox;
import org.hopandfork.jgnuplot.gui.panel.JGPPanel;
import org.hopandfork.jgnuplot.model.OutputFileFormat;
import org.hopandfork.jgnuplot.model.Plot;
import org.hopandfork.jgnuplot.runtime.GnuplotRunner;
import org.hopandfork.jgnuplot.runtime.terminal.PngcairoTerminal;
import org.hopandfork.jgnuplot.runtime.terminal.PostscriptTerminal;
import org.hopandfork.jgnuplot.runtime.terminal.SvgTerminal;
import org.hopandfork.jgnuplot.runtime.terminal.Terminal;


public class PlotDialog extends JGPDialog implements ActionListener {
	
	private static Logger LOG = Logger.getLogger(PlotDialog.class);

	private static final long serialVersionUID = 1L;

	private JTextField tfFileName;
	
	private FontComboBox cbFontName;
	
	private JTextField tfFontSize;
	
	private JComboBox<OutputFileFormat> cbFileFormat;
	
	private PlotController plotController;
	
	public PlotDialog(PlotController plotController) {
		super();
		this.plotController = plotController;
		add(createMainPanel());
		pack();
	}

	private JPanel createMainPanel(){
		// Create the panel.
		JGPPanel jp = new JGPPanel();
		// jp.setPreferredSize(new Dimension(400, 400));

		// Set the default panel layout.
		GridBagLayout gbl = new GridBagLayout();
		jp.setLayout(gbl);

		// Create the panel border.
		TitledBorder border = new TitledBorder(new EtchedBorder(), "Datasource");
		border.setTitleColor(Color.blue);
		jp.setBorder(border);

		
		// Create the buttons.
		JButton bFileChose = new JButton("...");
		bFileChose.setPreferredSize(new Dimension(20, 20));
		bFileChose.setActionCommand("...");
		bFileChose.addActionListener(this);

		JButton bOk = new JButton("ok");
		bOk.setPreferredSize(new Dimension(60, 20));
		bOk.setActionCommand("ok");
		bOk.addActionListener(this);

		JButton bCancel = new JButton("cancel");
		bCancel.setPreferredSize(new Dimension(100, 20));
		bCancel.setActionCommand("cancel");
		bCancel.addActionListener(this);
		
		
		tfFileName= new JTextField("",20);
		bFileChose.setPreferredSize(new Dimension(100, 20));
		
		cbFontName = new FontComboBox();
		
		tfFontSize = new JTextField("18",2);
		bFileChose.setPreferredSize(new Dimension(20, 20));
		
		cbFileFormat = new JComboBox<>();
		for (OutputFileFormat format : OutputFileFormat.values())
			cbFileFormat.addItem(format);

		int row = 0;
		jp.add(new JLabel("Filename"), 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
		
		jp.add(tfFileName, 1, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(bFileChose, 2, row, 1, 1, GridBagConstraints.HORIZONTAL);
		row += 1;
		jp.add(new JLabel("Font"), 0, row, 3, 1, GridBagConstraints.HORIZONTAL);
		jp.add(cbFontName, 1, row, 1, 1, GridBagConstraints.HORIZONTAL);
		row += 1;
		jp.add(new JLabel("Font size"), 0, row, 3, 1, GridBagConstraints.HORIZONTAL);
		jp.add(tfFontSize, 1, row, 1, 1, GridBagConstraints.HORIZONTAL);
		row += 1;
		jp.add(new JLabel("File format"), 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(cbFileFormat, 1, row, 3, 1, GridBagConstraints.HORIZONTAL);
		row += 1;
		jp.add(bOk, 1, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(bCancel, 3, row, 1, 1, GridBagConstraints.HORIZONTAL);

		return jp;		
	}
	



	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("..."))
			acFileBrowse();
		else if (e.getActionCommand().equals("ok")){
			acOk();
			this.setVisible(false);
		}
		else if (e.getActionCommand().equals("cancel")){
			this.setVisible(false);
		}
	}
	
	public void acOk() {
		String outputFileName = this.tfFileName.getText();
		File outputFile = new File(outputFileName);
		Plot plot = plotController.getCurrent();

		String fontName = null;
		if (cbFontName.getSelectedItem() != null)
			fontName = cbFontName.getSelectedItem().toString();
		int fontSize = Integer.parseInt(tfFontSize.getText()); // TODO check errors

		int widthPixels = 1200; // TODO
		int heightPixels = 800; // TODO

		Terminal terminal = null;
		OutputFileFormat outputFileFormat = (OutputFileFormat)cbFileFormat.getSelectedItem();

		switch (outputFileFormat) {
			case EPS:
				PostscriptTerminal postscriptTerminal = new PostscriptTerminal(widthPixels, heightPixels, outputFile);
				if (fontName != null)
					postscriptTerminal.setFont(fontName, fontSize);
				terminal = postscriptTerminal;
				break;
			case PNG:
				PngcairoTerminal pngTerminal = new PngcairoTerminal(widthPixels, heightPixels, outputFile);
				if (fontName != null)
					pngTerminal.setFont(fontName, fontSize);
				terminal = pngTerminal;
				break;
			case SVG:
				SvgTerminal svgTerminal = new SvgTerminal(widthPixels, heightPixels, outputFile);
				if (fontName != null)
					svgTerminal.setFont(fontName, fontSize);
				terminal = svgTerminal;
				break;
			default:
				// TODO error
		}

		GnuplotRunner.runGnuplot(terminal, plot, null); // TODO provide a ImageConsumer for checking return value

		this.setVisible(false);
		
	}
	
	public void acFileBrowse() {
		
		File f;
		
		if (!this.tfFileName.getText().trim().equals("")){
			f = new File(tfFileName.getText().trim());
			if (!f.exists()) f = new File(f.getPath());
		}
		else
			f = new File(".");
		
		// Open a file chooser that points to the current dir.
		JFileChooser file_chooser = new JFileChooser(f.getPath());

		// Set the Open dialog box size.
		file_chooser.setPreferredSize(new Dimension(500, 250));

		// Set to select directory
		file_chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		// Show the Open dialog box (returns the option selected)
		int selected = file_chooser.showOpenDialog(this);

		// If the Open button is pressed.
		if (selected == JFileChooser.APPROVE_OPTION) {
			// Get the selected file.
			tfFileName.setText(file_chooser.getSelectedFile().toString());
			tfFileName.setToolTipText(file_chooser.getSelectedFile().toString());
			return;
		}
		// If the Cancel button is pressed.
		else if (selected == JFileChooser.CANCEL_OPTION) {
			return;
		}

	}

	@Override
	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		super.setVisible(b);
	
	}
}
