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

package org.hopandfork.jgnuplot.control.project;

/*
 * Created on Aug 11, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.hopandfork.jgnuplot.JGP;
import org.hopandfork.jgnuplot.control.PlotController;
import org.hopandfork.jgnuplot.control.PlottableDataController;
import org.hopandfork.jgnuplot.gui.panel.BottomPanel;
import org.hopandfork.jgnuplot.gui.panel.OverviewPanel;
import org.hopandfork.jgnuplot.model.DataFile;
import org.hopandfork.jgnuplot.model.Plot;
import org.hopandfork.jgnuplot.model.Plot.Mode;
import org.hopandfork.jgnuplot.model.PlottableData;
import org.hopandfork.jgnuplot.model.style.GnuplotColor;
import org.hopandfork.jgnuplot.model.style.PlottingStyle;
import org.hopandfork.jgnuplot.utility.XMLManager;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

/**
 * @author mxhf
 *
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ProjectManager extends XMLManager {

	private OverviewPanel overviewPanel;
	private BottomPanel bottomPanel;

	protected File file;

	// Define the strings to be used in the setup file.
	private final String JGNUPLOT_CONFIG = "jGNUplot_config";

	private final String VERSION = "version";

	private final String TITLE = "plot_tile";

	private final String PLOT_TYPE = "plot_type";

	private final String MAX_X = "max_x_range";

	private final String MAX_Y = "max_y_range";

	private final String MIN_X = "min_x_range";

	private final String MIN_Y = "min_y_range";

	private final String MAX_Z = "min_z_range";

	private final String MIN_Z = "min_z_range";

	private final String XLABEL = "x_label";

	private final String YLABEL = "y_label";

	private final String ZLABEL = "z_label";

	private final String XLOGSCALE = "logscale_x_axis";

	private final String YLOGSCALE = "logscale_y_axis";

	private final String ZLOGSCALE = "logscale_z_axis";

	private final String DATASET_ITEMS = "datasets";

	private final String DATASET = "dataset";

	private final String LABEL_ITEMS = "labels";

	private final String LABEL = "label";

	private final String PS_FILENAME = "postscript file name";

	private final String SETTINGS_FILENAME = "settings file name";

	private final String PRE_PLOT_STRING = "pre_plot_string";

	private final String ID = "id";
	private final String TEXT = "text";
	private final String X_POS = "x_pos";
	private final String Y_POS = "y_pos";
	private final String RELATIVE_POS = "reletive_pos";
	private final String DO_PLOT = "do_plot";

	private final String CLASS = "class";
	private final String FILENAME = "filename";
	private final String DATASTRING = "datastring";
	private final String PRE_PROCESS_PROGRAM = "pre_process_program";
	private final String STYLE = "style";
	private final String COLOR = "color";
	private final String ADD_STYLE_OPT = "add_style_opt";
	private final String DATASET_TITLE = "dataset_title";

	private final String VARIABLE_ITEMS = "variables";
	private final String VARIABLE = "variable";
	private final String NAME = "name";
	private final String VALUE = "value";
	private final String ACTIVE = "active";

	private PlottableDataController plottableDataController;

	private PlotController plotController;

	/**
	 * ****************************************************************************
	 * Default Constructor.
	 * ****************************************************************************
	 */
	public ProjectManager(PlotController plotController, PlottableDataController plottableDataController) {
		this.plottableDataController = plottableDataController;
		this.plotController = plotController;
		file = new File(".");
	}

	public void patch_0_1(Document document) {
		NodeList datasets = document.getElementsByTagName(DATASET);
		for (int i = 0; i < datasets.getLength(); i++) {
			Element n = (Element) datasets.item(i);

			if (n.getElementsByTagName(CLASS).getLength() != 0) {
				String c = n.getElementsByTagName(CLASS).item(0).getTextContent().trim();
				if (c.equals("gnuplot.DataSet"))
					n.getElementsByTagName(CLASS).item(0).setTextContent("jgp.JGPDataSet");
				if (c.equals("gnuplot.Function"))
					n.getElementsByTagName(CLASS).item(0).setTextContent("jgp.JGPFunction");

			}
		}
		// load labels
		NodeList variables = document.getElementsByTagName(VARIABLE);

		for (int i = 0; i < variables.getLength(); i++) {
			Element n = (Element) variables.item(i);
			if (n.getElementsByTagName(CLASS).getLength() != 0) {
				String c = n.getElementsByTagName(CLASS).item(0).getTextContent().trim();
				if (c.equals("gnuplot.GPVariable"))
					n.getElementsByTagName(CLASS).item(0).setTextContent("jgp.JGPGnuplotVariable");
				if (c.equals("gnuplot.StringVariable"))
					n.getElementsByTagName(CLASS).item(0).setTextContent("jgp.JGPStringVariable");
			}
		} // for (int i = 0; i < variables.getLength(); i++)

	}

	/**
	 * This is a routing to patch old project files.
	 * 
	 * @param document
	 * @param old_version
	 * @throws ProjectManagerException
	 */
	public void patch(Document document, String old_version) throws ProjectManagerException {
		if (old_version.equals("0.0")) {
			// version 0.0 needs same patching as version 0.1
			patch_0_1(document);
		} else if (old_version.equals("0.1")) {
			patch_0_1(document);
		} else if (old_version.equals("0.1.1")) {
			patch_0_1(document);
		} else {
			throw new ProjectManagerException(
					"Could not patch project file of version " + old_version + " there is no aviable patch.");
		}

	}

	public void loadProjectFile(String xmlFile)
			throws DOMException, ClassNotFoundException, ParserConfigurationException, SAXException, IOException,
			InstantiationException, IllegalAccessException, ProjectManagerException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document document = null;
		DocumentBuilder builder = factory.newDocumentBuilder();

		document = builder.parse(new File(xmlFile));
		loadProjectXML(document);
	}

	public void loadProjectXML(Document document) throws ProjectManagerException, DOMException, ClassNotFoundException,
			InstantiationException, IllegalAccessException {

		Plot plot = plotController.getCurrent();

		// patch project file so that this version of jgp can deal with it.
		if (document.getElementsByTagName(VERSION) != null) {
			Node n = document.getElementsByTagName(VERSION).item(0);
			String v;
			// Verion 0.0 had no version information, we therfore set the
			// version to 0.0
			// if no version information was found.
			if (n == null)
				v = "0.0";
			else
				v = n.getTextContent().trim();
			if (v.compareTo(JGP.getVersion()) < 0) {
				patch(document, v);
			}
		}

		if (document.getElementsByTagName(TITLE).item(0) != null)
			plot.setTitle(document.getElementsByTagName(TITLE).item(0).getTextContent());
		if (document.getElementsByTagName(PLOT_TYPE).item(0) != null) {
			String splotType;
			splotType = document.getElementsByTagName(PLOT_TYPE).item(0).getTextContent();
			if (splotType.equals(Plot.Mode.PLOT_2D.name()))
				plot.setMode(Mode.PLOT_2D);
			else
				plot.setMode(Mode.PLOT_3D);

		}

		if (document.getElementsByTagName(MAX_X).item(0) != null)
			plot.setXmax(Double.parseDouble(document.getElementsByTagName(MAX_X).item(0).getTextContent()));
		if (document.getElementsByTagName(MIN_X).item(0) != null)
			plot.setXmin(Double.parseDouble(document.getElementsByTagName(MIN_X).item(0).getTextContent()));
		if (document.getElementsByTagName(MAX_Y).item(0) != null)
			plot.setYmax(Double.parseDouble(document.getElementsByTagName(MAX_Y).item(0).getTextContent()));
		if (document.getElementsByTagName(MIN_Y).item(0) != null)
			plot.setYmin(Double.parseDouble(document.getElementsByTagName(MIN_Y).item(0).getTextContent()));
		if (document.getElementsByTagName(MAX_Z).item(0) != null)
			plot.setZmax(Double.parseDouble(document.getElementsByTagName(MAX_Z).item(0).getTextContent()));
		if (document.getElementsByTagName(MIN_Z).item(0) != null)
			plot.setZmin(Double.parseDouble(document.getElementsByTagName(MIN_Z).item(0).getTextContent()));
		if (document.getElementsByTagName(XLABEL).item(0) != null)
			plot.setXlabel(document.getElementsByTagName(XLABEL).item(0).getTextContent());
		if (document.getElementsByTagName(YLABEL).item(0) != null)
			plot.setYlabel(document.getElementsByTagName(YLABEL).item(0).getTextContent());
		if (document.getElementsByTagName(ZLABEL).item(0) != null)
			plot.setZlabel(document.getElementsByTagName(ZLABEL).item(0).getTextContent());

		if (document.getElementsByTagName(PRE_PLOT_STRING) != null
				&& document.getElementsByTagName(PRE_PLOT_STRING).item(0) != null)
			plot.setPrePlotString(document.getElementsByTagName(PRE_PLOT_STRING).item(0).getTextContent());

		if (document.getElementsByTagName(XLOGSCALE).item(0) != null)
			plot.setLogScaleX(Boolean.parseBoolean(document.getElementsByTagName(XLOGSCALE).item(0).getTextContent()));

		if (document.getElementsByTagName(YLOGSCALE).item(0) != null)
			plot.setLogScaleY(Boolean.parseBoolean(document.getElementsByTagName(YLOGSCALE).item(0).getTextContent()));

		if (document.getElementsByTagName(ZLOGSCALE).item(0) != null)
			plot.setLogScaleZ(Boolean.parseBoolean(document.getElementsByTagName(ZLOGSCALE).item(0).getTextContent()));

		// if (document.getElementsByTagName(PS_FILENAME) != null &&
		// document.getElementsByTagName(PS_FILENAME).item(0) != null)
		// mainWindow.psFileName =
		// document.getElementsByTagName(PS_FILENAME).item(0).getTextContent();

		// if (document.getElementsByTagName(SETTINGS_FILENAME) != null &&
		// document.getElementsByTagName(SETTINGS_FILENAME).item(0) != null)
		// mainWindow.settingsFileName =
		// document.getElementsByTagName(SETTINGS_FILENAME).item(0).getTextContent();

		// load datasets
		// Element dataset_items = (Element)
		// document.getElementsByTagName(DATASET_ITEMS).item(0);
		// NodeList datasets = document.getElementsByTagName(DATASET_ITEMS);
		NodeList datasets = document.getElementsByTagName(DATASET);
		for (int i = 0; i < datasets.getLength(); i++) {
			DataFile ds;
			Element n = (Element) datasets.item(i);

			if (n.getElementsByTagName(CLASS).getLength() != 0) {
				Class c = Class.forName(n.getElementsByTagName(CLASS).item(0).getTextContent());
				ds = (DataFile) c.newInstance();

				if (n.getElementsByTagName(FILENAME).getLength() != 0)
					ds.setFileName(n.getElementsByTagName(FILENAME).item(0).getTextContent());
				// if (n.getElementsByTagName(DATASTRING).getLength() != 0)
				// ds.setDataString(n.getElementsByTagName(DATASTRING).item(0).getTextContent());
				if (n.getElementsByTagName(DATASET_TITLE).getLength() != 0)
					ds.setTitle(n.getElementsByTagName(DATASET_TITLE).item(0).getTextContent());
				if (n.getElementsByTagName(COLOR).getLength() != 0) {
					String sc = n.getElementsByTagName(COLOR).item(0).getTextContent();
					if (sc.trim().equals("auto"))
						ds.setColor(null);
					else
						ds.setColor(GnuplotColor.parseHexString(sc));
				}
				if (n.getElementsByTagName(STYLE).getLength() != 0)
					ds.setStyle(PlottingStyle.valueOf(n.getElementsByTagName(STYLE).item(0).getTextContent()));
				if (n.getElementsByTagName(ADD_STYLE_OPT).getLength() != 0)
					ds.setAddStyleOpt(n.getElementsByTagName(ADD_STYLE_OPT).item(0).getTextContent());
				if (n.getElementsByTagName(DO_PLOT).getLength() != 0)
					ds.setEnabled(Boolean.parseBoolean(n.getElementsByTagName(DO_PLOT).item(0).getTextContent()));

				if (n.getElementsByTagName(PRE_PROCESS_PROGRAM).getLength() != 0)
					ds.setPreProcessProgram(n.getElementsByTagName(PRE_PROCESS_PROGRAM).item(0).getTextContent());

				// mainWindow.plottableDataTableModel.data.add(ds); TODO
			}
		}
		// load labels // TODO

	}

	/**
	 * Generates a XML document which saves all information about the current
	 * project.
	 * 
	 * @return XML document.
	 */
	public Document saveProjectXML() {
		Plot plot = plotController.getCurrent();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document document = null;
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument(); // Create from whole cloth

			Element root = (Element) document.createElement(JGNUPLOT_CONFIG);
			document.appendChild(root);

			addTextNode(document, root, VERSION, JGP.getVersion());
			addTextNode(document, root, TITLE, plot.getTitle());

			addTextNode(document, root, PLOT_TYPE, plot.getMode().name());

			addTextNode(document, root, MAX_X, ""+plot.getXmax());
			addTextNode(document, root, MIN_X, ""+plot.getXmin());
			addTextNode(document, root, MAX_Y, ""+plot.getYmax());
			addTextNode(document, root, MIN_Y, ""+plot.getYmin());
			
			addTextNode(document, root, MAX_Z, ""+plot.getZmax());
			addTextNode(document, root, MIN_Z, ""+plot.getZmin());
			addTextNode(document, root, XLABEL, plot.getXlabel());
			addTextNode(document, root, YLABEL, plot.getYlabel());
			addTextNode(document, root, ZLABEL, plot.getZlabel());
			addTextNode(document, root, XLOGSCALE, ""+plot.isLogScaleX());
			addTextNode(document, root, YLOGSCALE, ""+plot.isLogScaleY());
			addTextNode(document, root, ZLOGSCALE, ""+plot.isLogScaleZ());
			addTextNode(document, root, PRE_PLOT_STRING, plot.getPrePlotString());
			// addTextNode(document, root, PS_FILENAME, mainWindow.psFileName);
			// addTextNode(document, root, SETTINGS_FILENAME,
			// mainWindow.settingsFileName);

			// add datasets
			Element datasets = (Element) document.createElement(DATASET_ITEMS);

			List<PlottableData> allPlottableData = plottableDataController.getPlottableData();
			for (int i = 0; i < allPlottableData.size(); i++) {
				PlottableData ds = allPlottableData.get(i);
				Element dataset = (Element) document.createElement(DATASET);
				dataset.setAttribute(ID, i + "");

				addTextNode(document, dataset, CLASS, ds.getClass().getName() + "");
				String fname = "";
				String preProcessProgram = "";
				if (ds instanceof DataFile) {
					fname = ((DataFile) ds).getFileName();
					preProcessProgram = ((DataFile) ds).getPreProcessProgram();
				}
				addTextNode(document, dataset, FILENAME, fname + "");
				// addTextNode(document, dataset, DATASTRING, ds.getDataString()
				// + "");
				addTextNode(document, dataset, DATASET_TITLE, ds.getTitle() + "");
				Color c = ds.getColor();
				String sc = "auto";
				if (c != null)
					sc = ds.getColor().getHexString();
				addTextNode(document, dataset, COLOR, sc + "");
				addTextNode(document, dataset, STYLE, ds.getStyle().name() + "");
				addTextNode(document, dataset, ADD_STYLE_OPT, ds.getAddStyleOpt() + "");
				addTextNode(document, dataset, DO_PLOT, ds.isEnabled() + "");
				addTextNode(document, dataset, PRE_PROCESS_PROGRAM, preProcessProgram + "");

				datasets.appendChild(dataset);
			}
			root.appendChild(datasets);

			// add labels // TODO
		} catch (ParserConfigurationException pce) {
			// Parser with specified options can't be built
			pce.printStackTrace();
		}

		return document;
	}

	/**
	 * Saves the current project to a file.
	 * 
	 * @param xmlFile
	 *            Path and filename for the project.
	 */
	public void writeProjectFile(String xmlFile) {
		try {
			FileOutputStream fos = new FileOutputStream(xmlFile);
			// XERCES 1 or 2 additionnal classes.
			OutputFormat of = new OutputFormat("XML", "ISO-8859-1", true);
			of.setIndent(1);
			of.setIndenting(true);
			// of.setDoctype(null,"users.dtd");
			XMLSerializer serializer = new XMLSerializer(fos, of);
			// As a DOM Serializer
			serializer.asDOMSerializer();
			serializer.serialize(saveProjectXML().getDocumentElement());
			fos.close();

		} catch (FileNotFoundException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}

	public String saveProjectString() throws IOException {
		OutputFormat of = new OutputFormat("XML", "ISO-8859-1", true);
		of.setIndent(1);
		of.setIndenting(true);

		StringWriter stringWriter = new StringWriter();
		XMLSerializer serializer = new XMLSerializer(stringWriter, of);
		// As a DOM Serializer
		serializer.asDOMSerializer();
		serializer.serialize(saveProjectXML().getDocumentElement());

		stringWriter.flush();
		return stringWriter.getBuffer().toString();

	}

}
