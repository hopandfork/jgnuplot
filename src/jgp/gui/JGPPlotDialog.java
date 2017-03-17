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


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import jgp.JGPFileFormat;
import jgp.JGPgnuplot;

public class JGPPlotDialog extends JGPDialog implements ActionListener {
	
	JGP owner;
	
	JTextField tfFileName;
	
	JGPFontComboBox cbFontName;
	
	JTextField tfFontSize;
	
	JCheckBox cbColor;
	
	JGPFileFormatComboBox cbFileFormat;
	
	

	public JGPPlotDialog(JGP owner) {
		super(owner);
		this.owner = owner;
		add(createMainPanel());
		pack();
		// TODO Auto-generated constructor stub
	}

	JGPPlotDialog(){
		add(createMainPanel());
		this.pack();
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
		
		cbFontName = new JGPFontComboBox();
		
		tfFontSize = new JTextField("18",2);
		bFileChose.setPreferredSize(new Dimension(20, 20));
		
		cbColor = new JCheckBox();
		//I summon most people will want colored plots.
		cbColor.setSelected(true);
		//tfUpDown.setFont(new Font("Courier", Font.PLAIN, 32));
		
		cbFileFormat = new JGPFileFormatComboBox();

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
		jp.add(new JLabel("Color print"), 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(cbColor, 1, row, 3, 1, GridBagConstraints.HORIZONTAL);
		row += 1;
		jp.add(new JLabel("File format"), 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(cbFileFormat, 1, row, 3, 1, GridBagConstraints.HORIZONTAL);
		row += 1;
		jp.add(bOk, 1, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(bCancel, 3, row, 1, 1, GridBagConstraints.HORIZONTAL);

		return jp;		
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
		String psFileName = this.tfFileName.getText();

		owner.clearShell();

		owner.println("calling GNUplot...");

		JGPgnuplot gp = owner.getGNUplot();
		
		gp.psColor = this.cbColor.isSelected();
		
		gp.psFontName = this.cbFontName.getSelectedItem().toString();
		
		if (!this.tfFontSize.equals(""))
			try {
				gp.psFontSize = Integer.parseInt(tfFontSize.getText());
			} catch (NumberFormatException e1) {
				owner.taShell.setText("Invalid font size: " + tfFontSize.getText());
			}
		
		try {
			gp.setOut(owner);
			gp.plotToFile(psFileName, (JGPFileFormat) cbFileFormat.getSelectedItem() );
		} catch (IOException e) {
			owner.taShell.setText(e.getMessage());
		} catch (InterruptedException e) {
			owner.taShell.setText(e.getMessage());
		}
		
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
