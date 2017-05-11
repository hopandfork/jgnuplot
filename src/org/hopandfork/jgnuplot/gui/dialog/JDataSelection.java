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

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicTreeUI.SelectionModelPropertyChangeHandler;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.hopandfork.jgnuplot.gui.JGPPanel;
import org.hopandfork.jgnuplot.gui.PreviewTableModel;

public class JDataSelection extends JTable implements ActionListener, FocusListener {

	private static final long serialVersionUID = 853945391719595729L;

	private String fileName;

	public PreviewTableModel pvTableModel;

	public JTextField tfDataString;

	private static String dataString;

	// stores curser position in datastring field.
	private int caretPos;

	// headers for the table
	private static String[] columns = new String[] { "Id", "Name", "Hourly Rate", "Part Time" };

	// actual data for the table in a 2d array
	private static Object[][] data = new Object[][] { { 1, "John", 40.0, false }, { 2, "Rambo", 70.0, false },
			{ 3, "Zorro", 60.0, true }, };

	public JDataSelection() throws HeadlessException {
		super(data, columns);
		init();
	}

	private void init() {

		this.setPreferredScrollableViewportSize(new Dimension(500, 200));
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.setColumnSelectionAllowed(true);
		this.setRowSelectionAllowed(false);
		this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		this.addFocusListener(this);

		// react to column selection
		ListSelectionModel selectionModel = this.getColumnModel().getSelectionModel();
		selectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {

				if (e.getValueIsAdjusting()) {
					System.out.println("selected column is " + getSelectedColumn() + 1);
					//TODO Have to manage selected columns 
				}
			}
		});

		packColumns(this);
	}


	/**
	 * Inserts a string s into the datastring field at the last cursor position
	 * 
	 * @param isacViewacView
	 */
	protected void insertDataString(String is) {
		String s = tfDataString.getText();
		String s0 = s.substring(0, this.caretPos);
		String s1 = s.substring(this.caretPos, s.length());
		tfDataString.setText(s0 + is + s1);

		tfDataString.setSelectionStart(this.caretPos);
		tfDataString.setSelectionEnd(this.caretPos + is.length());
		tfDataString.requestFocus();
	}

	/**
	 * Inserts a string s into the datastrin field at the last cursor position
	 * and increases the last cursor position by i.
	 * 
	 * @param is
	 */
	protected void insertDataString(String is, int i) {
		insertDataString(is);
		this.caretPos += i;
		tfDataString.setCaretPosition(this.caretPos);
	}

	private JPanel createButtonPanel() {
		// Create the panel.
		JGPPanel jp = new JGPPanel();
		jp.setBackground(new Color(0xf0f0f0));
		// Set the default panel layout.
		GridBagLayout gbl = new GridBagLayout();

		tfDataString = new JTextField("", 20);
		tfDataString.addFocusListener(this);
		tfDataString.setToolTipText(
				"This sting will be passed as 'using' part \n" + "of the plot command to gnuplot. Start gnuplot \n"
						+ "from the command line and enter 'help using' \n" + "for documentation.");
		// tfDataString.getCaretPosition()
		jp.setLayout(gbl);

		// Create the panel border.
		TitledBorder border = new TitledBorder(new EtchedBorder(), "");
		border.setTitleColor(Color.blue);
		jp.setBorder(border);

		// Create the buttons.
		JButton bOk = new JButton("Ok");
		bOk.setPreferredSize(new Dimension(80, 20));
		bOk.setActionCommand("ok");
		bOk.addActionListener(this);

		// Create the buttons.
		JButton bCancel = new JButton("Cancel");
		bCancel.setPreferredSize(new Dimension(80, 20));
		bCancel.setActionCommand("cancel");
		bCancel.addActionListener(this);

		int opButtonWidth = 60;
		// Create the buttons.
		JButton bParan = new JButton("(_)");
		bParan.setPreferredSize(new Dimension(opButtonWidth, 20));
		bParan.setActionCommand("(_)");
		bParan.addActionListener(this);

		// Create the buttons.
		JButton bFrac = new JButton("(_)/( )");
		bFrac.setPreferredSize(new Dimension(opButtonWidth, 20));
		bFrac.setActionCommand("(_)/( )");
		bFrac.addActionListener(this);

		// Create the buttons.
		JButton bExp = new JButton("(_)^( )");
		bExp.setPreferredSize(new Dimension(opButtonWidth, 20));
		bExp.setActionCommand("(_)^( )");
		bExp.addActionListener(this);

		// Create the buttons.
		JButton bSquare = new JButton("(_)^2");
		bSquare.setPreferredSize(new Dimension(opButtonWidth, 20));
		bSquare.setActionCommand("(_)^2");
		bSquare.addActionListener(this);

		// Create the buttons.
		JButton bSqrt = new JButton("sqrt");
		bSqrt.setPreferredSize(new Dimension(opButtonWidth, 20));
		bSqrt.setActionCommand("sqrt");
		bSqrt.addActionListener(this);

		// Create the buttons.
		JButton bPlus = new JButton("+");
		bPlus.setPreferredSize(new Dimension(opButtonWidth, 20));
		bPlus.setActionCommand("+");
		bPlus.addActionListener(this);

		// Create the buttons.
		JButton bMinus = new JButton("-");
		bMinus.setPreferredSize(new Dimension(opButtonWidth, 20));
		bMinus.setActionCommand("-");
		bMinus.addActionListener(this);

		// Create the buttons.
		JButton bMul = new JButton("x");
		bMul.setPreferredSize(new Dimension(opButtonWidth, 20));
		bMul.setActionCommand("x");
		bMul.addActionListener(this);

		// Create the buttons.
		JButton bSep = new JButton(":");
		bSep.setPreferredSize(new Dimension(opButtonWidth, 20));
		bSep.setActionCommand(":");
		bSep.addActionListener(this);

		int row = 0;
		jp.add(bPlus, 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(bMinus, 1, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(bMul, 2, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(bParan, 3, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(bSquare, 4, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(bSqrt, 5, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(bExp, 6, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(bFrac, 7, row, 1, 1, GridBagConstraints.HORIZONTAL);
		row += 1;
		jp.add(new JLabel("Source:"), 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(tfDataString, 1, row, 7, 1, GridBagConstraints.HORIZONTAL);
		row += 1;
		jp.add(bOk, 6, row, 1, 1, GridBagConstraints.HORIZONTAL);
		jp.add(bCancel, 7, row, 1, 1, GridBagConstraints.HORIZONTAL);

		jp.setPreferredSize(new Dimension(200, 90));
		jp.setMaximumSize(new Dimension(200, 90));

		return jp;
	}

	public void packColumns(JTable table) {
		for (int c = 0; c < table.getColumnCount(); c++) {
			packColumn(table, c, 2);
		}
	}

	// Sets the preferred width of the visible column specified by vColIndex.
	// The column
	// will be just wide enough to show the column head and the widest cell in
	// the column.
	// margin pixels are added to the left and right
	// (resulting in an additional width of 2*margin pixels).
	public void packColumn(JTable table, int vColIndex, int margin) {
		TableModel model = table.getModel();
		DefaultTableColumnModel colModel = (DefaultTableColumnModel) table.getColumnModel();
		TableColumn col = colModel.getColumn(vColIndex);
		int width = 0;

		// Get width of column header
		TableCellRenderer renderer = col.getHeaderRenderer();
		if (renderer == null) {
			renderer = table.getTableHeader().getDefaultRenderer();
		}
		Component comp = renderer.getTableCellRendererComponent(table, col.getHeaderValue(), false, false, 0, 0);
		width = comp.getPreferredSize().width;

		// Get maximum width of column data
		for (int r = 0; r < table.getRowCount(); r++) {
			renderer = table.getCellRenderer(r, vColIndex);
			comp = renderer.getTableCellRendererComponent(table, table.getValueAt(r, vColIndex), false, false, r,
					vColIndex);
			width = Math.max(width, comp.getPreferredSize().width);
		}

		// Add margin
		width += 2 * margin;

		// Set the width
		col.setPreferredWidth(width);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("ok")) {
			dataString = this.tfDataString.getText();
			this.setVisible(false);
		}
		if (e.getActionCommand().equals("cancel")) {
			dataString = null;
			this.setVisible(false);
		} else if (e.getActionCommand().equals("+"))
			insertDataString("+", 1);
		else if (e.getActionCommand().equals("-"))
			insertDataString("-", 1);
		else if (e.getActionCommand().equals("x"))
			insertDataString("*", 1);
		else if (e.getActionCommand().equals("(_)"))
			insertDataString("()", 1);
		else if (e.getActionCommand().equals("(_)/( )"))
			insertDataString("()/()", 1);
		else if (e.getActionCommand().equals("(_)^( )"))
			insertDataString("()**()", 1);
		else if (e.getActionCommand().equals("(_)^2"))
			insertDataString("()**2", 1);
		else if (e.getActionCommand().equals("sqrt"))
			insertDataString("()**0.5", 1);
		else if (e.getActionCommand().equals(":"))
			insertDataString(":", 1);

	}

	public void focusLost(FocusEvent e) {
		if (e.getSource() == tfDataString) {
			System.out.println("tfDataString lost focus, caret pos: " + tfDataString.getCaretPosition());

			this.caretPos = tfDataString.getCaretPosition();
		} else if (e.getSource() == this)
			this.clearSelection();
	}

	public static String getDataString() {
		return dataString;
	}

	public static void setDataString(String dataString) {
		JDataSelection.dataString = dataString;
	}

	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub

	}

}
