package org.hopandfork.jgnuplot.gui.panel;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.apache.log4j.Logger;
import org.hopandfork.jgnuplot.control.LabelController;
import org.hopandfork.jgnuplot.control.PlotController;
import org.hopandfork.jgnuplot.control.PlottableDataController;
import org.hopandfork.jgnuplot.gui.utility.GridBagConstraintsFactory;
import org.hopandfork.jgnuplot.model.Plot;
import org.hopandfork.jgnuplot.runtime.GnuplotRunner;

/**
 * Panel containing components for plot preview.
 */
public class PreviewPanel extends JGPPanel implements Observer, GnuplotRunner.ImageConsumer, ComponentListener {

	private static final long serialVersionUID = 824126200434468835L;

	private PlotController plotController;
	private Image image = null;
	static private final double WIDTH_HEIGHT_RATIO = 4.0/3.0;

    private boolean refreshEnabled = true;

	static final private Logger LOG = Logger.getLogger(PreviewPanel.class);

    public PreviewPanel(PlotController plotController, PlottableDataController plottableDataController, LabelController labelController)
    {
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

        int previewWidth, previewHeight;
        double panelRatio = (double)getWidth()/(double)getHeight();
        if (panelRatio > WIDTH_HEIGHT_RATIO) {
            previewHeight = Math.max(900,getHeight());
            previewWidth = (int)((double)previewHeight*WIDTH_HEIGHT_RATIO);
        } else {
            previewWidth = Math.max(1200, getWidth());
            previewHeight = (int)((double)previewWidth/WIDTH_HEIGHT_RATIO);
        }

        Plot plot = plotController.getCurrent();
        String plotScript = plot.toPlotString();
        plotScript = "set terminal pngcairo size " + previewWidth +  "," + previewHeight + "\n" + plotScript;
        GnuplotRunner pr = new GnuplotRunner(plotScript, this);
        new Thread(pr).start();
    }

    @Override
    public void readImage(Image image) {
        this.image = image;
        renderImage();
    }

    private void renderImage ()
    {
        this.removeAll();

        if (image == null) {
            LOG.debug("Image is null!");
            this.revalidate();
            this.repaint();
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

        Image _image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(_image));

        GridBagConstraints gbc = GridBagConstraintsFactory.create(0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH);
        this.add(label, gbc);
        this.revalidate();
        this.repaint();
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
    public void componentResized(ComponentEvent componentEvent) {
        renderImage();
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
