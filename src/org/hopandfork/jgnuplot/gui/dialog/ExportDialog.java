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


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;
import org.hopandfork.jgnuplot.control.PlotController;
import org.hopandfork.jgnuplot.gui.combobox.FontComboBox;
import org.hopandfork.jgnuplot.gui.panel.JGPPanel;
import org.hopandfork.jgnuplot.gui.utility.GridBagConstraintsFactory;
import org.hopandfork.jgnuplot.gui.utility.JOptionPaneUtils;
import org.hopandfork.jgnuplot.model.OutputFileFormat;
import org.hopandfork.jgnuplot.model.Plot;
import org.hopandfork.jgnuplot.runtime.GnuplotRunner;
import org.hopandfork.jgnuplot.runtime.terminal.PngcairoTerminal;
import org.hopandfork.jgnuplot.runtime.terminal.PostscriptTerminal;
import org.hopandfork.jgnuplot.runtime.terminal.SvgTerminal;
import org.hopandfork.jgnuplot.runtime.terminal.Terminal;


public class ExportDialog extends JGPDialog implements ActionListener, GnuplotRunner.ImageConsumer {

    private static Logger LOG = Logger.getLogger(ExportDialog.class);

    private static final long serialVersionUID = 1L;

    private JTextField tfFileName;
    private FontComboBox cbFontName;
    private SpinnerNumberModel spinnerFontSizeModel;
    private SpinnerNumberModel spinnerWidthModel;
    private SpinnerNumberModel spinnerHeightModel;
    private JComboBox<OutputFileFormat> cbFileFormat;
    private JCheckBox chkAutomaticFont;

    private PlotController plotController;

    public ExportDialog(PlotController plotController) {
        super();
        this.plotController = plotController;
        add(createMainPanel());
        pack();
    }

    private JPanel createMainPanel() {
        // Create the panel.
        JGPPanel jp = new JGPPanel();

        // Set the default panel layout.
        GridBagLayout gbl = new GridBagLayout();
        jp.setLayout(gbl);

        // Create the buttons.
        JButton bFileChose = new JButton("...");
        bFileChose.setActionCommand("...");
        bFileChose.addActionListener(this);

        JButton bOk = new JButton("ok");
        bOk.setActionCommand("ok");
        bOk.addActionListener(this);

        JButton bCancel = new JButton("cancel");
        bCancel.setActionCommand("cancel");
        bCancel.addActionListener(this);


        tfFileName = new JTextField("", 20);

        chkAutomaticFont = new JCheckBox("Auto", true);
        cbFontName = new FontComboBox();
        cbFontName.setEnabled(!chkAutomaticFont.isSelected());

        spinnerFontSizeModel = new SpinnerNumberModel(18, 1, 999, 1);

        final JSpinner tfFontSize = new JSpinner(spinnerFontSizeModel);
        tfFontSize.setEnabled(!chkAutomaticFont.isSelected());

        chkAutomaticFont.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                cbFontName.setEnabled(!chkAutomaticFont.isSelected());
                tfFontSize.setEnabled(!chkAutomaticFont.isSelected());
            }
        });

        spinnerWidthModel = new SpinnerNumberModel(1200, 1, 9999, 5);
        JSpinner spinnerWidth = new JSpinner(spinnerWidthModel);
        spinnerHeightModel = new SpinnerNumberModel(800, 1, 9999, 5);
        JSpinner spinnerHeight = new JSpinner(spinnerHeightModel);

        cbFileFormat = new JComboBox<>();
        for (OutputFileFormat format : OutputFileFormat.values())
            cbFileFormat.addItem(format);

        GridBagConstraints gbc;
        int row = 0;
        gbc = GridBagConstraintsFactory.create(0, row, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
        jp.add(new JLabel("Format"), gbc);
        gbc = GridBagConstraintsFactory.create(1, row, 3, 1, 10, 0, GridBagConstraints.HORIZONTAL);
        jp.add(cbFileFormat, gbc);

        row += 1;
        gbc = GridBagConstraintsFactory.create(0, row, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
        jp.add(new JLabel("Width (px)"), gbc);
        gbc = GridBagConstraintsFactory.create(1, row, 1, 1, 10, 0, GridBagConstraints.HORIZONTAL);
        jp.add(spinnerWidth, gbc);
        gbc = GridBagConstraintsFactory.create(2, row, 1, 1, 10, 0, GridBagConstraints.NONE, GridBagConstraints.EAST);
        jp.add(new JLabel("Height (px)"), gbc);
        gbc = GridBagConstraintsFactory.create(3, row, 1, 1, 10, 0, GridBagConstraints.HORIZONTAL);
        jp.add(spinnerHeight, gbc);

        row += 1;
        gbc = GridBagConstraintsFactory.create(0, row, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
        jp.add(new JLabel("Font"), gbc);
        gbc = GridBagConstraintsFactory.create(1, row, 2, 1, 10, 0, GridBagConstraints.HORIZONTAL);
        jp.add(cbFontName, gbc);
        gbc = GridBagConstraintsFactory.create(3, row, 1, 1, 10, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
        jp.add(chkAutomaticFont, gbc);

        row += 1;
        gbc = GridBagConstraintsFactory.create(0, row, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
        jp.add(new JLabel("Font size"), gbc);
        gbc = GridBagConstraintsFactory.create(1, row, 1, 1, 10, 0, GridBagConstraints.HORIZONTAL);
        jp.add(tfFontSize, gbc);

        row += 1;
        gbc = GridBagConstraintsFactory.create(0, row, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
        jp.add(new JLabel("Filename"), gbc);
        gbc = GridBagConstraintsFactory.create(1, row, 2, 1, 10, 0, GridBagConstraints.HORIZONTAL);
        jp.add(tfFileName, gbc);
        gbc = GridBagConstraintsFactory.create(3, row, 1, 1, 10, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
        jp.add(bFileChose, gbc);

        row += 1;
        gbc = GridBagConstraintsFactory.create(0, row, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
        jp.add(bOk, gbc);
        gbc = GridBagConstraintsFactory.create(1, row, 1, 1, 10, 0, GridBagConstraints.NONE, GridBagConstraints.WEST);
        jp.add(bCancel, gbc);

        return jp;
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("..."))
            acFileBrowse();
        else if (e.getActionCommand().equals("ok")) {
            acOk();
        } else if (e.getActionCommand().equals("cancel")) {
            this.setVisible(false);
        }
    }

    public void acOk() {
        String outputFileName = this.tfFileName.getText();
        if (outputFileName.isEmpty()) {
            JOptionPaneUtils.showWarning(this, "Export", "A valid output filename must be provided.");
            return;
        }
        File outputFile = new File(outputFileName);

        String fontName = null;
        if (chkAutomaticFont.isSelected() && cbFontName.getSelectedItem() != null)
            fontName = cbFontName.getSelectedItem().toString();
        int fontSize = spinnerFontSizeModel.getNumber().intValue();

        int widthPixels = spinnerWidthModel.getNumber().intValue();
        int heightPixels = spinnerHeightModel.getNumber().intValue();

        Terminal terminal = null;
        OutputFileFormat outputFileFormat = (OutputFileFormat) cbFileFormat.getSelectedItem();

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
        }

        if (terminal != null) {
            Plot plot = plotController.getCurrent();
            GnuplotRunner.runGnuplot(terminal, plot, this);
        }
    }

    public void acFileBrowse() {

        File f;

        if (!this.tfFileName.getText().trim().equals("")) {
            f = new File(tfFileName.getText().trim());
            if (!f.exists()) f = new File(f.getPath());
        } else {
            f = new File(".");
        }

        // Open a file chooser that points to the current dir.
        JFileChooser file_chooser = new JFileChooser(f.getPath());

        // Set to select directory
        file_chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        // Show the Open dialog box (returns the option selected)
        int selected = file_chooser.showOpenDialog(this);

        // If the Open button is pressed.
        if (selected == JFileChooser.APPROVE_OPTION) {
            // Get the selected file.
            tfFileName.setText(file_chooser.getSelectedFile().toString());
            tfFileName.setToolTipText(file_chooser.getSelectedFile().toString());
        }
    }


    @Override
    public void onImageGenerated(Image image) {
        /* should never be called */
    }

    @Override
    public void onImageGenerated(File output) {
        LOG.info("Exported plot.");
        setVisible(false);
    }

    @Override
    public void onImageGenerationError(String errorMessage) {
        /* Something went wrong! */
        LOG.error("Failed plot export: " + errorMessage);
        JOptionPaneUtils.showError(this, "Export", "An error occurred during export:\n"  + errorMessage);
    }
}
