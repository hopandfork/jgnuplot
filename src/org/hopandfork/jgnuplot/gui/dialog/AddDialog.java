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
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.hopandfork.jgnuplot.control.PlottableDataController;
import org.hopandfork.jgnuplot.gui.JGPPanel;
import org.hopandfork.jgnuplot.gui.StyleComboBox;
import org.hopandfork.jgnuplot.model.*;
import org.hopandfork.jgnuplot.model.style.PlottingStyle;


public class AddDialog extends JGPDialog implements ActionListener {

	JTextField tfFileName;

	JTextField tfDataString;

	JTextField tfTitle;

	JComboBox cbStyle;

	JTextField tfPreProcess;

	JRadioButton rbFile;

	JRadioButton rbFunc;

	JLabel lFunction;

	JButton bFileChose;

	JButton bProgChose;

	JButton bDataStringAssist;

	static PlottableData plotObject;

	private PlottableDataController controller;


	AddDialog(String title, PlottableDataController controller) {
		add(createMainPanel());
		this.setTitle(title);
		this.pack();
		this.controller = controller;
	}

	AddDialog(PlottableData plotObject, String title, PlottableDataController controller) {
		add(createMainPanel());
		this.setTitle(title);
		AddDialog.plotObject = plotObject;
		initPlotObject(plotObject);

		this.pack();
	}

	private JPanel createMainPanel() {
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
		bFileChose = new JButton("...");
		bFileChose.setPreferredSize(new Dimension(20, 20));
		bFileChose.setActionCommand("...");
		bFileChose.addActionListener(this);

		bProgChose = new JButton("...");
		bProgChose.setPreferredSize(new Dimension(20, 20));
		bProgChose.setActionCommand("progchoose");
		bProgChose.addActionListener(this);

		bDataStringAssist = new JButton("...");
		bDataStringAssist.setPreferredSize(new Dimension(20, 20));
		bDataStringAssist.setActionCommand("DataStringAssist");
		bDataStringAssist.addActionListener(this);

		JButton bOk = new JButton("ok");
		bOk.setPreferredSize(new Dimension(60, 20));
		bOk.setActionCommand("ok");
		bOk.addActionListener(this);

		JButton bApply = new JButton("apply");
		bApply.setPreferredSize(new Dimension(100, 20));
		bApply.setActionCommand("apply");
		bApply.addActionListener(this);

		JButton bCancel = new JButton("cancel");
		bCancel.setPreferredSize(new Dimension(100, 20));
		bCancel.setActionCommand("cancel");
		bCancel.addActionListener(this);

		rbFile = new JRadioButton("file", true);
		rbFunc = new JRadioButton("function");
		rbFile.addActionListener(this);
		rbFile.setActionCommand("file");
		rbFunc.addActionListener(this);
		rbFunc.setActionCommand("function");


		ButtonGroup group = new ButtonGroup();
		group.add(rbFile);
		group.add(rbFunc);


		tfFileName = new JTextField("", 6);
		tfFileName.setToolTipText("Path to the data file.");

		tfPreProcess = new JTextField("", 6);
		tfPreProcess.setToolTipText("Allows to preprocess the file throu an external command.\n" +
				"Variables: $if == input file, $of == ouput file. \n" +
				"Example: cat $if | sort -n > $of; sorts the data file.");

		tfDataString = new JTextField("1:2", 6);
		tfDataString.setToolTipText("This sting will be passed as 'using' part \n" +
				"of the plot command to gnuplot. Start gnuplot \n" +
				"from the command line and enter 'help using' \n" +
				"for documentation.");

		tfTitle = new JTextField("", 6);
		tfTitle.setToolTipText("The title will be passed vie the 'title' option of the plot command to gnuplot.");

		cbStyle = new StyleComboBox();
		//tfLeftRight.setFont(new Font("Courier", Font.PLAIN, 32));

		int row = 0;
		jp.add(new JLabel("Source:"), 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(rbFile, 1, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(rbFunc, 2, row, 2, 1, GridBagConstraints.HORIZONTAL);
		row += 1;
		jp.add(new JLabel("File"), 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(tfFileName, 1, row, 3, 1, GridBagConstraints.HORIZONTAL);
		jp.add(bFileChose, 4, row, 1, 1, GridBagConstraints.HORIZONTAL);
		row += 1;
		jp.add(new JLabel("Preprocess external program"), 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(tfPreProcess, 1, row, 3, 1, GridBagConstraints.HORIZONTAL);
		jp.add(bProgChose, 4, row, 1, 1, GridBagConstraints.HORIZONTAL);

		row += 1;
		jp.add(lFunction = new JLabel("Datastring"), 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(tfDataString, 1, row, 3, 1, GridBagConstraints.HORIZONTAL);
		jp.add(bDataStringAssist, 4, row, 1, 1, GridBagConstraints.HORIZONTAL);
		row += 1;
		jp.add(new JLabel("Title"), 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(tfTitle, 1, row, 3, 1, GridBagConstraints.HORIZONTAL);
		row += 1;
		jp.add(new JLabel("Style"), 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(cbStyle, 1, row, 3, 1, GridBagConstraints.HORIZONTAL);

		row += 1;
		jp.add(bOk, 1, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(bApply, 2, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(bCancel, 3, row, 1, 1, GridBagConstraints.HORIZONTAL);

		return jp;
	}

	/**
	 * Initializes alls fields with the values from a iven plot object
	 *
	 * @param plotObject
	 */
	private void initPlotObject(PlottableData plotObject) {
		//should never happen....but does not hurt to check
		if (null == plotObject)
			return;

		if (plotObject.getClass().equals(Function.class)) {
			rbFile.setSelected(true);
		} else
			rbFile.setSelected(true);

		if (plotObject instanceof DataFile) {
			DataFile ds = (DataFile) plotObject;
			tfFileName.setText(ds.getFileName());
			tfPreProcess.setText(ds.getPreProcessProgram());
		}


		if (plotObject instanceof DataFile) {
			DataFile df = (DataFile) plotObject;
			tfDataString.setText(df.getDataSelection().toString());
		} else {
			Function f = (Function) plotObject;
			tfDataString.setText(f.getFunctionString());
		}

		tfTitle.setText(plotObject.getTitle());

		cbStyle.setSelectedItem(plotObject.getStyle());

	}

	private static final long serialVersionUID = 1L;


	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("..."))
			acFileBrowse();
		if (e.getActionCommand().equals("progchoose"))
			acProgBrowse();
		else if (e.getActionCommand().equals("ok")) {
			acApply();
			this.setVisible(false);
		} else if (e.getActionCommand().equals("apply"))
			acApply();
		else if (e.getActionCommand().equals("cancel"))
			this.setVisible(false);
		else if (e.getActionCommand().equals("DataStringAssist"))
			acView();
		else if (e.getActionCommand().equals("file")) {
			bDataStringAssist.setEnabled(true);
			tfFileName.setEnabled(true);
			bFileChose.setEnabled(true);
			tfPreProcess.setEnabled(true);
			bProgChose.setEnabled(true);
			lFunction.setText("Datastring");
		} else if (e.getActionCommand().equals("function")) {
			bDataStringAssist.setEnabled(false);
			tfFileName.setEnabled(false);
			bFileChose.setEnabled(false);
			tfPreProcess.setEnabled(false);
			bProgChose.setEnabled(false);
			lFunction.setText("Function");
		}
	}

	public void acView() {
		String dataString = PreviewDialog.showDataStringAssist(tfFileName.getText(), tfDataString.getText());
		if (dataString != null)
			this.tfDataString.setText(dataString);
	}

	public void acApply() {

		// TODO check if we are editing!

		if (rbFile.isSelected()) {
			// TODO change the way dataSelection is set by user
			String selectionString = tfDataString.getText();
			String[] tokens = selectionString.split(":");
			DataSelection dataSelection;
			if (tokens.length == 2) {
				dataSelection = new DataSelection2D(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
			} else if (tokens.length == 3) {
				dataSelection = new DataSelection3D(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
			} else {
				return;
			}

			DataFile df = controller.addDataFile(tfFileName.getText(), tfTitle.getText(), (PlottingStyle)cbStyle.getSelectedItem(), dataSelection);
			if (tfPreProcess.getText() != "")
				df.setPreProcessProgram(tfPreProcess.getText());

		} else {
			controller.addFunction(tfDataString.getText(), tfTitle.getText(), (PlottingStyle)cbStyle.getSelectedItem());
		}

		this.setVisible(false);
	}

	public void acFileBrowse() {

		File f;

		if (!this.tfFileName.getText().trim().equals("")) {
			f = new File(tfFileName.getText().trim());
			if (!f.exists()) f = new File(f.getPath());
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
			if (!f.exists()) f = new File(f.getPath());
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


	public static PlottableData showAddDialog(String title, PlottableDataController controller) {
		AddDialog ptm = new AddDialog(title, controller);
		ptm.setModal(true);
		ptm.show();
		ptm.dispose();
		return plotObject;
	}

	public static PlottableData showAddDialog(PlottableData p, String title, PlottableDataController controller) {
		AddDialog ptm = new AddDialog(p, title, controller);
		ptm.setModal(true);
		ptm.show();
		ptm.dispose();
		return plotObject;
	}

}
