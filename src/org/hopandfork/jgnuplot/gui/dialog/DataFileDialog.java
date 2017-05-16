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

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.hopandfork.jgnuplot.control.PlottableDataController;
import org.hopandfork.jgnuplot.gui.JGPPanel;
import org.hopandfork.jgnuplot.gui.StyleComboBox;
import org.hopandfork.jgnuplot.model.DataFile;
import org.hopandfork.jgnuplot.model.PlottableData;
import org.hopandfork.jgnuplot.model.style.PlottingStyle;

/**
 * 
 * This class allows to add or edit a plottable object like Function or
 * DataFile.
 *
 */
public class DataFileDialog extends PlottableDataDialog implements ActionListener {

	private static final long serialVersionUID = -4285893032555125235L;

	public static final String TITLE = "Data File";

	protected JTextField tfFileName;

	protected JTextField tfTitle;

	protected StyleComboBox cbStyle;

	protected JTextField tfPreProcess;

	private JLabel lColumnSelection;

	private JButton bFileChose;

	private JButton bProgChose;

	private JDataSelection dataSelectionTable;

	/**
	 * This constructor allows to create a PlottableDataDialog with a specified
	 *
	 * @param controller Controller.
	 */
	public DataFileDialog(PlottableDataController controller) {
		this.controller = controller;

		add(createMainPanel());

		this.setTitle(TITLE);
		this.pack();
		this.setModal(true);
	}

	/**
	 * This constructor is used to create a PlottableDataDialog filled with a
	 * PlottableData values, ready to be modified.
	 * 
	 * @param plottableObject
	 *            the object from which values are taken.
	 * @param controller
	 *            a PlottableDataController to update the PlottableData in case
	 *            of changes.
	 * @throws NullPointerException
	 *             is thrown in case of null plottableObject.
	 */
	public DataFileDialog(PlottableData plottableObject, PlottableDataController controller) throws IOException {

		/* Checks if a PlottableData is valid. */
		if (plottableObject != null) {
			initFields();
			this.plottableObject = plottableObject;
		} else {
			throw new NullPointerException("plottableObject has to be not null");
		}
	}

	/**
	 * This method allows to set all field in the dialog to apply some changes.
	 */
	private void initFields() {

		DataFile dataFile = (DataFile) plottableObject;
		tfFileName.setText(dataFile.getFileName());
		tfPreProcess.setText(dataFile.getPreProcessProgram());

		tfTitle.setText(plottableObject.getTitle());

		cbStyle.setSelectedItem(plottableObject.getStyle());
	}

	private JPanel createMainPanel() {
		// Create the panel.
		JGPPanel jp = new JGPPanel();

		// Set the default panel layout.
		GridBagLayout gbl = new GridBagLayout();
		jp.setLayout(gbl);

		// Create the buttons.
		bFileChose = new JButton("...");
		bFileChose.setPreferredSize(new Dimension(20, 20));
		bFileChose.setActionCommand("...");
		bFileChose.addActionListener(this);

		bProgChose = new JButton("...");
		bProgChose.setPreferredSize(new Dimension(20, 20));
		bProgChose.setActionCommand("progchoose");
		bProgChose.addActionListener(this);

		JButton bOk = new JButton("ok");
		bOk.setPreferredSize(new Dimension(60, 20));
		bOk.setActionCommand("ok");
		bOk.addActionListener(this);

		JButton bAdvanced = new JButton("Advanced");
		bAdvanced.setPreferredSize(new Dimension(100, 20));
		bAdvanced.setActionCommand("advanced");
		bAdvanced.addActionListener(this);

		JButton bCancel = new JButton("cancel");
		bCancel.setPreferredSize(new Dimension(100, 20));
		bCancel.setActionCommand("cancel");
		bCancel.addActionListener(this);

		tfFileName = new JTextField("", 6);
		tfFileName.setToolTipText("Path to the data file.");

		tfPreProcess = new JTextField("", 6);
		tfPreProcess.setToolTipText("Allows to preprocess the file throu an external command.\n"
				+ "Variables: $if == input file, $of == ouput file. \n"
				+ "Example: cat $if | sort -n > $of; sorts the data file.");

		tfTitle = new JTextField("", 6);
		tfTitle.setToolTipText("The title will be passed vie the 'title' option of the plot command to gnuplot.");

		cbStyle = new StyleComboBox();

		dataSelectionTable = new JDataSelection();

		int row = 0;
		jp.add(new JLabel("File"), 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(tfFileName, 1, row, 3, 1, GridBagConstraints.HORIZONTAL);
		jp.add(bFileChose, 4, row, 1, 1, GridBagConstraints.HORIZONTAL);
		row += 1;
		jp.add(new JLabel("Preprocess external program"), 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(tfPreProcess, 1, row, 3, 1, GridBagConstraints.HORIZONTAL);
		jp.add(bProgChose, 4, row, 1, 1, GridBagConstraints.HORIZONTAL);

		row += 1;
		jp.add(lColumnSelection = new JLabel("Columns selection"), 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
		// Create the scroll pane and add the table to it.
		jp.add(dataSelectionTable, 1, row, 3, 1, GridBagConstraints.HORIZONTAL);

		row += 1;
		jp.add(new JLabel("Title"), 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(tfTitle, 1, row, 3, 1, GridBagConstraints.HORIZONTAL);
		row += 1;
		jp.add(new JLabel("Style"), 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(cbStyle, 1, row, 3, 1, GridBagConstraints.HORIZONTAL);

		row += 1;
		jp.add(bAdvanced, 1, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(bOk, 2, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(bCancel, 3, row, 1, 1, GridBagConstraints.HORIZONTAL);

		return jp;
	}

	/**
	 * This method allows to manage actions performed on the AddDialog.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("..."))
			acFileBrowse();
		if (e.getActionCommand().equals("progchoose"))
			acProgBrowse();
		else if (e.getActionCommand().equals("ok")) {
			// TODO checks if file path is empty
			// TODO checks if function is empty
			acApply();
			this.setVisible(false);
		} else if (e.getActionCommand().equals("advanced")) {
			// TODO change AddDialog content to advanced option
			// PreProcessprogram

		} else if (e.getActionCommand().equals("cancel"))
			this.setVisible(false);
	}

	// TODO integrate in this dialog
	public void acApply() {

		controller.addDataFile(tfFileName.getText(), tfTitle.getText(), (PlottingStyle) cbStyle.getSelectedItem(),
				dataSelectionTable.getDataSelection(), tfPreProcess.getText());

		this.setVisible(false);
	}

	public void acFileBrowse() {

		File f;

		if (!this.tfFileName.getText().trim().equals("")) {
			f = new File(tfFileName.getText().trim());
			if (!f.exists())
				f = new File(f.getPath());
		} else
			f = new File(".");

		// Open a file chooser that points to the current dir.
		JFileChooser file_chooser = new JFileChooser(f.getPath());

		// Set the Open dialog box size.
		file_chooser.setPreferredSize(new Dimension(800, 500));

		// Set to select directory
		file_chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		// Show the Open dialog box (returns the option selected)
		int selected = file_chooser.showOpenDialog(this);

		// If the Open button is pressed.
		if (selected == JFileChooser.APPROVE_OPTION) {
			// Get the selected file.
			tfFileName.setText(file_chooser.getSelectedFile().toString());
			tfFileName.setToolTipText(file_chooser.getSelectedFile().toString());
			dataSelectionTable.update(tfFileName.getText());
			return;
		}
		// If the Cancel button is pressed.
		else if (selected == JFileChooser.CANCEL_OPTION) {
			return;
		}

	}

	public void acProgBrowse() {

		File f;

		if (!this.tfFileName.getText().trim().equals("")) {
			f = new File(tfFileName.getText().trim());
			if (!f.exists())
				f = new File(f.getPath());
		} else
			f = new File(".");

		// Open a file chooser that points to the current dir.
		JFileChooser file_chooser = new JFileChooser(f.getPath());

		// Set the Open dialog box size.
		file_chooser.setPreferredSize(new Dimension(800, 500));

		// Set to select directory
		file_chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		// Show the Open dialog box (returns the option selected)
		int selected = file_chooser.showOpenDialog(this);

		// If the Open button is pressed.
		if (selected == JFileChooser.APPROVE_OPTION) {
			// Get the selected file.
			tfPreProcess.setText(file_chooser.getSelectedFile().toString() + " $if $of");
			tfPreProcess.setToolTipText(file_chooser.getSelectedFile().toString());
			return;
		}
		// If the Cancel button is pressed.
		else if (selected == JFileChooser.CANCEL_OPTION) {
			return;
		}

	}
}
