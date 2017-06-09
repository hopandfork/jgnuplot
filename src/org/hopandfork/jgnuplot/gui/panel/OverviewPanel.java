package org.hopandfork.jgnuplot.gui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.hopandfork.jgnuplot.control.LabelController;
import org.hopandfork.jgnuplot.control.PlottableDataController;
import org.hopandfork.jgnuplot.gui.panel.BottomPanel.Display;
import org.hopandfork.jgnuplot.gui.table.ColorEditor;
import org.hopandfork.jgnuplot.gui.table.ColorRenderer;
import org.hopandfork.jgnuplot.gui.table.LabelsTableModel;
import org.hopandfork.jgnuplot.gui.table.PlottableDataTableModel;
import org.hopandfork.jgnuplot.model.Label;
import org.hopandfork.jgnuplot.model.PlottableData;

public class OverviewPanel extends JGPPanel implements Display, ChangeListener {
	public interface BottomDisplay {
		public JButton getbEdit();

		public JButton getbDelete();

		public JButton getbAdd();

		public JButton getbMoveUp();

		public JButton getbMoveDown();
	}

	public interface MenuDisplay {
		public JMenuItem getEdit_menu_item();

		public JMenuItem getDelete_menu_item();

		public JMenuItem getMoveup_menu_item();

		public JMenuItem getMovedown_menu_item();
	}

	private static final long serialVersionUID = 3708875306419236700L;

	private JTabbedPane tp;

	private PlottableDataTableModel plottableDataTableModel;

	private JTable dataSetTable;

	private LabelsTableModel labelTableModel;

	private JTable labelTable;

	private JTextArea prePlotString;

	private PlottableDataController plottableDataController;

	private LabelController labelController;

	private BottomDisplay bottomDisplay;

	private MenuDisplay menuDisplay;

	public OverviewPanel(MenuDisplay menuDisplay, BottomDisplay bottomDisplay,
			PlottableDataController plottableDataController, LabelController labelController) {
		this.menuDisplay = menuDisplay;
		this.bottomDisplay = bottomDisplay;
		this.plottableDataController = plottableDataController;
		this.labelController = labelController;
		
		createCenterPanel();
	}

	private void createCenterPanel() {
		// Create the panel.
		this.setPreferredSize(new Dimension(700, 400));
		this.setBackground(new Color(0xf0f0f0));
		// Set the default panel layout.
		GridBagLayout gbl = new GridBagLayout();

		this.setLayout(gbl);

		int row = 0;
		GridBagConstraints gbc2 = new GridBagConstraints();

		gbc2.gridx = 0;
		gbc2.gridy = 0;
		gbc2.gridwidth = 5;
		gbc2.weightx = 1.0;
		gbc2.weighty = 1.0;
		gbc2.fill = GridBagConstraints.BOTH;

		// this works, the other version does not...don't know why
		this.add(create_tabbed_pane(), gbc2);
	}

	/**
	 * 
	 */
	private JTabbedPane create_tabbed_pane() {
		// Create a new tabbed pane and set the tabs to be on top.
		tp = new JTabbedPane(JTabbedPane.TOP);

		tp.addTab("Datasets", createDataSetPanel());

		tp.addTab("Labels", createLabelSetPanel());

		tp.addTab("Add. plot commands", createPrePlotStringPanel());

		// Set the tab event listener.
		tp.addChangeListener(this);

		return tp;
	}

	private JPanel createLabelSetPanel() {

		// Create the panel.
		JGPPanel jp = new JGPPanel();
		// Set the default panel layout.
		GridBagLayout gbl = new GridBagLayout();
		jp.setLayout(gbl);

		labelTableModel = new LabelsTableModel(labelController);
		labelTable = new JTable(labelTableModel);
		labelTable.setPreferredScrollableViewportSize(new Dimension(500, 200));
		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(labelTable);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		jp.add(scrollPane, gbc);

		return jp;
	}

	private JPanel createDataSetPanel() {

		// Create the panel.
		JGPPanel jp = new JGPPanel();
		// Set the default panel layout.
		GridBagLayout gbl = new GridBagLayout();
		jp.setLayout(gbl);

		plottableDataTableModel = new PlottableDataTableModel(plottableDataController);
		dataSetTable = new JTable(plottableDataTableModel);
		dataSetTable.setPreferredScrollableViewportSize(new Dimension(500, 200));
		// dataSetTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(dataSetTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		// Set up renderer and editor for the Favorite Color column.
		dataSetTable.setDefaultRenderer(Color.class, new ColorRenderer(true));
		dataSetTable.setDefaultEditor(Color.class, new ColorEditor());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		jp.add(scrollPane, gbc);

		return jp;
	}

	private JPanel createPrePlotStringPanel() {
		// Create the panel.
		JGPPanel jp = new JGPPanel();
		// Set the default panel layout.
		GridBagLayout gbl = new GridBagLayout();
		jp.setLayout(gbl);

		prePlotString = new JTextArea(100, 20);
		prePlotString.setWrapStyleWord(true);
		prePlotString.setLineWrap(true);

		JScrollPane scrollPane = new JScrollPane(prePlotString);
		scrollPane.setPreferredSize(new Dimension(520, 240));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		jp.add(scrollPane, gbc);

		return jp;

	}

	@Override
	public boolean isPlottableDataSelected() {
		boolean selected = false;

		if (tp.getSelectedIndex() == 0) {
			selected = true;
		}

		return selected;
	}

	@Override
	public boolean isLabelSelected() {
		boolean selected = false;

		if (tp.getSelectedIndex() == 1) {
			selected = true;
		}

		return selected;
	}

	@Override
	public PlottableData getSelectedPlottableData() {
		return plottableDataTableModel.getSelectedPlottableData(dataSetTable.getSelectedRow());
	}

	@Override
	public Label getSelectedLabel() {
		return labelTableModel.getSelectedLabel(labelTable.getSelectedRow());
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource().equals(tp)) {
			int i = tp.getSelectedIndex();
			// Create the buttons.
			boolean editingEnabled = i <= 1;
			bottomDisplay.getbEdit().setEnabled(editingEnabled);
			menuDisplay.getEdit_menu_item().setEnabled(editingEnabled);

			boolean deleteEnabled = i <= 1;
			bottomDisplay.getbDelete().setEnabled(deleteEnabled);
			menuDisplay.getDelete_menu_item().setEnabled(deleteEnabled);

			boolean addEnabled = i <= 1;
			bottomDisplay.getbAdd().setEnabled(addEnabled);

			bottomDisplay.getbMoveUp().setEnabled(i == 0);
			menuDisplay.getMoveup_menu_item().setEnabled(i == 0);

			bottomDisplay.getbMoveDown().setEnabled(i == 0);
			menuDisplay.getMovedown_menu_item().setEnabled(i == 0);
		}
	}

	@Override
	public void addPlottableData(PlottableData plottableData) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<PlottableData> getSelectedPlottableDatas() {
		return plottableDataTableModel.getSelectedPlottableData(dataSetTable.getSelectedRows());
	}

	@Override
	public List<Label> getSelectedLabels() {
		return labelTableModel.getSelectedLabels(labelTable.getSelectedRows());
	}

	@Override
	public JTextArea getPrePlotString() {
		return prePlotString;
	}
}
