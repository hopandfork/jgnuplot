package org.hopandfork.jgnuplot.gui.panel;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.hopandfork.jgnuplot.control.LabelController;
import org.hopandfork.jgnuplot.control.PlotController;
import org.hopandfork.jgnuplot.control.PlottableDataController;
import org.hopandfork.jgnuplot.gui.utility.GridBagConstraintsFactory;
import org.hopandfork.jgnuplot.model.Plot;
import org.hopandfork.jgnuplot.runtime.GnuplotRunner;

/**
 * Panel containing components for plot preview.
 */
public class PreviewPanel extends JGPPanel implements Observer, GnuplotRunner.ImageConsumer {

	private static final long serialVersionUID = 824126200434468835L;

	private PlotController plotController;

    public PreviewPanel(PlotController plotController, PlottableDataController plottableDataController, LabelController labelController)
    {
        this.plotController = plotController;

        plotController.addObserver(this);
        plottableDataController.addObserver(this);
        labelController.addObserver(this);

        GridBagLayout gbl = new GridBagLayout();
        this.setLayout(gbl);
    }

    @Override
    public void update(Observable observable, Object o) {
        refreshPlot();
    }

    private void refreshPlot() {
        Plot plot = plotController.getCurrent();
        String plotScript = plot.toPlotString();
        plotScript = "set terminal pngcairo size 600,400\n" + plotScript; // TODO size
        GnuplotRunner pr = new GnuplotRunner(plotScript, this);
        new Thread(pr).start();
    }

    @Override
    public void readImage(Image image) {
        JLabel label;

        if (image == null)
        	return;

        label = new JLabel(new ImageIcon(image));

        GridBagConstraints gbc = GridBagConstraintsFactory.create(0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH);
        this.removeAll();
        this.add(label, gbc);
        this.revalidate();
    }
}
