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
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.hopandfork.jgnuplot.control.PlottableDataController;
import org.hopandfork.jgnuplot.gui.DataSelector;
import org.hopandfork.jgnuplot.gui.panel.JGPPanel;
import org.hopandfork.jgnuplot.gui.combobox.StyleComboBox;
import org.hopandfork.jgnuplot.model.DataFile;
import org.hopandfork.jgnuplot.model.DataSelection;
import org.hopandfork.jgnuplot.model.style.PlottingStyle;

/**
 * This class allows to add or edit a plottable object like Function or
 * DataFile.
 */
public class DataFileDialog extends PlottableDataDialog implements ActionListener {

    private static Logger LOG = Logger.getLogger(DataFileDialog.class);

    private static final long serialVersionUID = -4285893032555125235L;

    private static final String TITLE = "Data File";

    private JTextField tfFileName;

    private JTextField tfTitle;

    private StyleComboBox cbStyle;

    private JButton bFileChose;

    private DataSelector dataSelectionTable;

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
     * @param dataFile   the object from which values are taken.
     * @param controller a PlottableDataController to update the PlottableData in case
     *                   of changes.
     * @throws NullPointerException is thrown in case of null dataFile.
     */
    public DataFileDialog(DataFile dataFile, PlottableDataController controller) throws IOException {
        this.plottableObject = dataFile;
        this.controller = controller;

        add(createMainPanel());

		/* Checks if a PlottableData is valid. */
        if (plottableObject != null) {
            initFields();
        } else {
            throw new NullPointerException("dataFile has to be not null");
        }

        this.setTitle(TITLE);
        this.pack();
        this.setModal(true);
    }

    /**
     * This method allows to set all field in the dialog to apply some changes.
     */
    private void initFields() {

        DataFile dataFile = (DataFile) plottableObject;
        tfFileName.setText(dataFile.getFileName());

        dataSelectionTable.update(dataFile.getFileName());
        dataSelectionTable.updateDataSelection(dataFile.getDataSelection());

        tfTitle.setText(dataFile.getTitle());

        cbStyle.setSelectedItem(dataFile.getStyle());
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

        tfTitle = new JTextField("", 6);
        tfTitle.setToolTipText("The title will be passed vie the 'title' option of the plot command to gnuplot.");

        cbStyle = new StyleComboBox();

        dataSelectionTable = new DataSelector();

        int row = 0;
        jp.add(new JLabel("File"), 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
        jp.add(tfFileName, 1, row, 3, 1, GridBagConstraints.HORIZONTAL);
        jp.add(bFileChose, 4, row, 1, 1, GridBagConstraints.HORIZONTAL);

        row += 1;
        jp.add(new JLabel("Columns selection"), 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
        // Create the scroll pane and add the table to it.
        jp.add(dataSelectionTable, 1, row, 3, 1, GridBagConstraints.HORIZONTAL);

        row += 1;
        jp.add(new JLabel("Title"), 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
        jp.add(tfTitle, 1, row, 3, 1, GridBagConstraints.HORIZONTAL);
        row += 1;
        jp.add(new JLabel("Style"), 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
        jp.add(cbStyle, 1, row, 3, 1, GridBagConstraints.HORIZONTAL);

        row += 1;
        jp.add(bOk, 1, row, 1, 1, GridBagConstraints.HORIZONTAL);
        jp.add(bCancel, 2, row, 1, 1, GridBagConstraints.HORIZONTAL);

        return jp;
    }

    /**
     * This method allows to manage actions performed on the AddDialog.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("..."))
            acFileBrowse();
        else if (e.getActionCommand().equals("ok")) {
            // TODO checks if file path is empty
            // TODO checks if function is empty
            acApply();
            this.setVisible(false);
        } else if (e.getActionCommand().equals("cancel"))
            this.setVisible(false);
    }

    /**
     * This method allows to create a new DataFile or modify an existing one.
     */
    private void acApply() {
        DataSelection dataSelection = dataSelectionTable.getDataSelection();

        if (plottableObject != null) {
            controller.updateDataFile((DataFile) plottableObject, tfFileName.getText(), tfTitle.getText(),
                    (PlottingStyle) cbStyle.getSelectedItem(), dataSelectionTable.getDataSelection());
        } else {
            /* Adds new function */
            if (dataSelection != null) {
                controller.addDataFile(tfFileName.getText(), tfTitle.getText(),
                        (PlottingStyle) cbStyle.getSelectedItem(), dataSelectionTable.getDataSelection());

                this.setVisible(false);
            }
        }
    }

    private void acFileBrowse() {

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
            this.pack();
        }
    }

}

