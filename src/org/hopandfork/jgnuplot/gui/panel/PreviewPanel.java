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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
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
import org.hopandfork.jgnuplot.gui.utility.GridBagConstraintsFactory;
import org.hopandfork.jgnuplot.model.Plot;
import org.hopandfork.jgnuplot.runtime.GnuplotRunner;
import org.hopandfork.jgnuplot.runtime.terminal.PngcairoTerminal;
import org.hopandfork.jgnuplot.runtime.terminal.Terminal;

/**
 * Panel containing components for plot preview.
 */
public class PreviewPanel extends JGPPanel implements Observer, GnuplotRunner.ImageConsumer, ComponentListener {

	private static final long serialVersionUID = 824126200434468835L;

	private PlotController plotController;
	private Image image = null;
	static private final double WIDTH_HEIGHT_RATIO = 4.0 / 3.0;

	private boolean refreshEnabled = true;

	static final private Logger LOG = Logger.getLogger(PreviewPanel.class);

	public PreviewPanel(PlotController plotController, PlottableDataController plottableDataController,
			LabelController labelController) {
		this.plotController = plotController;

		plotController.addObserver(this);
		plottableDataController.addObserver(this);
		labelController.addObserver(this);

		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);

		this.addComponentListener(this);
	}

	@Override
	public void update(Observable observable, Object o) {
		refreshPlot();
	}

	private void refreshPlot() {
		if (!refreshEnabled)
			return;
		
		/* Evaluates if nothing have to be plotted */
		if (plotController.getCurrent().getPlottableData().size() > 0) {
			int previewWidth, previewHeight;
			double panelRatio = (double) getWidth() / (double) getHeight();
			if (panelRatio > WIDTH_HEIGHT_RATIO) {
				previewHeight = Math.max(900, getHeight());
				previewWidth = (int) ((double) previewHeight * WIDTH_HEIGHT_RATIO);
			} else {
				previewWidth = Math.max(1200, getWidth());
				previewHeight = (int) ((double) previewWidth / WIDTH_HEIGHT_RATIO);
			}

	        Plot plot = plotController.getCurrent();
	        Terminal terminal = new PngcairoTerminal(previewWidth, previewHeight);
	        GnuplotRunner.runGnuplot(terminal, plot, this);
		} else {
			//TODO Back to an empty preview
			LOG.info("Shows an empty preview");
		}
	}

	@Override
	public void componentResized(ComponentEvent componentEvent) {
		renderImage();
	}

    @Override
    public void onImageGenerated (Image image) {
        this.image = image;
        renderImage();
    }

    @Override
    public void onImageGenerated (File outputFile) {
        /* nothing to do */
    }

    protected void renderEmptyPreview()
    {
        // TODO
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
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            removeAll();
        }
    }

    private void renderImage ()
    {
        safeRemoveAllComponents();

        if (image == null) {
            renderEmptyPreview();
            revalidate();
            repaint();
            return;
        }

        int width, height;
        double panelRatio = (double)getWidth()/(double)getHeight();
        if (panelRatio > WIDTH_HEIGHT_RATIO) {
            height = getHeight();
            width = (int)((double)height*WIDTH_HEIGHT_RATIO);
        } else {
            width = getWidth();
            height = (int)((double)width/WIDTH_HEIGHT_RATIO);
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

    @Override
    public void onImageGenerationError(String errorMessage) {
        JLabel label;
        label = new JLabel(errorMessage);

        GridBagConstraints gbc = GridBagConstraintsFactory.create(0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH);
        this.removeAll();
        this.add(label, gbc);
        this.revalidate();
    }

    @Override
    public void componentMoved(ComponentEvent componentEvent) {
        // nothing to do here
    }

    @Override
    public void componentShown(ComponentEvent componentEvent) {
    	refreshEnabled = true;
        LOG.info("Enabled preview refreshing");
    }

    @Override
    public void componentHidden(ComponentEvent componentEvent) {
        refreshEnabled = false;
        LOG.info("Disabled preview refreshing");
    }
}
