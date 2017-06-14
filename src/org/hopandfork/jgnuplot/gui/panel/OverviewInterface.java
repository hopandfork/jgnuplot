package org.hopandfork.jgnuplot.gui.panel;

import java.util.List;

import javax.swing.JTextArea;

import org.hopandfork.jgnuplot.model.Label;
import org.hopandfork.jgnuplot.model.PlottableData;

public interface OverviewInterface {
		List<Label> getSelectedLabels();

		PlottableData getSelectedPlottableData();

		List<PlottableData> getAllSelectedPlottableData();

		Label getSelectedLabel();

}
