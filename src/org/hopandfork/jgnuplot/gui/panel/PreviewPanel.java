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

package org.hopandfork.jgnuplot.gui.panel;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.hopandfork.jgnuplot.control.LabelController;
import org.hopandfork.jgnuplot.control.PlotController;
import org.hopandfork.jgnuplot.control.PlottableDataController;
import org.hopandfork.jgnuplot.gui.presenter.panel.PreviewInterface;
import org.hopandfork.jgnuplot.gui.utility.GridBagConstraintsFactory;
import org.hopandfork.jgnuplot.model.Plot;
import org.hopandfork.jgnuplot.runtime.GnuplotRunner;
import org.hopandfork.jgnuplot.runtime.terminal.PngcairoTerminal;
import org.hopandfork.jgnuplot.runtime.terminal.Terminal;

/**
 * Panel containing components for plot preview.
 */
public class PreviewPanel extends JGPPanel implements PreviewInterface {

	private static final long serialVersionUID = 824126200434468835L;




	public PreviewPanel() {
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
	}


    public void clear()
    {
        safeRemoveAllComponents();
    }

    private void safeRemoveAllComponents() {
        if (!SwingUtilities.isEventDispatchThread()) {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        PreviewPanel.this.removeAll();
                    }
                });
            } catch (InterruptedException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            removeAll();
        }
    }

    public void displayImage (Image image)
    {
        safeRemoveAllComponents();

        if (image == null) {
            clear();
            revalidate();
            repaint();
            return;
        }

        int width, height;
        double panelRatio = (double)getWidth()/(double)getHeight();
        double imageRatio = (double)image.getWidth(null)/(double)image.getHeight(null);

        if (panelRatio > imageRatio) {
            height = getHeight();
            width = (int)((double)height*imageRatio);
        } else {
            width = getWidth();
            height = (int)((double)width/imageRatio);
        }

        if (height < 1 || width < 1)
            return; /* panel hidden */

        final Image _image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JLabel label = new JLabel(new ImageIcon(_image));
                GridBagConstraints gbc = GridBagConstraintsFactory.create(0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH);
                PreviewPanel.this.add(label, gbc);
                PreviewPanel.this.revalidate();
                PreviewPanel.this.repaint();
            }
        });
    }


    public void displayMessage (String message)
    {
        JLabel label;
        label = new JLabel(message);

        GridBagConstraints gbc = GridBagConstraintsFactory.create(0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH);
        this.removeAll();
        this.add(label, gbc);
        this.revalidate();
    }

}
