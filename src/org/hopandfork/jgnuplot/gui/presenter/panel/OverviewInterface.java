package org.hopandfork.jgnuplot.gui.presenter.panel;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;

public interface OverviewInterface {
		
		JPanel toJPanel();
		
		JButton getButtonAddLabel();
		
		JButton getButtonEditLabel();
		
		JButton getButtonDeleteLabel();
		
		JButton getButtonAddDataFile();
		
		JButton getButtonAddFunction();
		
		JButton getButtonEdit();
		
		JButton getButtonDelete();
		
		JButton getButtonMoveUp();
		
		JButton getButtonMoveDown();
		
		JTable getPlottableDataTable();
		
		JTable getLabelTable();
		
		JTextArea getPrePlotString();
}
