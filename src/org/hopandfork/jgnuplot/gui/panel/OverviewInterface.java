package org.hopandfork.jgnuplot.gui.panel;

import java.util.List;

import javax.swing.JTextArea;

import org.hopandfork.jgnuplot.model.Label;
import org.hopandfork.jgnuplot.model.PlottableData;

public interface OverviewInterface {
		public boolean isPlottableDataSelected();

		public boolean isLabelSelected();

		public List<Label> getSelectedLabels();

		public PlottableData getSelectedPlottableData();

		public List<PlottableData> getSelectedPlottableDatas();

		public Label getSelectedLabel();

		public void addPlottableData(PlottableData plottableData);

		public JTextArea getPrePlotString();
}
