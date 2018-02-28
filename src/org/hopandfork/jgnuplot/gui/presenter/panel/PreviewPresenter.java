package org.hopandfork.jgnuplot.gui.presenter.panel;

import org.apache.log4j.Logger;
import org.hopandfork.jgnuplot.control.LabelController;
import org.hopandfork.jgnuplot.control.PlotController;
import org.hopandfork.jgnuplot.control.PlottableDataController;
import org.hopandfork.jgnuplot.model.Plot;
import org.hopandfork.jgnuplot.runtime.GnuplotRunner;
import org.hopandfork.jgnuplot.runtime.terminal.PngcairoTerminal;
import org.hopandfork.jgnuplot.runtime.terminal.Terminal;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class PreviewPresenter implements Observer, GnuplotRunner.ImageConsumer, ComponentListener {

    private PreviewInterface previewPanel;
    private PlotController plotController;
    private Image image = null;
    private boolean refreshEnabled = true;
    static final private Logger LOG = Logger.getLogger(PreviewPresenter.class);

    static private final double WIDTH_HEIGHT_RATIO = 4.0 / 3.0;
    static private final int MIN_HEIGHT_PIXEL = 600;
    static private final int MIN_WIDTH_PIXEL = (int)(WIDTH_HEIGHT_RATIO * MIN_HEIGHT_PIXEL);

    public PreviewPresenter (PreviewInterface previewPanel, PlotController plotController, PlottableDataController plottableDataController,
			LabelController labelController) {
        this.previewPanel = previewPanel;
        previewPanel.addComponentListener(this);


        this.plotController = plotController;
        plotController.addObserver(this);
        plottableDataController.addObserver(this);
        labelController.addObserver(this);
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

            Dimension panelDimension = previewPanel.getSize();
            int panelWidth = panelDimension.width;
            int panelHeight = panelDimension.height;
            double panelRatio = (double) panelWidth / (double) panelHeight;

            if (panelRatio > WIDTH_HEIGHT_RATIO) {
                previewHeight = Math.max(MIN_HEIGHT_PIXEL, panelHeight);
                previewWidth = (int) ((double) previewHeight * WIDTH_HEIGHT_RATIO);
            } else {
                previewWidth = Math.max(MIN_WIDTH_PIXEL, panelWidth);
                previewHeight = (int) ((double) previewWidth / WIDTH_HEIGHT_RATIO);
            }

            Plot plot = plotController.getCurrent();
            Terminal terminal = new PngcairoTerminal(previewWidth, previewHeight);
            GnuplotRunner.runGnuplot(terminal, plot, this);
        } else {
            previewPanel.clear();
        }
    }

    private void renderImage()
    {
        previewPanel.displayImage(image);
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
    @Override
    public void onImageGenerationError(String errorMessage) {
        previewPanel.displayMessage(errorMessage);
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
