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

package org.hopandfork.jgnuplot;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeListener;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hopandfork.jgnuplot.control.LabelController;
import org.hopandfork.jgnuplot.control.PlotController;
import org.hopandfork.jgnuplot.control.PlottableDataController;
import org.hopandfork.jgnuplot.control.SettingsManager;
import org.hopandfork.jgnuplot.control.project.ProjectManager;
import org.hopandfork.jgnuplot.control.project.ProjectManagerException;
import org.hopandfork.jgnuplot.gui.JGPFileFilter;
import org.hopandfork.jgnuplot.gui.RecentProjectMenuItem;
import org.hopandfork.jgnuplot.gui.dialog.AboutDialog;
import org.hopandfork.jgnuplot.gui.dialog.ConsoleDialog;
import org.hopandfork.jgnuplot.gui.dialog.DataFileDialog;
import org.hopandfork.jgnuplot.gui.dialog.FunctionDialog;
import org.hopandfork.jgnuplot.gui.dialog.PlotDialog;
import org.hopandfork.jgnuplot.gui.panel.BottomPanel;
import org.hopandfork.jgnuplot.gui.panel.JGPPanel;
import org.hopandfork.jgnuplot.gui.panel.OverviewPanel;
import org.hopandfork.jgnuplot.gui.panel.OverviewPanel.MenuDisplay;
import org.hopandfork.jgnuplot.gui.panel.PreviewPanel;
import org.hopandfork.jgnuplot.utility.UpdateChecker;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

public class JGP extends JFrame implements ActionListener, MenuDisplay {

	private static Logger LOG = Logger.getLogger(JGP.class);

	public static final boolean debug = true;

	private UpdateChecker updateChecker;

	private ConsoleDialog consoleDialog;

	private JCheckBox cbUpdateCheck;

	public JTextArea taShell;
	public JTextArea prePlotString;

	public JMenuItem add_datafile_menu_item;
	public JMenuItem add_function_menu_item;
	public JMenuItem delete_menu_item;
	public JMenuItem clear_menu_item;
	public JMenuItem edit_menu_item;
	public JMenuItem moveup_menu_item;
	public JMenuItem movedown_menu_item;

	public String projectFileName;

	public JMenu file_menu;

	private OverviewPanel overviewPanel;

	private JPanel previewPanel;

	private BottomPanel bottomPanel;

	public static final int nRecentProjects = 8;

	public static final int startRecentProjects = 7;

	private static final long serialVersionUID = 1L;

	private static final String SETTINGS_FILE = ".JGP";

	private static final String STANDARD_PROJECT_FILE = ".JGP.project";

	/**
	 * Controller for PlottableData management.
	 */
	private PlottableDataController plottableDataController = new PlottableDataController();

	/**
	 * Controller for Label management.
	 */
	private LabelController labelController = new LabelController();

	private PlotController plotController = new PlotController();

	public JGP() {
		this.setTitle("JGNUplot");

		this.setLocationByPlatform(true);

		// Set the dialog box size.
		setSize(550, 750);
		this.setMinimumSize(new Dimension(550, 750));

		// Create the menu bar and add it to the dialog box.
		this.setJMenuBar(create_menu_bar());

		JGPPanel content_pane = new JGPPanel();
		this.add(content_pane);
		
		// Set the default panel layout.
		GridBagLayout gbl = new GridBagLayout();
		content_pane.setLayout(gbl);

		/* Creates panels. */
		overviewPanel = new OverviewPanel(this, bottomPanel, plottableDataController, labelController);
		bottomPanel = new BottomPanel(overviewPanel, plottableDataController, labelController, plotController);
		previewPanel = new PreviewPanel();

		/* Sets minimum size for panels. */
		Dimension minimumSize = new Dimension(100, 150);
		previewPanel.setMinimumSize(minimumSize);
		overviewPanel.setMinimumSize(minimumSize);
		bottomPanel.setMinimumSize(minimumSize);

		/* Creates split pane. */
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, overviewPanel, previewPanel);
		splitPane.setOneTouchExpandable(true);
		splitPane.setResizeWeight(0.4);

		content_pane.add(splitPane, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL);
		content_pane.add(bottomPanel, 0, 1, 1, 1, GridBagConstraints.HORIZONTAL);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exit();
			}
		}); // ignore event itself

		// load settings, for example recently used projects
		loadSettings();

		loadStandardProject();

	}

	@Deprecated
	public static String getVersion() {
		return "0.1.2"; // TODO
	}

	/**
	 * *******************************************************************************
	 * This method creates the default menu bar for the Voodoo dialog box. The
	 * menu bar contains a single "Close" button.
	 *
	 * @return Returns a JMenuBar that contains a "Close" menu button.
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

		JMenuItem save_std_menu_item = new JMenuItem("Save Settings as standard");
		save_std_menu_item.addActionListener(this);
		save_std_menu_item.setActionCommand("Save project as standard");
		file_menu.add(save_std_menu_item);

		file_menu.addSeparator();

		JMenuItem load_menu_item = new JMenuItem("Load project...");
		load_menu_item.addActionListener(this);
		load_menu_item.setActionCommand("Load project...");
		file_menu.add(load_menu_item);

		file_menu.addSeparator();

		for (int i = 0; i < nRecentProjects; i++) {
			RecentProjectMenuItem menu_item = new RecentProjectMenuItem("-");
			menu_item.addActionListener(this);
			file_menu.add(menu_item);
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

		edit_menu.addSeparator();

		add_datafile_menu_item = new JMenuItem("Add DataFile");
		add_datafile_menu_item.addActionListener(this);
		add_datafile_menu_item.setActionCommand("add_datafile");
		edit_menu.add(add_datafile_menu_item);

		add_function_menu_item = new JMenuItem("Add Function");
		add_function_menu_item.addActionListener(this);
		add_function_menu_item.setActionCommand("add_function");
		edit_menu.add(add_function_menu_item);

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

		moveup_menu_item = new JMenuItem("Move dataset(s) up");
		moveup_menu_item.addActionListener(this);
		moveup_menu_item.setActionCommand("moveup");
		edit_menu.add(moveup_menu_item);

		movedown_menu_item = new JMenuItem("Move dataset(s) down");
		movedown_menu_item.addActionListener(this);
		movedown_menu_item.setActionCommand("movedown");
		edit_menu.add(movedown_menu_item);

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

	public static void main(String[] args) throws MalformedURLException {
		/* Log4j initialization */
		PropertyConfigurator.configure(
				System.getProperty("user.dir") + System.getProperty("file.separator") + "config/log4j2.properties");

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
		if (e.getActionCommand().equals("add_datafile")) {
			DataFileDialog dataFileDialog = new DataFileDialog(plottableDataController);
			dataFileDialog.setVisible(true);
		} else if (e.getActionCommand().equals("add_function")) {
			FunctionDialog addFunctionDialog = new FunctionDialog(plottableDataController);
			addFunctionDialog.setVisible(true);
		} else if (e.getActionCommand().equals("Exit")) {
			exit();
		} else if (e.getActionCommand().equals("new")) {
			acNew();
		} else if (e.getActionCommand().equals("about")) {
			AboutDialog.showAboutDialog();
		} else if (e.getActionCommand().equals("Save project to..."))
			acSaveProjectTo();
		else if (e.getActionCommand().equals("Save"))
			acSaveProject();
		else if (e.getActionCommand().equals("Save project as standard"))
			acSaveStandardProject();
		else if (e.getActionCommand().contains("load_recent_project:"))
			loadProject(e.getActionCommand().replace("load_recent_project:", "").trim());
		else if (e.getActionCommand().equals("Load project..."))
			acLoadProject();
		else if (e.getActionCommand().equals("updatecheck")) {
			if (cbUpdateCheck.isSelected())
				startCheckUpdates();
			else
				stopCheckUpdates();
		}

	}

	private void exit() {
		saveSettingst();
		System.exit(0);
	}

	public void acShowConsole() {
		showConsole("", true);

	}

	public void acSaveProjectTo() {
		try {
			saveProjectTo();
		} catch (IOException e1) {
			LOG.error("Error saving project: " + e1.getMessage());
		}
	}

	public void acSaveProject() {
		try {
			saveProject();
		} catch (IOException e1) {
			LOG.error("Error saving project: " + e1.getMessage());
		}
	}

	public void acSaveStandardProject() {

		new ProjectManager(plotController, plottableDataController).writeProjectFile(STANDARD_PROJECT_FILE);

	}

	public void acLoadProject() {
		try {
			loadProject();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error loading project: ", JOptionPane.ERROR_MESSAGE);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error loading project: ", JOptionPane.ERROR_MESSAGE);
		} catch (InstantiationException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error loading project: ", JOptionPane.ERROR_MESSAGE);
		} catch (IllegalAccessException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error loading project: ", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void loadStandardProject() {
		/* Checks if a project already exists before load it */
		if (Files.exists(Paths.get(STANDARD_PROJECT_FILE))) {
			try {
				new ProjectManager(plotController, plottableDataController).loadProjectFile(STANDARD_PROJECT_FILE);
			} catch (RuntimeException e) {
				LOG.error("No standard project loaded:" + e.getMessage());
			} catch (ClassNotFoundException e) {
				LOG.error("No standard project loaded:" + e.getMessage());
			} catch (ParserConfigurationException e) {
				LOG.error("No standard project loaded:" + e.getMessage());
			} catch (SAXException e) {
				LOG.error("No standard project loaded:" + e.getMessage());
			} catch (IOException e) {
				LOG.error("No standard project loaded:" + e.getMessage());
			} catch (InstantiationException e) {
				LOG.error("No standard project loaded:" + e.getMessage());
			} catch (IllegalAccessException e) {
				LOG.error("No standard project loaded:" + e.getMessage());
			} catch (ProjectManagerException e) {
				LOG.error("No standard project loaded:" + e.getMessage());
			}
		}
	}

	public void acNew() {
		// TODO BottomPanel.reset
		bottomPanel.reset();

		// TODO Overview.reset

		setFileTitle("<New>");
		this.projectFileName = null;
	}

	public void startCheckUpdates() {
		System.out.println("Starting update checking ...");
		updateChecker = new UpdateChecker(this, plottableDataController);
		Thread t = new Thread(updateChecker);
		t.start();

	}

	public void stopCheckUpdates() {
		System.out.println("Stopping update checking ...");
		if (updateChecker != null)
			updateChecker.setCheckForUpdate(false);
	}

	public void loadProject()
			throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
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
	 *
	 * @param fileName
	 */
	public void setFileTitle(String fileName) {
		this.setTitle(fileName);
	}

	public void loadProject(String fileName) {

		try {
			new ProjectManager(plotController, plottableDataController).loadProjectFile(fileName);
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
		} catch (ProjectManagerException e) {
			showConsole("No standard project loaded:" + e.getMessage(), false);
		}

		// Pack the second column of the table
		// TableUtils.packColumns(dataSetTable);

		// update dialog title
		setFileTitle(fileName);

		projectFileName = fileName;

	}

	/**
	 * ****************************************************************************
	 * Saves the current cproject asking the user for a filname.
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
			// outFile = new RandomAccessFile(file, "rw");

			// dumpSettings(outFile);

			new ProjectManager(plotController, plottableDataController).writeProjectFile(file.getPath());
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
	 * Saves the current project. f nor project file name is know yet, i.e. if
	 * now prior project was opened or save, saveProjectTo will be called
	 * instead()
	 * ****************************************************************************
	 */
	public void saveProject() throws IOException {
		// check if already a project was save or loaded. Check if this project
		// exists
		if (projectFileName == null || !(new File(projectFileName)).exists()) {
			saveProjectTo();
			return;
		}

		new ProjectManager(plotController, plottableDataController).writeProjectFile(projectFileName);
		addRecentProject(projectFileName);

	}

	public void saveSettingst() {
		if (debug)
			System.out.println("Saving settings to: " + SETTINGS_FILE);
		SettingsManager sm = new SettingsManager(this);

		// check whether project already exist in the list,
		// if yes, move it to the first position
		for (int i = startRecentProjects; i < (startRecentProjects + nRecentProjects); i++) {
			JMenuItem mi = (JMenuItem) file_menu.getItem(i);
			if (!mi.getText().equals("-")) {
				sm.projectFiles.add(mi.getText().trim());
			}
		}
		sm.writeSettingsXML(SETTINGS_FILE);

	}

	public void loadSettings() {
		SettingsManager sm = new SettingsManager(this);

		try {
			sm.readSettingsXML(SETTINGS_FILE);
		} catch (ParserConfigurationException e) {
			showConsole("No standard settings loaded:" + e.getMessage(), false);
		} catch (SAXException e) {
			showConsole("No standard settings loaded:" + e.getMessage(), false);
		} catch (IOException e) {
			showConsole("No standard settings loaded:" + e.getMessage(), false);
		}
		// travers backwards to preserve order. addRecentProject will
		// insert the projects at the beginning of the list, therfor oldest
		// projects must be added first
		for (int i = sm.projectFiles.size() - 1; i >= 0; i--) {
			this.addRecentProject(sm.projectFiles.get(i));
		}

	}

	public void println(String s) {
		showConsole(s, true, false);
	}

	public void printerrln(String s) {
		// for errors the console will be made visible
		showConsole(s, true, true);
	}

	public void clearShell() {
		showConsole("", false, false);
	}

	/**
	 * Add a project (its filename) to the recent project list in the file menu.
	 *
	 * @param textContent
	 */
	public void addRecentProject(String textContent) {
		textContent = textContent.trim();
		// check whether project already exist in the list,
		// if yes, move it to the first position
		for (int i = startRecentProjects; i < nRecentProjects; i++) {
			JMenuItem mi = (JMenuItem) file_menu.getItem(i);
			if (mi.getText().equals(textContent)) {

				JMenuItem old_first = file_menu.getItem(startRecentProjects);
				file_menu.remove(startRecentProjects);
				file_menu.add(mi, startRecentProjects);
				file_menu.remove(i);
				file_menu.add(old_first, i);
				return;
			}
		}

		// move existing entries down
		// put added recent project on top of the list
		RecentProjectMenuItem menu_item = new RecentProjectMenuItem(textContent);
		menu_item.setActionCommand("load_recent_project: " + textContent);
		menu_item.addActionListener(this);
		file_menu.add(menu_item, startRecentProjects);
		file_menu.remove(startRecentProjects + nRecentProjects);

	}

	/**
	 * Shows a console dialog. Calls showConsole(String text, boolean append,
	 * boolean makeVisible) with makeVisible == true.
	 *
	 * @param text
	 *            Text to display in the console.
	 */
	void showConsole(String text, boolean append) {
		showConsole(text, append, true);
	}

	/**
	 * Shows a console dialog.
	 *
	 * @param text
	 *            Text to display in the console.
	 * @param makeVisible
	 *            Tells whether the console should be made visibile if not
	 *            visible already.
	 */
	public void showConsole(String text, boolean append, boolean makeVisible) {

		if (consoleDialog == null)
			consoleDialog = new ConsoleDialog();
		if (makeVisible)
			consoleDialog.setVisible(true);

		if (append)
			text = consoleDialog.getText() + "\n" + text;
		consoleDialog.setText(text);

	}

	@Override
	public JMenuItem getEdit_menu_item() {
		return edit_menu_item;
	}

	@Override
	public JMenuItem getDelete_menu_item() {
		return delete_menu_item;
	}

	@Override
	public JMenuItem getMoveup_menu_item() {
		return moveup_menu_item;
	}

	@Override
	public JMenuItem getMovedown_menu_item() {
		return movedown_menu_item;
	}
}
