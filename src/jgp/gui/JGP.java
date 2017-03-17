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

package jgp.gui;



import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.xml.parsers.ParserConfigurationException;

import jgp.JGPDataSet;
import jgp.JGPGnuplotVariable;
import jgp.JGPLabel;
import jgp.JGPPlotable;
import jgp.JGPPrintWriter;
import jgp.JGPgnuplot;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import org.w3c.dom.Document;

class HistoryElement{
	String description;
	Document state; 
	HistoryElement(String description, Document state){
		this.description = description;
		this.state = state;
	}
}

class UpdateChecker implements Runnable{
	JGP owner;
	
	
	public boolean checkForUpdate;
	
	UpdateChecker(JGP owner){
		this.owner = owner;
	}
	
	public void run(){
		checkForUpdate = true;
			
			while (checkForUpdate){
				//check wether one of the files has changed
				for (int i = 0; i < owner.dsTableModel.data.size(); i++){
					if (owner.dsTableModel.data.get(i).getClass().equals(JGPDataSet.class)
							&& ((JGPPlotable) owner.dsTableModel.data.get(i)).getDoPlot() ){
						JGPDataSet ds = (JGPDataSet) owner.dsTableModel.data.get(i);
						//if dataset source file has chenged, replot
						File f = new File(ds.getFileName());
						if ( f.lastModified() > ds.getLastChanged() ){
							try {
								owner.acPlot();
								ds.setLastChanged(f.lastModified());
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
	}
}

public class JGP extends JGPFrame implements ActionListener, ChangeListener, JGPPrintWriter, DocumentListener, FocusListener{
	
	
	private Stack<HistoryElement> history;
	private Stack<HistoryElement> redo;
	
	public static final boolean debug = true;
	
	private UpdateChecker updateChecker;

	private JGPConsoleDialog consoleDialog;
	//private JGPAddDialog addDialog;
	
	private JGPPlotDialog plotDialog;
	
	private JCheckBox cbUpdateCheck;
	
	
	//public JTable dataSetTable;
	
	public JGPDSTableModel dsTableModel;
	public JTable dataSetTable;
	
	public JGPLabelTableModel labelTableModel;
	public JTable labelTable;
	
	public JGPVariableTableModel variableTableModel;
	public JTable variableTable;
	
	public JTextArea taShell;
	public JTextArea prePlotString;
	
	public JTextField tfTitle;
	public JTextField tfMaxX;
	//tfUpDown.setFont(new Font("Courier", Font.PLAIN, 32));
	
	public JTextField tfMaxY;
	//tfLeftRight.setFont(new Font("Courier", Font.PLAIN, 32));
	public JTextField tfMinX;
	public JTextField tfMinY;
	
	public JRadioButton rb2D;
	public JRadioButton rb3D;
	
	public JCheckBox cbLogScaleX;
	public JCheckBox cbLogScaleY;
	public JTextField tfXLabel;
	public JTextField tfYLabel;
	public JTextField tfZLabel;
	
	public JTextField tfMaxZ;
	public JTextField tfMinZ;
	public JCheckBox cbLogScaleZ;
	public JTabbedPane tp;
	
	public JButton bEdit;
	public JButton bDelete;
	public JButton bAdd;
	public JButton bClone;
	public JButton bClear;
	public JButton bMoveUp;
	public JButton bMoveDown;
	
	public  JMenuItem add_menu_item;
	public JMenuItem delete_menu_item;
	public JMenuItem clear_menu_item;
	public JMenuItem edit_menu_item;
	public JMenuItem clone_menu_item ;
	public JMenuItem moveup_menu_item;
	public JMenuItem movedown_menu_item;
	public JMenuItem undo_menu_item;
	public JMenuItem redo_menu_item;
	
	public JTextField statusBar;
	
	public String  projectFileName;

	public String psFileName;

	public JMenu file_menu; 
	
	public static final int nRecentProjects = 8;
	
	public static final int startRecentProjects = 7;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String SETTINGS_FILE = ".JGP";
	
	private static final String STANDARD_PROJECT_FILE = ".JGP.project";
	

	//private ArrayList<JGPRecentProjectMenuItem> recentProjects;

	public JGP() {
		//super((Frame) null, "JGNUplot");
		this.setTitle("JGNUplot");

		this.setLocationByPlatform(true);

		plotDialog = new JGPPlotDialog(this);
		
		//file = new File(".");
		
		// Set the dialog box size.
		setSize(550, 750);
		this.setMinimumSize(new Dimension(550,750));

		// Create the menu bar and add it to the dialog box.
		this.setJMenuBar(create_menu_bar());

		Container content_pane = this.getContentPane();

		// Set the default panel layout.
		GridBagLayout gbl = new GridBagLayout();
		content_pane.setLayout(gbl);
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;

		content_pane.add(createCenterPanel(), gbc);



		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exit();
			}
		}); // ignore event itself
		
		
		//load settings, for example recently used projects
		loadSettings();


		loadStandardProject();
		
		history = new Stack<HistoryElement>();
		redo = new Stack<HistoryElement>();

	}

	
	public static String getVersion(){
		return "0.1.2";
	}
	
	/**
	 * *******************************************************************************
	 * This method creates the default menu bar for the Voodoo dialog box. The
	 * menu bar contains a single "Close" button.
	 * 
	 * @return Returns a JMenuBar that contains a "Close" menu button.
	 * 
	 * @version 1.00
	 * @author Scott Streit
	 *         *******************************************************************************
	 */
	protected JMenuBar create_menu_bar() {
		JMenuBar menu_bar = new JMenuBar();

		// Set the panel layout.
		menu_bar.setLayout(new FlowLayout(FlowLayout.LEFT));

		// Set the menubar's border.
		menu_bar.setBorder(new BevelBorder(BevelBorder.RAISED));
		menu_bar.setBorderPainted(true);

		// Add the file menu the menu bar.
		file_menu = new JMenu("File");
		file_menu.setBorderPainted(false);
		menu_bar.add(file_menu);

		JMenuItem new_menu_item = new JMenuItem("New");
		new_menu_item.addActionListener(this);
		new_menu_item.setActionCommand("new");
		file_menu.add(new_menu_item);

		JMenuItem save_menu_item = new JMenuItem("Save");
		save_menu_item.addActionListener(this);
		save_menu_item.setActionCommand("Save");
		file_menu.add(save_menu_item);

		JMenuItem save_to_menu_item = new JMenuItem("Save project to...");
		save_to_menu_item.addActionListener(this);
		save_to_menu_item.setActionCommand("Save project to...");
		file_menu.add(save_to_menu_item);

		JMenuItem save_std_menu_item = new JMenuItem(
				"Save Settings as standard");
		save_std_menu_item.addActionListener(this);
		save_std_menu_item.setActionCommand("Save project as standard");
		file_menu.add(save_std_menu_item);

		file_menu.addSeparator();
		


		JMenuItem load_menu_item = new JMenuItem("Load project...");
		load_menu_item.addActionListener(this);
		load_menu_item.setActionCommand("Load project...");
		file_menu.add(load_menu_item);

		file_menu.addSeparator();

		//recentProjects = new ArrayList<JGPRecentProjectMenuItem>();
		for (int i = 0; i < nRecentProjects; i++){
			JGPRecentProjectMenuItem menu_item = new JGPRecentProjectMenuItem("-");
			menu_item.addActionListener(this);
			file_menu.add(menu_item);
			//recentProjects.add((JGPRecentProjectMenuItem) file_menu.add(menu_item));
		}
		
		file_menu.addSeparator();
		
		JMenuItem exit_menu_item = new JMenuItem("Exit");
		exit_menu_item.addActionListener(this);
		exit_menu_item.setActionCommand("Exit");
		file_menu.add(exit_menu_item);

		// Add the edit menu the menu bar.
		JMenu edit_menu = new JMenu("Edit");
		edit_menu.setBorderPainted(false);
		menu_bar.add(edit_menu);

		undo_menu_item = new JMenuItem("Undo");
		undo_menu_item.addActionListener(this);
		undo_menu_item.setActionCommand("undo");
		edit_menu.add(undo_menu_item);
		//Not supported yet
		undo_menu_item.setEnabled(false);

		redo_menu_item = new JMenuItem("Redo");
		redo_menu_item.addActionListener(this);
		redo_menu_item.setActionCommand("Redo");
		edit_menu.add(redo_menu_item);
		//Not supported yet
		redo_menu_item.setEnabled(false);
		
		edit_menu.addSeparator();

		add_menu_item = new JMenuItem("Add");
		add_menu_item.addActionListener(this);
		add_menu_item.setActionCommand("add");
		edit_menu.add(add_menu_item);

		delete_menu_item = new JMenuItem("Delete");
		delete_menu_item.addActionListener(this);
		delete_menu_item.setActionCommand("delete");
		edit_menu.add(delete_menu_item);

		clear_menu_item = new JMenuItem("Clear");
		clear_menu_item.addActionListener(this);
		clear_menu_item.setActionCommand("clear");
		edit_menu.add(clear_menu_item);

		edit_menu.addSeparator();

		edit_menu_item = new JMenuItem("Edit dataset");
		edit_menu_item.addActionListener(this);
		edit_menu_item.setActionCommand("edit");
		edit_menu.add(edit_menu_item);

		clone_menu_item = new JMenuItem("Clone dataset");
		clone_menu_item.addActionListener(this);
		clone_menu_item.setActionCommand("clone");
		edit_menu.add(clone_menu_item);

		moveup_menu_item = new JMenuItem("Move dataset(s) up");
		moveup_menu_item.addActionListener(this);
		moveup_menu_item.setActionCommand("moveup");
		edit_menu.add(moveup_menu_item);

		movedown_menu_item = new JMenuItem("Move dataset(s) down");
		movedown_menu_item.addActionListener(this);
		movedown_menu_item.setActionCommand("movedown");
		edit_menu.add(movedown_menu_item);

		edit_menu.addSeparator();

		JMenuItem settings_menu_item = new JMenuItem("Settings...");
		settings_menu_item.addActionListener(this);
		settings_menu_item.setActionCommand("settings");
		edit_menu.add(settings_menu_item);
		//Not supported yet
		settings_menu_item.setEnabled(false);

		// Add the view menu the menu bar.
		JMenu view_menu = new JMenu("View");
		view_menu.setBorderPainted(false);
		menu_bar.add(view_menu);

		JMenuItem console_menu_item = new JMenuItem("Show console");
		console_menu_item.addActionListener(this);
		console_menu_item.setActionCommand("showconsole");
		view_menu.add(console_menu_item);


		// Add the file menu the menu bar.
		JMenu help_menu = new JMenu("Help");
		help_menu.setBorderPainted(false);
		menu_bar.add(help_menu);

		JMenuItem about_menu_item = new JMenuItem("About");
		about_menu_item.addActionListener(this);
		about_menu_item.setActionCommand("about");
		help_menu.add(about_menu_item);


		return menu_bar;
	}

	/**
	 * ****************************************************************************
	 * Creates the "tabbed" panels portion of the window.
	 * 
	 * @return The created "tabbed pane" is returned.
	 * 
	 * @see JTabbedPane
	 * @version 1.00
	 * @author Xiangyang (Helena) Xian
	 *         ****************************************************************************
	 */
	private JTabbedPane create_tabbed_pane() {
		// Create a new tabbed pane and set the tabs to be on top.
		tp = new JTabbedPane(JTabbedPane.TOP);


		tp.addTab("Datasets", createDataSetPanel());

		tp.addTab("Labels", createLabelSetPanel());

		tp.addTab("Variables", createVariablePanel());

		tp.addTab("Add. plot commands", createPrePlotStringPanel());

		// Set the tab event listener.
		tp.addChangeListener(this);

		return tp;
	}
	
	private JPanel createButtonPanel(){
		JGPPanel jp = new JGPPanel();
		
		// Set the default panel layout.
		GridBagLayout gbl = new GridBagLayout();
		jp.setLayout(gbl);

		
		// Create the buttons.
		JButton bPlot = new JButton("plot");
		bPlot.setPreferredSize(new Dimension(60, 20));
		bPlot.setActionCommand("plot");
		bPlot.addActionListener(this);

		// Create the buttons.
		JButton bPlotPs = new JButton("save to file");
		bPlotPs.setPreferredSize(new Dimension(160, 20));
		bPlotPs.setActionCommand("plotps");
		bPlotPs.addActionListener(this);

		// Create the buttons.
		JButton bPrint = new JButton("print");
		bPrint.setPreferredSize(new Dimension(80, 20));
		bPrint.setActionCommand("print");
		bPrint.addActionListener(this);

		JButton bPlotString = new JButton("preview plotstring");
		bPlotString.setPreferredSize(new Dimension(160, 20));
		bPlotString.setActionCommand("genplotcmds");
		bPlotString.addActionListener(this);
		
		int row = 0;
		jp.add(bPlot, 0, row, 1, 1, GridBagConstraints.NONE);
		jp.add(bPlotPs, 1, row, 1, 1, GridBagConstraints.NONE);
		jp.add(bPrint, 2, row, 1, 1, GridBagConstraints.NONE);
		jp.add(bPlotString, 3, row, 1, 1, GridBagConstraints.NONE);
		
		return jp;
		
	}

	private JPanel createCenterPanel(){
		// Create the panel.
		JGPPanel jp = new JGPPanel();
		// jp.setPreferredSize(new Dimension(400, 400));
		jp.setBackground(new Color(0xf0f0f0));
		// Set the default panel layout.
		GridBagLayout gbl = new GridBagLayout();

		jp.setLayout(gbl);

		// Create the panel border.
		TitledBorder border = new TitledBorder(new EtchedBorder(), "");
		border.setTitleColor(Color.blue);
		jp.setBorder(border);

		


        // Create the buttons.
		bEdit = new JButton("edit");
		bEdit.setPreferredSize(new Dimension(80, 20));
		bEdit.setActionCommand("edit");
		bEdit.addActionListener(this);

		bDelete = new JButton("delete");
		bDelete.setPreferredSize(new Dimension(80, 20));
		bDelete.setActionCommand("delete");
		bDelete.addActionListener(this);

		bAdd = new JButton("add");
		bAdd.setPreferredSize(new Dimension(80, 20));
		bAdd.setActionCommand("add");
		bAdd.addActionListener(this);
		
		bClone = new JButton("clone");
		bClone.setPreferredSize(new Dimension(80, 20));
		bClone.setActionCommand("clone");
		bClone.addActionListener(this);
		
		bClear = new JButton("clear");
		bClear.setPreferredSize(new Dimension(80, 20));
		bClear.setActionCommand("clear");
		bClear.addActionListener(this);
		
		bMoveUp = new JButton("up");
		bMoveUp.setPreferredSize(new Dimension(80, 20));
		bMoveUp.setActionCommand("moveup");
		bMoveUp.addActionListener(this);
		
		bMoveDown = new JButton("down");
		bMoveDown.setPreferredSize(new Dimension(80, 20));
		bMoveDown.setActionCommand("movedown");
		bMoveDown.addActionListener(this);
		
		cbUpdateCheck = new JCheckBox("automatic replot on file changes");
		cbUpdateCheck.setActionCommand("updatecheck");
		cbUpdateCheck.addActionListener(this);

	    rb2D = new JRadioButton("2D plot", true);
	    rb3D = new JRadioButton("3D plot");
	    rb2D.addChangeListener(this);
	    rb3D.addChangeListener(this);
	    rb3D.setActionCommand("2dplot");
	    rb3D.setActionCommand("3dplot");
	    
	    ButtonGroup group = new ButtonGroup();
	    group.add(rb2D);
	    group.add(rb3D);
	    
		 tfTitle= new JTextField("",16);
		 tfTitle.addFocusListener(this);
		 tfTitle.getDocument().addDocumentListener(this);
		 
		 tfMaxX= new JTextField("",8);
		 tfMaxX.addFocusListener(this);
		 tfMaxX.getDocument().addDocumentListener(this);
		 
		 tfMaxY = new JTextField("",8);
		 tfMaxY.addFocusListener(this);
		 tfMaxY.getDocument().addDocumentListener(this);
		 
		 tfXLabel = new JTextField("",10);
		 tfXLabel.addFocusListener(this);
		 tfXLabel.getDocument().addDocumentListener(this);
		 
		 tfMinX= new JTextField("",8);
		 tfMinX.addFocusListener(this);
		 tfMinX.getDocument().addDocumentListener(this);
		 
		 tfMinY= new JTextField("",8);
		 tfMinY.addFocusListener(this);
		 tfMinY.getDocument().addDocumentListener(this);
		 
		 tfYLabel = new JTextField("",10);
		 tfYLabel.addFocusListener(this);
		 tfYLabel.getDocument().addDocumentListener(this);
		 
		 tfMaxZ= new JTextField("",8);
		 tfMaxZ.addFocusListener(this);
		 tfMaxZ.getDocument().addDocumentListener(this);
		 
		 tfMinZ= new JTextField("",8);
		 tfMinZ.addFocusListener(this);
		 tfMinZ.getDocument().addDocumentListener(this);
		 
		 tfZLabel = new JTextField("",10);
		 tfZLabel.addFocusListener(this);
		 tfZLabel.getDocument().addDocumentListener(this);
		 
		 cbLogScaleX = new JCheckBox();
		 cbLogScaleY = new JCheckBox();
		 cbLogScaleZ = new JCheckBox();
		 
		 //disable 3d until user selets 3d plot
		tfMaxZ.setEnabled( false );
		tfMinZ.setEnabled( false );
		tfZLabel.setEnabled( false );
		cbLogScaleZ.setEnabled( false );
		
		statusBar = new JTextField();
		statusBar.setBackground(this.getBackground());
		statusBar.setEditable(false);
		statusBar.setFocusable(false);
		int row = 0;
		//jp.add(new JLabel("Datasets"), 0, row, 1, 1, GridBagConstraints.HORIZONTAL);
		GridBagConstraints gbc2 = new GridBagConstraints();

		gbc2.gridx = 0;
		gbc2.gridy = 0;
		gbc2.gridwidth = 5;
		gbc2.weightx = 1.0;
		gbc2.weighty = 1.0;
		gbc2.fill = GridBagConstraints.BOTH;

		//this works, the other version does not...don't know why
		jp.add(create_tabbed_pane(), gbc2);
		//jp.add(create_tabbed_pane(), 0, row, 4, 3, GridBagConstraints.BOTH);
		row += 3;
		jp.add(bMoveUp, 0, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		jp.add(bMoveDown, 1, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		jp.add(cbUpdateCheck, 2, row, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		row += 1;
		jp.add(bEdit, 0, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		jp.add(bAdd, 1, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		jp.add(bClone, 2, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		jp.add(bDelete, 3, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		jp.add(bClear, 4, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		row += 1;
		//jp.add(createShellPanel(), 0, row, 5, 3, GridBagConstraints.HORIZONTAL);
		row += 3;
		jp.add(new JLabel("Title"), 0, row, 1, 1, GridBagConstraints.WEST);
		jp.add(tfTitle, 1, row, 4, 1, GridBagConstraints.BOTH);
		row += 1;
		jp.add(new JLabel("Plottype:"), 0, row, 1, 1, GridBagConstraints.WEST);
		jp.add(rb2D, 1, row, 1, 1, GridBagConstraints.NONE);
		jp.add(rb3D, 2, row, 1, 1, GridBagConstraints.NONE);
		row += 1;
		jp.add(new JLabel("min X"), 0, row, 1, 1, GridBagConstraints.WEST);
		jp.add(tfMinX, 1, row, 1, 1, GridBagConstraints.NONE);
		jp.add(new JLabel("x axis label"), 2, row, 1, 1, GridBagConstraints.WEST);
		jp.add(tfXLabel, 3, row, 2, 1, GridBagConstraints.BOTH);
		row += 1;
		jp.add(new JLabel("max X"), 0, row, 1, 1, GridBagConstraints.WEST);
		jp.add(tfMaxX, 1, row, 1, 1, GridBagConstraints.NONE);
		jp.add(new JLabel("logscale x axis"), 2, row, 1, 1, GridBagConstraints.WEST);
		jp.add(cbLogScaleX, 3, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		row += 1;
		jp.add(new JLabel("min Y"), 0, row, 1, 1, GridBagConstraints.WEST);
		jp.add(tfMinY, 1, row, 1, 1, GridBagConstraints.NONE);
		jp.add(new JLabel("y axis label"), 2, row, 1, 1, GridBagConstraints.WEST);
		jp.add(tfYLabel, 3, row, 2, 1, GridBagConstraints.BOTH);
		row += 1;
		jp.add(new JLabel("max Y"), 0, row, 1, 1, GridBagConstraints.WEST);
		jp.add(tfMaxY, 1, row, 1, 1, GridBagConstraints.NONE);
		jp.add(new JLabel("logscale y axis"), 2, row, 1, 1, GridBagConstraints.WEST);
		jp.add(cbLogScaleY, 3, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		row += 1;
		jp.add(new JLabel("min Z"), 0, row, 1, 1, GridBagConstraints.WEST);
		jp.add(tfMinZ, 1, row, 1, 1, GridBagConstraints.NONE);
		jp.add(new JLabel("z axis label"), 2, row, 1, 1, GridBagConstraints.WEST);
		jp.add(tfZLabel, 3, row, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		row += 1;
		jp.add(new JLabel("max Z"), 0, row, 1, 1, GridBagConstraints.WEST);
		jp.add(tfMaxZ, 1, row, 1, 1, GridBagConstraints.NONE);
		jp.add(new JLabel("logscale z axis"), 2, row, 1, 1, GridBagConstraints.WEST);
		jp.add(cbLogScaleZ, 3, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		row += 1;
		jp.add(createButtonPanel(), 0, row, 5, 1, GridBagConstraints.NONE, GridBagConstraints.WEST);
		row += 1;
		jp.add(statusBar, 0, row, 5, 1, GridBagConstraints.BOTH);

		
		return jp;		
	}

	private JPanel createShellPanel(){
		// Create the panel.
		JGPPanel jp = new JGPPanel();
		// Set the default panel layout.
		GridBagLayout gbl = new GridBagLayout();
		jp.setLayout(gbl);
		
        taShell = new JTextArea(100,20);
        taShell.setWrapStyleWord(true);
        taShell.setLineWrap(true);

        JScrollPane scrollPane = new JScrollPane(taShell);
        scrollPane.setPreferredSize(new Dimension(500, 200));

		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.gridx = 0;
		gbc2.gridy = 0;
		gbc2.gridwidth = 4;
		gbc2.weightx = 1.0;
		gbc2.weighty = 1.0;
		gbc2.fill = GridBagConstraints.BOTH;

		//this works, the other version does not...don't know why
		jp.add(scrollPane, gbc2);

		return jp;
		
	}
	
	private JPanel createLabelSetPanel(){
		

		// Create the panel.
		JGPPanel jp = new JGPPanel();
		// Set the default panel layout.
		GridBagLayout gbl = new GridBagLayout();
		jp.setLayout(gbl);

		labelTableModel = new JGPLabelTableModel();
		labelTable = new JTable(labelTableModel);
		labelTable.setPreferredScrollableViewportSize(new Dimension(500, 200));
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(labelTable);
		// jp.setPreferredSize(new Dimension(400, 400));

        TableColumn styleColumn = labelTable.getColumnModel().getColumn(3);
        styleColumn.setCellEditor(new DefaultCellEditor(new JGPRelativePosComboBox() ));



		
        //TableColumn doPlotColumn = dataSetTable.getColumnModel().getColumn(4);
        //doPlotColumn.setCellEditor(new DefaultCellEditor(new JCheckBox()));
        
        
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		jp.add(scrollPane, gbc);
		
		return jp;	
	}
	
	private JPanel createPrePlotStringPanel(){
		// Create the panel.
		JGPPanel jp = new JGPPanel();
		// Set the default panel layout.
		GridBagLayout gbl = new GridBagLayout();
		jp.setLayout(gbl);
		
        prePlotString = new JTextArea(100,20);
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
	
	private JPanel createDataSetPanel(){
		
		// Create the panel.
		JGPPanel jp = new JGPPanel();
		// Set the default panel layout.
		GridBagLayout gbl = new GridBagLayout();
		jp.setLayout(gbl);
		
		dsTableModel = new JGPDSTableModel();
		dataSetTable = new JTable(dsTableModel);
        dataSetTable.setPreferredScrollableViewportSize(new Dimension(500, 200));
        dataSetTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        
        //dsTableModel.addRow(new DataSet("foo", "1:2", "foo", Style.dots));
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(dataSetTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        
		// jp.setPreferredSize(new Dimension(400, 400));
        TableColumn styleColumn = dataSetTable.getColumnModel().getColumn(4);
        styleColumn.setCellEditor(new DefaultCellEditor(new JGPStyleComboBox() ));

        
        //Set up renderer and editor for the Favorite Color column.
        dataSetTable.setDefaultRenderer(Color.class,
                                 new JGPColorRenderer(true));
        dataSetTable.setDefaultEditor(Color.class,
                               new JGPColorEditor());


		
        //TableColumn doPlotColumn = dataSetTable.getColumnModel().getColumn(4);
        //doPlotColumn.setCellEditor(new DefaultCellEditor(new JCheckBox()));
        
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
        jp.add(scrollPane, gbc);
		
		return jp;
	}
	

    public void packColumns(JTable table) {
        for (int c=1; c<table.getColumnCount(); c++) {
            packColumn(table, c, 2);
        }
    }
	   // Sets the preferred width of the visible column specified by vColIndex. The column
    // will be just wide enough to show the column head and the widest cell in the column.
    // margin pixels are added to the left and right
    // (resulting in an additional width of 2*margin pixels).
    public void packColumn(JTable table, int vColIndex, int margin) {
        TableModel model = table.getModel();
        DefaultTableColumnModel colModel = (DefaultTableColumnModel)table.getColumnModel();
        TableColumn col = colModel.getColumn(vColIndex);
        int width = 0;
    
        // Get width of column header
        TableCellRenderer renderer = col.getHeaderRenderer();
        if (renderer == null) {
            renderer = table.getTableHeader().getDefaultRenderer();
        }
        Component comp = renderer.getTableCellRendererComponent(
            table, col.getHeaderValue(), false, false, 0, 0);
        width = comp.getPreferredSize().width;
    
        // Get maximum width of column data
        for (int r=0; r<table.getRowCount(); r++) {
            renderer = table.getCellRenderer(r, vColIndex);
            comp = renderer.getTableCellRendererComponent(
                table, table.getValueAt(r, vColIndex), false, false, r, vColIndex);
            width = Math.max(width, comp.getPreferredSize().width);
        }
    
        // Add margin
        width += 2*margin;
    
        // Set the width
        col.setPreferredWidth(width);
    }
    
	private JPanel createVariablePanel(){

		// Create the panel.
		JGPPanel jp = new JGPPanel();
		// Set the default panel layout.
		GridBagLayout gbl = new GridBagLayout();
		jp.setLayout(gbl);

		variableTableModel = new JGPVariableTableModel();
		variableTable = new JTable(variableTableModel);
		variableTable.setPreferredScrollableViewportSize(new Dimension(500, 200));
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(variableTable);
		// jp.setPreferredSize(new Dimension(400, 400));

        TableColumn typeColumn = variableTable.getColumnModel().getColumn(0);
        typeColumn.setCellEditor(new DefaultCellEditor(new JGPVariableTypeComboBox() ));



		
        //TableColumn doPlotColumn = dataSetTable.getColumnModel().getColumn(4);
        //doPlotColumn.setCellEditor(new DefaultCellEditor(new JCheckBox()));
        
        
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		jp.add(scrollPane, gbc);
		
		return jp;	
	
	}

    public static void main(String[] args) throws MalformedURLException {
    	//invoke splash window
    	//URL in = JGNUplot.class.getResource("./images/splash.png");
        //SplashWindow.splash(in);
    	JGPSplashWindow.splash("./images/splash.png");
        JGPSplashWindow.invoke("jgp.gui.JGP", "start", args);
        JGPSplashWindow.disposeSplash();
        //invoke start directly to get rid of splash
        //start(args);
    }
    
    public static void start(String[] args) {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                	JGP m = new JGP();
                    m.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    m.pack();
                    m.setVisible(true);
                }
            });
        } catch (InterruptedException e) {
            // Ignore: If this exception occurs, we return too early, which
            // makes the splash window go away too early.
            // Nothing to worry about. Maybe we should write a log message.
        } catch (InvocationTargetException e) {
            // Error: Startup has failed badly. 
            // We can not continue running our application.
            InternalError error = new InternalError();
            error.initCause(e);
            throw error;
        }
    }

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("add")){
			saveHistoryState(e.getActionCommand());
			int i = tp.getSelectedIndex();
			
			switch (i) {
				case 0: 
					acAdd();
					break;
	            case 1:  {
	            	labelTableModel.addRow(new JGPLabel() );
	            }
				break;
	            case 2:  {
	            	variableTableModel.addRow(new JGPGnuplotVariable() );
	            }
	            break;
			}
		}
		else if (e.getActionCommand().equals("undo")){
			acUndo();
		}
		else if (e.getActionCommand().equals("genplotcmds")){
			acGenPlotCmds();
		}
		else if (e.getActionCommand().equals("edit")){
			saveHistoryState(e.getActionCommand());
			acEdit();
		}
		else if (e.getActionCommand().equals("delete")){
			saveHistoryState(e.getActionCommand());
			acDelete();
		}
		else if (e.getActionCommand().equals("moveup")){
			saveHistoryState(e.getActionCommand());
			acMoveUp();
		}
		else if (e.getActionCommand().equals("movedown")){
			saveHistoryState(e.getActionCommand());
			acMoveDown();
		}
		else if (e.getActionCommand().equals("showconsole")){
			acShowConsole();
		}
		
		else if (e.getActionCommand().equals("plot")){
			try {
				acPlot();
			} catch (IOException e1) {
				showConsole("Plot failed:\n" + e1.getMessage(), false, true);
			} catch (InterruptedException e1) {
				showConsole("Plot failed:\n" + e1.getMessage(), false, true);
			}
		}
		else if (e.getActionCommand().equals("plotps")){
			acPlotPS();
		}
		else if (e.getActionCommand().equals("print")){
			acPrint();
		}
		else if (e.getActionCommand().equals("new")){
			saveHistoryState(e.getActionCommand());
			acNew();
		}
		else if (e.getActionCommand().equals("about"))
			JGPAboutDialog.showAboutDialog();
		else if (e.getActionCommand().equals("clone")){
			saveHistoryState(e.getActionCommand());
			acClone();
			}
		else if (e.getActionCommand().equals("clear")){
			saveHistoryState(e.getActionCommand());
			acClear();
		}
		else if (e.getActionCommand().equals("Save project to..."))
			acSaveProjectTo();
		else if (e.getActionCommand().equals("Save"))
			acSaveProject();
		else if (e.getActionCommand().equals("Save project as standard"))
			acSaveStandardProject();
		else if (e.getActionCommand().contains("load_recent_project:"))
			loadProject(e.getActionCommand().replace("load_recent_project:","").trim());
		else if (e.getActionCommand().equals("Load project..."))
			acLoadProject();
		else if (e.getActionCommand().equals("updatecheck")) {
			if (cbUpdateCheck.isSelected())
				startCheckUpdates();
			else
				stopCheckUpdates();
		}

		
	}
	
	public void exit(){
		saveSettingst();
		System.exit(0);
	}
	

	public void cl2Dor3D(){
		tfMaxZ.setEnabled( !rb2D.isSelected() );
		tfMinZ.setEnabled( !rb2D.isSelected() );
		tfZLabel.setEnabled( !rb2D.isSelected() );
		cbLogScaleZ.setEnabled( !rb2D.isSelected() );

	}
	

	
	public void acAdd(){
		JGPPlotable p = JGPAddDialog.showAddDialog("Add dataset...");
		if (null != p) dsTableModel.addRow(p);
		System.out.println("added..");

	}
	
	public void acShowConsole(){
		showConsole("", true);

	}
	
	public void acPrint() {
		JGPgnuplot gp = getGNUplot();
		gp.setOut((JGPPrintWriter) this);
		try {
			
			gp.printThreaded("","");
		} catch (IOException e) {
			showConsole("Error printing: " + e.getMessage(), false, true);
		} catch (InterruptedException e) {
			showConsole("Error printing: " + e.getMessage(), false, true);
		}
	}
	
	
	public void acSaveProjectTo() {
		try {
			saveProjectTo();
		} catch (IOException e1) {
			showConsole("Error saving project: " + e1.getMessage(), false, true);
		}
	}
	
	public void acSaveProject() {
		try {
			saveProject();
		} catch (IOException e1) {
			showConsole("Error saving project: " + e1.getMessage(), false, true);
		}
	}
	
	public void acPlotPS(){
//		JFileChooser file_chooser;
//		
//		File file;
//		
//		if (psFileName != null)
//			file = new File(psFileName);
//		else
//			file = new File(".");
//
//		// Open a file chooser that points to the current dir.
//		file_chooser = new JFileChooser(file.getPath());
//
//		// Set the file chooser size.
//		file_chooser.setPreferredSize(new Dimension(500, 400));
//
//		// Show the Save dialog box (returns the option selected)
//		int selected = file_chooser.showSaveDialog(this);
//
//		// If the Open button is pressed.
//		if (selected == JFileChooser.APPROVE_OPTION) {
//			// Get the selected file.
//			file = file_chooser.getSelectedFile();
//			
//			psFileName = file.getPath();
//
//			clearShell();
//
//			println("calling GNUplot...");
//
//			GNUplot gp = getGNUplot();
//			try {
//				gp.plotPostScript(file.getPath());
//			} catch (IOException e) {
//				taShell.setText(e.getMessage());
//			} catch (InterruptedException e) {
//				taShell.setText(e.getMessage());
//			}
//
//			return;
//		}
//		// If the Cancel button is pressed.
//		else if (selected == JFileChooser.CANCEL_OPTION) {
//			return;
//		}
		plotDialog.setVisible(true);
	}

	public void acSaveStandardProject() {

		new JGPProjectManager(this).writeProjectFile(STANDARD_PROJECT_FILE);

	}

	public void acLoadProject() {
		try {
			loadProject();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error loading project: ",
					JOptionPane.ERROR_MESSAGE);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error loading project: ",
					JOptionPane.ERROR_MESSAGE);
		} catch (InstantiationException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error loading project: ",
					JOptionPane.ERROR_MESSAGE);
		} catch (IllegalAccessException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error loading project: ",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void loadStandardProject() {
			try {
				new JGPProjectManager(this).loadProjectFile(STANDARD_PROJECT_FILE);
			} catch (RuntimeException e) {
				showConsole("No standard project loaded:" + e.getMessage(), false);
			} catch (ClassNotFoundException e) {
				showConsole("No standard project loaded:" + e.getMessage(), false);
			} catch (ParserConfigurationException e) {
				showConsole("No standard project loaded:" + e.getMessage(), false);
			} catch (SAXException e) {
				showConsole("No standard project loaded:" + e.getMessage(), false);
			} catch (IOException e) {
				showConsole("No standard project loaded:" + e.getMessage(), false);
			} catch (InstantiationException e) {
				showConsole("No standard project loaded:" + e.getMessage(), false);
			} catch (IllegalAccessException e) {
				showConsole("No standard project loaded:" + e.getMessage(), false);
			} catch (JGPProjectManagerException e) {
				showConsole("No standard project loaded:" + e.getMessage(), false);
			}

	}
	

	public void acEdit(){
		int i = tp.getSelectedIndex();
		
		switch (i) {
			case 0:{ 
					int[] r = dataSetTable.getSelectedRows();
					if (r.length == 0){
						JOptionPane.showMessageDialog(this,"You need to select one row to edit." ,  "Editing datasets", 
								JOptionPane.INFORMATION_MESSAGE);

						return;
					} else if (r.length > 1) {
						JOptionPane.showMessageDialog(this, "You need to select only one row to edit." ,"Editing datasets",  
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					JGPPlotable p = JGPAddDialog.showAddDialog(dsTableModel.data.get(r[0]), "Edit dataset...");
					if (null != p) dsTableModel.data.set(r[0], p);
					dsTableModel.fireTableDataChanged();
					System.out.println("added..");
				}
			break;
            case 1: { 
				JOptionPane.showMessageDialog(this,  "Dialog editing of labels not supported yet! Edit them in the table." , "Editing labels",
						JOptionPane.INFORMATION_MESSAGE);
            }
        	break;
            case 2: { 
				JOptionPane.showMessageDialog(this,  "Dialog editing of variables not supported yet! Edit them in the table." , "Editing variables",
						JOptionPane.INFORMATION_MESSAGE);
            }
        	break;
		}		
	}
	
	public void acNew(){
			tfTitle.setText("");
			tfMaxX.setText("");
			tfMinX.setText("");
			tfMaxY.setText("");
			tfMinY.setText("");
			tfXLabel.setText("");
			tfYLabel.setText("");
			cbLogScaleX.setSelected(false);
			cbLogScaleX.setSelected(false);
			
			clearDataSetTable();
			clearLabelTable();
			clearVariableTable();
			
			setFileTitle("<New>");
			this.projectFileName = null;
	}
	
	/**
	 * Moves the currently selected datasets one position further up in the list.
	 */
	public void acMoveUp(){
		int i = tp.getSelectedIndex();
		switch (i) {
				case 0:{ 
				int[] r = dataSetTable.getSelectedRows();
	
				//check if userselected any datasets to move
				if (r.length == 0) {
					JOptionPane.showMessageDialog(this, "No dataset selected.", "Moving datasets",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
	
				//check if user tried to move the first dataset further up
				if (r[0] == 0) {
					JOptionPane.showMessageDialog(this, "Cannot move datasets further up.", "Moving datasets",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				//move all selected datasets one position further up
				for (int j = 0; j < r.length; j++){
					//get selected and prevoius datasets
					JGPPlotable pCur = dsTableModel.data.get(r[j]);
					JGPPlotable pPre = dsTableModel.data.get(r[j] - 1);
					
					//and swap them
					dsTableModel.data.set(r[j], pPre);
					dsTableModel.data.set(r[j] - 1, pCur);
				}
				//notify renderer that there were changes
				dsTableModel.fireTableDataChanged();
				//re-select rows
				for (int j = 0; j < r.length; j++){
					dataSetTable.addRowSelectionInterval(r[j]-1, r[j]-1);
				}
	        }
	        break;
	        case 1: { 
				JOptionPane.showMessageDialog(this,  "Moving of labels not supported yet!", "Moving labels",
						JOptionPane.INFORMATION_MESSAGE);

	        }
	    	break;
	        case 2: { 
				JOptionPane.showMessageDialog(this,  "Moving of variables not supported yet!", "Moving variables",
						JOptionPane.INFORMATION_MESSAGE);
	        }
	        break;
	
		}
	}

	/**
	 * Moves the currently selected datasets one position further down in the list.
	 */
	public void acMoveDown(){
		int i = tp.getSelectedIndex();
		switch (i) {
			case 0:{ 
				int[] r = dataSetTable.getSelectedRows();
	
				//check if userselected any datasets to move
				if (r.length == 0) {
					JOptionPane.showMessageDialog(this,  "No dataset selected.", "Moving datasets",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
	
				//check if user tried to move the last dataset further down
				if (r[r.length - 1] == (dsTableModel.data.size() -1) ) {
					JOptionPane.showMessageDialog(this,  "Cannot move datasets further down.", "Moving datasets",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				//move all selected datasets one position further up
				for (int j = 0; j < r.length; j++){
					//get selected and prevoius datasets
					JGPPlotable pCur = dsTableModel.data.get(r[j]);
					JGPPlotable pNext = dsTableModel.data.get(r[j] + 1);
					
					//and swap them
					dsTableModel.data.set(r[j], pNext);
					dsTableModel.data.set(r[j] + 1, pCur);
					
				}
				//notify renderer that there were changes
				dsTableModel.fireTableDataChanged();
				for (int j = 0; j < r.length; j++){
					dataSetTable.addRowSelectionInterval(r[j]+1, r[j]+1);
				}
	        }
	        break;
	        case 1: { 
				JOptionPane.showMessageDialog(this, "Moving of labels not supported yet!",  "Moving labels",
						JOptionPane.INFORMATION_MESSAGE);

	        }
	    	break;
	        case 2: { 
				JOptionPane.showMessageDialog(this,  "Moving of variables not supported yet!", "Moving variables",
						JOptionPane.INFORMATION_MESSAGE);
	        }
            break;

		}
	}
	
	
	/**
	 * Clones currently selected datasets.
	 */
	public void acClone(){
		int i = tp.getSelectedIndex();
		switch (i) {
			case 0:{ 
				int[] r = dataSetTable.getSelectedRows();
	
				//check if user selected any datasets to move
				if (r.length == 0) {
					JOptionPane.showMessageDialog(this,  "No dataset selected!", "Cloning datasets",
							JOptionPane.INFORMATION_MESSAGE);

					return;
				}
	
				//check if user selected more than one dataset 
				if (r.length > 1) {
					JOptionPane.showMessageDialog(this,  "Can only clone one dataset!", "Cloning datasets",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
	
				//get selected and prevoius datasets
				JGPPlotable pCur = dsTableModel.data.get(r[0]);
				dsTableModel.data.add(r[0] + 1, pCur.getClone() );
					
				//notify renderer that there were changes
				dsTableModel.fireTableDataChanged();
			}
			break;
            case 1: { 
				JOptionPane.showMessageDialog(this,  "Cloning of labels not supported yet!", "Cloning labels",
						JOptionPane.INFORMATION_MESSAGE);
            }
        	break;
            case 2: { 
				JOptionPane.showMessageDialog(this,  "Cloning of variables not supported yet!", "Cloning variables",
						JOptionPane.INFORMATION_MESSAGE);
            }
            break;
		}
	}

	public void acDelete(){
		int i = tp.getSelectedIndex();
		
		switch (i) {
			case 0:{ 
				int[] r = dataSetTable.getSelectedRows();
				if (r.length == 0){
					JOptionPane.showMessageDialog(this,  "No dataset selected.", "Deleting datasets",
							JOptionPane.INFORMATION_MESSAGE);
					return;
					}
				for (int j = 0; j < r.length; j++){
					this.dsTableModel.data.remove(r[j]);
					dsTableModel.fireTableDataChanged();
				}
			}
			break;
            case 1: { 
				int[] r = labelTable.getSelectedRows();
				if (r.length == 0){
					JOptionPane.showMessageDialog(this,  "No label selected.", "Deleting labels",
							JOptionPane.INFORMATION_MESSAGE);
					return;
					}
				for (int j = 0; j < r.length; j++){
					this.labelTableModel.data.remove(r[j]);
					labelTableModel.fireTableDataChanged();
				}
            }
        	break;
            case 2: { 
				int[] r = variableTable.getSelectedRows();
				if (r.length == 0){
					JOptionPane.showMessageDialog(this,  "No variable selected.", "Deleting variables",
							JOptionPane.INFORMATION_MESSAGE);
					return;
					}
				for (int j = 0; j < r.length; j++){
					this.variableTableModel.variables.remove(r[j]);
					variableTableModel.fireTableDataChanged();
				}
            }
        	break;
		}
	}

	public void acClear(){
		int i = tp.getSelectedIndex();
		
		switch (i) {
			case 0:{ 
				clearDataSetTable();
			}
			break;
            case 1: { 
            	clearLabelTable();
            }
        	break;
            case 2: { 
            	clearVariableTable();
            }
        	break;
            case 3: { 
            	clearPrePlotString();
            }
        	break;
		}
	}
	
	public void clearPrePlotString(){
		prePlotString.setText("");
	}
	
	public void clearDataSetTable(){
		int count = dataSetTable.getRowCount();
		for (int j = 0; j < count; j++){
			this.dsTableModel.data.remove(0);
		}
		dsTableModel.fireTableDataChanged();
		
	}

	public void clearLabelTable(){
		int count = labelTableModel.getRowCount();
		for (int j = 0; j < count; j++){
			this.labelTableModel.data.remove(0);
		}
		labelTableModel.fireTableDataChanged();
		
	}

	public void clearVariableTable(){
		int count = variableTableModel.getRowCount();
		for (int j = 0; j < count; j++){
			this.variableTableModel.variables.remove(0);
		}
		variableTableModel.fireTableDataChanged();
		
	}

	public void acPlot() throws IOException, InterruptedException{
		clearShell();
		println("calling GNUplot...");
		JGPgnuplot gp = getGNUplot();
		gp.setOut((JGPPrintWriter) this);
		gp.plotThreaded();
	}

	JGPgnuplot getGNUplot(){
		JGPgnuplot gp = new JGPgnuplot();
		for (int i = 0; i < dsTableModel.data.size(); i++){
			gp.dataSets.add(dsTableModel.data.get(i));
		}		
		for (int i = 0; i < labelTableModel.data.size(); i++){
			gp.labels.add(labelTableModel.data.get(i));
		}
		for (int i = 0; i < variableTableModel.variables.size(); i++){
			gp.variables.add(variableTableModel.variables.get(i));
		}
		gp.setTitle(tfTitle.getText());
		try {
			if (!tfMaxX.getText().trim().equals("")) 
				gp.setXmax(Double.parseDouble(tfMaxX.getText()));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this,  "Check max x value!"  + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		try {
			if (!tfMaxY.getText().trim().equals("")) 
				gp.setYmax(Double.parseDouble(tfMaxY.getText()));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this,  "Check max y value!"  + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		try {
			if (!tfMinX.getText().trim().equals("")) 
				gp.setXmin(Double.parseDouble(tfMinX.getText()));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this,  "Check min x value!"  + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		try {
			if (!tfMinY.getText().trim().equals("")) 
				gp.setYmin(Double.parseDouble(tfMinY.getText()));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Check min y value!"  + e.getMessage(),  "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		try {
			if (!tfMaxZ.getText().trim().equals("")) 
				gp.setZmax(Double.parseDouble(tfMaxZ.getText()));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this,  "Check max z value!"  + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		try {
			if (!tfMinZ.getText().trim().equals("")) 
				gp.setXmin(Double.parseDouble(tfMinZ.getText()));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this,  "Check min z value!"  + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}

		gp.setLogScaleX(cbLogScaleX.isSelected());
		gp.setLogScaleY(cbLogScaleY.isSelected());
		gp.setLogScaleZ(cbLogScaleZ.isSelected());
		gp.setXlabel(tfXLabel.getText());
		gp.setYlabel(tfYLabel.getText());
		gp.setZlabel(tfZLabel.getText());
		
		gp.setPrePlotString(prePlotString.getText() + "\n");
		
		if ( rb2D.isSelected() )
			gp.setPlotType(JGPgnuplot.PlotType.TWO_DIM);
		if ( rb3D.isSelected() )
			gp.setPlotType(JGPgnuplot.PlotType.THREE_DIM);
		
		return gp;
	}
	
	/**
	 * Shows a console dialog. Calls showConsole(String text, boolean append, boolean makeVisible) with makeVisible == true.
	 * @param text Text to display in the console.
	 */
	void showConsole(String text, boolean append){
		showConsole(text, append, true);
	}

	/**
	 * Shows a console dialog.
	 * @param text Text to display in the console.
	 * @param makeVisible Tells whether the console should be made visibile if not visible already.
	 */
	void showConsole(String text, boolean append, boolean makeVisible){
		
		if (consoleDialog == null)
			consoleDialog = new JGPConsoleDialog();
		if (makeVisible)	
			consoleDialog.setVisible(true);
		
		if (append)
			text = consoleDialog.getText() + "\n" + text;
		consoleDialog.setText(text);
		
	}
	
	public void acGenPlotCmds(){
		JGPgnuplot gp = getGNUplot();
		String plotString = gp.getPlotString();
		//taShell.setText( plotString );
		
		showConsole( plotString, false );
	}

	public void stateChanged(ChangeEvent e) {
		if (e.getSource().equals(rb2D)) 
			cl2Dor3D();
		if (e.getSource().equals(tp)){
			int i = tp.getSelectedIndex();
				       // Create the buttons.
				bEdit.setEnabled(i == 0);
				edit_menu_item.setEnabled(i == 0);
				
				bDelete.setEnabled(i < 3);
				delete_menu_item.setEnabled(i < 3);
				
				bAdd.setEnabled(i < 3);
				add_menu_item.setEnabled(i < 3);
				
				bClone.setEnabled(i == 0);
				clone_menu_item.setEnabled(i == 0) ;
				
				//bClear.setEnabled(i == 0);
				bMoveUp.setEnabled(i == 0);
				moveup_menu_item.setEnabled(i == 0);
				
				bMoveDown.setEnabled(i == 0);
				movedown_menu_item.setEnabled(i == 0);
		}
		
	}
	
	
	
	public void startCheckUpdates(){
		System.out.println("Starting update checking ...");
		updateChecker = new UpdateChecker(this);
		Thread t = new Thread(updateChecker);
		t.start();
		
	}
	
	public void stopCheckUpdates(){
		System.out.println("Stopping update checking ...");
		if (updateChecker != null)
			updateChecker.checkForUpdate = false;
	}


	/**
	 * ****************************************************************************
	 * Loads the component settings, previously stored in the specified text
	 * file, into the Controller Setup Window. The file is specified via a input
	 * parameter. Calls the read_file() method.
	 *
	 * @param file
	 *            The input file.
	 * @param cameraInfo.setupDialog
	 *            The Controller Setup window.
	 *
	 * @throws IOException
	 * @see read_file()
	 * @see SetupDialog
	 * @version 1.00
	 * @author Scott Streit
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 *         ****************************************************************************
	 */
	public void loadProject() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		JFileChooser file_chooser;
	
		File file;
		if (projectFileName != null) 
			file = new File(projectFileName);
		else
			file = new File(".");
			
		// Open a file chooser that points to the current dir.
		file_chooser = new JFileChooser(file.getPath());
		JGPFileFilter jgpFilter = new JGPFileFilter("jgp", "JGNUplot Files");

		file_chooser.setFileFilter(jgpFilter);
	
		// Set the Open dialog box size.
		file_chooser.setPreferredSize(new Dimension(800, 500));
		
		// Show the Save dialog box (returns the option selected)
		int selected = file_chooser.showOpenDialog(this);
	
		// If the Open button is pressed.
		if (selected == JFileChooser.APPROVE_OPTION) {
			 file = file_chooser.getSelectedFile();
			 
			 loadProject(file.getPath());
			 addRecentProject(file.getPath());
			 
		} else if (selected == JFileChooser.CANCEL_OPTION) {
			return;
		}
	
	}
	
	/**
	 * Updates the dialog title.
	 * @param fileName
	 */
	public void setFileTitle(String fileName){
		this.setTitle(fileName);
	}

	public void loadProject(String fileName){
		//clear tables
//		dsTableModel.data.clear();
//		dsTableModel.fireTableDataChanged();
//		labelTableModel.data.clear();
//		labelTableModel.fireTableDataChanged();
		clearDataSetTable();
		clearLabelTable();
		clearVariableTable();
		
		//parseFile(inFile);
		try {
			new JGPProjectManager(this).loadProjectFile(fileName);
		} catch (DOMException e) {
			showConsole("No standard project loaded:" + e.getMessage(), false);
		} catch (ClassNotFoundException e) {
			showConsole("No standard project loaded:" + e.getMessage(), false);
		} catch (ParserConfigurationException e) {
			showConsole("No standard project loaded:" + e.getMessage(), false);
		} catch (SAXException e) {
			showConsole("No standard project loaded:" + e.getMessage(), false);
		} catch (IOException e) {
			showConsole("No standard project loaded:" + e.getMessage(), false);
		} catch (InstantiationException e) {
			showConsole("No standard project loaded:" + e.getMessage(), false);
		} catch (IllegalAccessException e) {
			showConsole("No standard project loaded:" + e.getMessage(), false);
		} catch (JGPProjectManagerException e) {
			showConsole("No standard project loaded:" + e.getMessage(), false);
		}
		
	   	 // Pack the second column of the table
		packColumns(dataSetTable);
		
		 //update dialog title
		 setFileTitle(fileName);
		 
		 projectFileName = fileName;

		showStatus("Project loaded: " + projectFileName);


	}

	/**
	 * ****************************************************************************
	 * Saves the current cproject asking the user for a filname.
	 *
	 *
	 * @throws IOException
	 * ****************************************************************************
	 */
	public void saveProjectTo() throws IOException {
		JFileChooser file_chooser;
	
		File file;
		if (projectFileName != null) 
			file = new File(projectFileName);
		else
			file = new File(".");
			
		// Open a file chooser that points to the current dir.
		file_chooser = new JFileChooser(file.getPath());
		JGPFileFilter jgpFilter = new JGPFileFilter("jgp", "JGNUplot Files");
		file_chooser.setFileFilter(jgpFilter);
		
		// Set the file chooser size.
		file_chooser.setPreferredSize(new Dimension(500, 400));
	
		// Show the Save dialog box (returns the option selected)
		int selected = file_chooser.showSaveDialog(this);
	
		// If the Open button is pressed.
		if (selected == JFileChooser.APPROVE_OPTION) {
			// Get the selected file.
			file = file_chooser.getSelectedFile();
			
			projectFileName = file.getPath();
			setFileTitle(projectFileName);
			//outFile = new RandomAccessFile(file, "rw");
	
			//dumpSettings(outFile);
	
			new JGPProjectManager(this).writeProjectFile(file.getPath());
			addRecentProject(file.getPath());
			
			
			return;
		}
		// If the Cancel button is pressed.
		else if (selected == JFileChooser.CANCEL_OPTION) {
			return;
		}
	}

	/**
	 * ****************************************************************************
	 * Saves the current project. f nor project file name is know yet, i.e.
	 * if now prior project was opened or save, saveProjectTo will be called instead()
	 * ****************************************************************************
	 */
	public void saveProject() throws IOException {
		//check if already a project was save or loaded. Check if this project exists
		if (projectFileName == null || !(new File(projectFileName)).exists()) {
			saveProjectTo();
			return;
		}


		new JGPProjectManager(this).writeProjectFile(projectFileName);
		showStatus("Project saved to: " + projectFileName);
		addRecentProject(projectFileName);
			
	}
	
	public void showStatus(String s){
		statusBar.setText(s);
		statusBar.setCaretPosition(0);
	}
	
	public void saveSettingst(){
		if (debug) System.out.println("Saving settings to: " + SETTINGS_FILE);
		JGPSettingsManager sm = new JGPSettingsManager(this);

		
		//check whether project already exist in the list, 
		//	if yes, move it to the first position
		for (int i = startRecentProjects; i < (startRecentProjects + nRecentProjects); i++){
			JMenuItem mi = (JMenuItem) file_menu.getItem(i);
			if ( !mi.getText().equals("-")){
				sm.projectFiles.add(mi.getText().trim());
			}
		}
		sm.writeSettingsXML(SETTINGS_FILE);
		
		
	}

	public void loadSettings(){
		JGPSettingsManager sm = new JGPSettingsManager(this);

		try {
			sm.readSettingsXML(SETTINGS_FILE);
		} catch (ParserConfigurationException e) {
			showConsole("No standard settings loaded:" + e.getMessage(), false);
		} catch (SAXException e) {
			showConsole("No standard settings loaded:" + e.getMessage(), false);
		} catch (IOException e) {
			showConsole("No standard settings loaded:" + e.getMessage(), false);
		}
		//travers backwards to preserve order. addRecentProject will 
		// insert the projects at the beginning of the list, therfor oldest 
		// projects must be added first
		for (int i = sm.projectFiles.size()-1; i >= 0; i--){
			this.addRecentProject(sm.projectFiles.get(i));
		}
	
		
	}

	public void println(String s) {
		showConsole(s, true, false);
	}
	
	public void printerrln(String s) {
		//for errors the console will be made visible
		showConsole(s, true, true);
	}
	
	public void clearShell() {
		showConsole("", false, false);
	}

	/**
	 * Add a project (its filename) to the recent project list in the 
	 * file menu.
	 * @param textContent
	 */
	public void addRecentProject(String textContent) {
		textContent = textContent.trim();
		//check whether project already exist in the list, 
		//	if yes, move it to the first position
		for (int i = startRecentProjects; i < nRecentProjects; i++){
			JMenuItem mi = (JMenuItem) file_menu.getItem(i);
			if ( mi.getText().equals(textContent)){
				
				JMenuItem old_first = file_menu.getItem(startRecentProjects);
				file_menu.remove(startRecentProjects);
				file_menu.add(mi, startRecentProjects);
				file_menu.remove(i);
				file_menu.add(old_first, i);
				return;
			}
		}
		
		//move existing entries down
		//put added recent project on top of the list
		JGPRecentProjectMenuItem menu_item = new JGPRecentProjectMenuItem(textContent);
		menu_item.setActionCommand("load_recent_project: " + textContent);
		menu_item.addActionListener(this);
		file_menu.add(menu_item, startRecentProjects);
		file_menu.remove(startRecentProjects + nRecentProjects);

		
	}
	
	public void saveHistoryState(String desc){
		HistoryElement h =  
			new HistoryElement(desc, new JGPProjectManager(this).saveProjectXML());
		history.push(h);
		undo_menu_item.setText("Undo " + desc);
		undo_menu_item.setEnabled(true);
		//redo_menu_item.setText("Redo ");
		//redo_menu_item.setEnabled(false);
	}

	public void acUndo(){
		HistoryElement h = history.pop();
		HistoryElement previous = null; 
		if (history.isEmpty())
		{
			undo_menu_item.setText("Undo ");
			undo_menu_item.setEnabled(false);

		} else {
			previous = history.peek();
			undo_menu_item.setText("Undo " + previous.description);
		}
		//redo.push(current)
		
		try {
			clearDataSetTable();
			clearLabelTable();
			clearVariableTable();

			//now restore previous state
			new JGPProjectManager(this).loadProjectXML(h.state);
		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JGPProjectManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

	
	boolean valueChanged = false;
	HistoryElement tmpHistory;
	
	public void changedUpdate(DocumentEvent e) {
		valueChanged = true;
	}


	public void insertUpdate(DocumentEvent e) {
		valueChanged = true;
	}


	public void removeUpdate(DocumentEvent e) {
		valueChanged = true;
	}


	public void focusGained(FocusEvent e) {
		valueChanged = false;
		//save history since user might attempt to change the
		//	components value
		tmpHistory = new HistoryElement("textfield change", new JGPProjectManager(this).saveProjectXML());
	}


	public void focusLost(FocusEvent e) {
		//if the focus loosing component did  change its value
		// save history which was temporarey saved when component gained focus
		// focus
		if (valueChanged){
			history.push(tmpHistory);
			undo_menu_item.setText("Undo " + tmpHistory.description);
			undo_menu_item.setEnabled(true);
		}
	}






	

}
