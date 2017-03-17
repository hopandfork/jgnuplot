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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import jgp.JGPColor;
import jgp.JGPDataSet;
import jgp.JGPLabel;
import jgp.JGPPlotable;
import jgp.JGPRelativePos;
import jgp.JGPStyle;
import jgp.JGPVariable;
import jgp.JGPgnuplot;

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
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class JGPProjectManager extends JGPXMLManager {

	private JGP mainWindow;


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
	private final String  TEXT = "text";
	private final String  X_POS = "x_pos";
	private final String  Y_POS = "y_pos";
	private final String  RELATIVE_POS = "reletive_pos";
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


		
	/**
	 * ****************************************************************************
	 * Default Constructor.
	 * ****************************************************************************
	 */
	public JGPProjectManager(JGP mainWindow) {
		this.mainWindow = mainWindow;
		file = new File(".");
	}

	public void patch_0_1(Document document){
		NodeList datasets =
		      document.getElementsByTagName(DATASET);
		for (int i = 0; i < datasets.getLength(); i++){
			Element n = (Element) datasets.item(i);
			
				if (n.getElementsByTagName(CLASS).getLength() != 0){
					String c = n.getElementsByTagName(CLASS).item(0).getTextContent().trim();
					if (c.equals("gnuplot.DataSet"))
						n.getElementsByTagName(CLASS).item(0).setTextContent("jgp.JGPDataSet");
					if (c.equals("gnuplot.Function")) 
						n.getElementsByTagName(CLASS).item(0).setTextContent("jgp.JGPFunction");
						
				}
		}
		//load labels
		NodeList variables =
		      document.getElementsByTagName(VARIABLE);

		for (int i = 0; i < variables.getLength(); i++){
			Element n = (Element) variables.item(i);
					if (n.getElementsByTagName(CLASS).getLength() != 0){
						String c = n.getElementsByTagName(CLASS).item(0).getTextContent().trim();
						if (c.equals("gnuplot.GPVariable"))
							n.getElementsByTagName(CLASS).item(0).setTextContent("jgp.JGPGnuplotVariable");
						if (c.equals("gnuplot.StringVariable")) 
							n.getElementsByTagName(CLASS).item(0).setTextContent("jgp.JGPStringVariable");			
					}
		}//for (int i = 0; i < variables.getLength(); i++)
		
		
		
	}
	/**
	 * This is a routing to patch old project files.
	 * @param document
	 * @param old_version
	 * @throws JGPProjectManagerException 
	 */
	public void patch(Document document, String old_version) throws JGPProjectManagerException{
		if ( old_version.equals("0.0") ){
			this.mainWindow.showConsole("Project file was created with version 0.0. Patch will be applied. File will not be touched!", false, false);
			//version 0.0 needs same patching as version 0.1
			patch_0_1(document);
		} else	if ( old_version.equals("0.1") ){
			this.mainWindow.showConsole("Project file was created with version 0.1. Patch will be applied. File will not be touched!", false, false);
			patch_0_1(document);
		} else	if ( old_version.equals("0.1.1") ){
			this.mainWindow.showConsole("Project file was created with version 0.1.1. Patch will be applied. File will not be touched!", false, false);
			patch_0_1(document);
		} else {
			throw new JGPProjectManagerException("Could not patch project file of version " + old_version + 
					" there is no aviable patch." );
		}
			
	}
	
	public void loadProjectFile(String xmlFile) throws DOMException, ClassNotFoundException, ParserConfigurationException, SAXException, IOException, InstantiationException, IllegalAccessException, JGPProjectManagerException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document document = null;
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(new File(xmlFile));
			loadProjectXML(document);
	}
	
	public void loadProjectXML(Document document) throws JGPProjectManagerException, DOMException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		//patch project file so that this version of jgp can deal with it.
		if (document.getElementsByTagName(VERSION) != null){
			Node n = document.getElementsByTagName(VERSION).item(0);
			String v;
			//Verion 0.0 had no version information, we therfore set the version to 0.0
			// if no version information was found.
			if (n == null)
				v = "0.0";
			else
				v = n.getTextContent().trim();
			if (v.compareTo(JGP.getVersion()) < 0){
				patch(document, v);
			}
		}
			

		if (document.getElementsByTagName(TITLE).item(0) != null)
			mainWindow.tfTitle.setText(document.getElementsByTagName(TITLE).item(0).getTextContent() );
		if (document.getElementsByTagName(PLOT_TYPE).item(0) != null){
					String splotType;
					splotType =
						 document.getElementsByTagName(PLOT_TYPE).item(0).getTextContent() ; 
					if (splotType.equals(JGPgnuplot.PlotType.TWO_DIM.toString()))
						mainWindow.rb2D.setSelected(true);
					else
						mainWindow.rb3D.setSelected(true);
						
		}
		
		
		if (document.getElementsByTagName(MAX_X).item(0) != null)
			mainWindow.tfMaxX.setText(document.getElementsByTagName(MAX_X).item(0).getTextContent() );
		if (document.getElementsByTagName(MIN_X).item(0) != null)
			mainWindow.tfMinX.setText(document.getElementsByTagName(MIN_X).item(0).getTextContent() );
		if (document.getElementsByTagName(MAX_Y).item(0) != null)
			mainWindow.tfMaxY.setText(document.getElementsByTagName(MAX_Y).item(0).getTextContent() );
		if (document.getElementsByTagName(MIN_Y).item(0) != null)
			mainWindow.tfMinY.setText(document.getElementsByTagName(MIN_Y).item(0).getTextContent() );
		if (document.getElementsByTagName(MAX_Z).item(0) != null )
			mainWindow.tfMaxZ.setText(document.getElementsByTagName(MAX_Z).item(0).getTextContent() );
		if (document.getElementsByTagName(MIN_Z).item(0) != null)
			mainWindow.tfMinZ.setText(document.getElementsByTagName(MIN_Z).item(0).getTextContent() );
		if (document.getElementsByTagName(XLABEL).item(0) != null)
			mainWindow.tfXLabel.setText(document.getElementsByTagName(XLABEL).item(0).getTextContent() );
		if (document.getElementsByTagName(YLABEL).item(0) != null)
			mainWindow.tfYLabel.setText(document.getElementsByTagName(YLABEL).item(0).getTextContent() );
		if (document.getElementsByTagName(ZLABEL).item(0) != null)
			mainWindow.tfZLabel.setText(document.getElementsByTagName(ZLABEL).item(0).getTextContent() );
		
		if (document.getElementsByTagName(PRE_PLOT_STRING) != null
				&& document.getElementsByTagName(PRE_PLOT_STRING).item(0) != null)
			mainWindow.prePlotString.setText(document.getElementsByTagName(PRE_PLOT_STRING).item(0).getTextContent() );
		
		
		if (document.getElementsByTagName(XLOGSCALE).item(0) != null)
			mainWindow.cbLogScaleX.setSelected(
					Boolean.parseBoolean(
							document.getElementsByTagName(XLOGSCALE).item(0).getTextContent()
							));
		
		if (document.getElementsByTagName(YLOGSCALE).item(0) != null)
			mainWindow.cbLogScaleX.setSelected(
					Boolean.parseBoolean(
							document.getElementsByTagName(YLOGSCALE).item(0).getTextContent()
							));
		
		if (document.getElementsByTagName(ZLOGSCALE).item(0) != null)
			mainWindow.cbLogScaleX.setSelected(
					Boolean.parseBoolean(
							document.getElementsByTagName(ZLOGSCALE).item(0).getTextContent()
							));
		
		//if (document.getElementsByTagName(PS_FILENAME) != null && document.getElementsByTagName(PS_FILENAME).item(0) != null)
		//	mainWindow.psFileName = document.getElementsByTagName(PS_FILENAME).item(0).getTextContent();
		
		//if (document.getElementsByTagName(SETTINGS_FILENAME) != null && document.getElementsByTagName(SETTINGS_FILENAME).item(0) != null)
		//	mainWindow.settingsFileName = document.getElementsByTagName(SETTINGS_FILENAME).item(0).getTextContent();
		

		//load datasets
		//Element dataset_items = (Element) document.getElementsByTagName(DATASET_ITEMS).item(0);
		//NodeList datasets = document.getElementsByTagName(DATASET_ITEMS);
		NodeList datasets =
		      document.getElementsByTagName(DATASET);
		for (int i = 0; i < datasets.getLength(); i++){
			JGPPlotable ds;
			Element n = (Element) datasets.item(i);
			
				if (n.getElementsByTagName(CLASS).getLength() != 0){
					Class c = Class.forName(n.getElementsByTagName(CLASS).item(0).getTextContent());
					ds = (JGPPlotable) c.newInstance();
					
					if (n.getElementsByTagName(FILENAME).getLength() != 0)
						ds.setFileName(n.getElementsByTagName(FILENAME).item(0).getTextContent());
					if (n.getElementsByTagName(DATASTRING).getLength() != 0)
						ds.setDataString(n.getElementsByTagName(DATASTRING).item(0).getTextContent());
					if (n.getElementsByTagName(DATASET_TITLE).getLength() != 0)
						ds.setTitle(n.getElementsByTagName(DATASET_TITLE).item(0).getTextContent());
					if (n.getElementsByTagName(COLOR).getLength() != 0){
						String sc = n.getElementsByTagName(COLOR).item(0).getTextContent();
						if (sc.trim().equals("auto") ) ds.setColor(null);
						else ds.setColor( JGPColor.parseHexString(sc) );
					}
					if (n.getElementsByTagName(STYLE).getLength() != 0)
						ds.setStyle(JGPStyle.valueOf( n.getElementsByTagName(STYLE).item(0).getTextContent() ) );
					if (n.getElementsByTagName(ADD_STYLE_OPT).getLength() != 0)
						ds.setAddStyleOpt( n.getElementsByTagName(ADD_STYLE_OPT).item(0).getTextContent()  );
					if (n.getElementsByTagName(DO_PLOT).getLength() != 0)
						ds.setDoPlot(Boolean.parseBoolean(n.getElementsByTagName(DO_PLOT).item(0).getTextContent()  ));
					
					if (n.getElementsByTagName(PRE_PROCESS_PROGRAM).getLength() != 0)
						ds.setPreProcessProgram(n.getElementsByTagName(PRE_PROCESS_PROGRAM).item(0).getTextContent());
					
					
					mainWindow.dsTableModel.data.add(ds);	
				}
					

				mainWindow.dsTableModel.fireTableDataChanged();
		}
		//load labels
		NodeList labels =
		      document.getElementsByTagName(LABEL);

		for (int i = 0; i < labels.getLength(); i++){
			Element n = (Element) labels.item(i);
			JGPLabel l = new JGPLabel();
				if (n.getElementsByTagName(TEXT).getLength() != 0)
					l.setText(n.getElementsByTagName(TEXT).item(0).getTextContent());
				if (n.getElementsByTagName(X_POS).getLength() != 0)
					l.setX(Double.parseDouble(n.getElementsByTagName(X_POS).item(0).getTextContent() ) );
				if (n.getElementsByTagName(Y_POS).getLength() != 0)
					l.setY(Double.parseDouble(n.getElementsByTagName(Y_POS).item(0).getTextContent() ) );
				if (n.getElementsByTagName(RELATIVE_POS).getLength() != 0)
					l.setRelativePos(JGPRelativePos.valueOf(n.getElementsByTagName(RELATIVE_POS).item(0).getTextContent() ) );
				if (n.getElementsByTagName(DO_PLOT).getLength() != 0)
					l.setDoPlot(Boolean.parseBoolean(n.getElementsByTagName(DO_PLOT).item(0).getTextContent()  ) );

				mainWindow.labelTableModel.data.add(l);	

					
		}
		
		
		//load labels
		NodeList variables =
		      document.getElementsByTagName(VARIABLE);

		for (int i = 0; i < variables.getLength(); i++){
			Element n = (Element) variables.item(i);
			

					if (n.getElementsByTagName(CLASS).getLength() != 0){
					    Class c = Class.forName(n.getElementsByTagName(CLASS).item(0).getTextContent());
					    JGPVariable v = (JGPVariable) c.newInstance();
					
						if (n.getElementsByTagName(NAME).getLength() != 0)
							v.setName(n.getElementsByTagName(NAME).item(0).getTextContent());
						
						if (n.getElementsByTagName(VALUE).getLength() != 0)
							v.setValue(n.getElementsByTagName(VALUE).item(0).getTextContent());
							
						if (n.getElementsByTagName(ACTIVE).getLength() != 0)
							v.setActive(Boolean.parseBoolean(n.getElementsByTagName(ACTIVE).item(0).getTextContent()  ) );
	
						mainWindow.variableTableModel.variables.add(v);
					}
					
		}//for (int i = 0; i < variables.getLength(); i++)
	}
	
	/**
	 * Generates a XML document which saves all information about the current project.
	 * @return XML document.
	 */
	public Document saveProjectXML(){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document document = null;
        try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();  // Create from whole cloth

			Element root =
			      (Element) document.createElement(JGNUPLOT_CONFIG);
			document.appendChild(root);

			addTextNode(document, root, VERSION, JGP.getVersion());
			addTextNode(document, root, TITLE, mainWindow.tfTitle.getText());

			JGPgnuplot.PlotType plotType;
			if (mainWindow.rb2D.isSelected())
				plotType = JGPgnuplot.PlotType.TWO_DIM;
			else
				plotType = JGPgnuplot.PlotType.THREE_DIM;
			addTextNode(document, root, PLOT_TYPE, plotType.toString());

			addTextNode(document, root, MAX_X, mainWindow.tfMaxX.getText());
			addTextNode(document, root, MIN_X, mainWindow.tfMinX.getText());
			addTextNode(document, root, MAX_Y, mainWindow.tfMaxY.getText());
			addTextNode(document, root, MIN_Y, mainWindow.tfMinY.getText());
			addTextNode(document, root, MAX_Z, mainWindow.tfMaxZ.getText());
			addTextNode(document, root, MIN_Z, mainWindow.tfMinZ.getText());
			addTextNode(document, root, XLABEL, mainWindow.tfXLabel.getText());
			addTextNode(document, root, YLABEL, mainWindow.tfYLabel.getText());
			addTextNode(document, root, ZLABEL, mainWindow.tfYLabel.getText());
			addTextNode(document, root, XLOGSCALE, mainWindow.cbLogScaleX.isSelected() + "");
			addTextNode(document, root, YLOGSCALE, mainWindow.cbLogScaleY.isSelected() + "");
			addTextNode(document, root, ZLOGSCALE, mainWindow.cbLogScaleY.isSelected() + "");
			addTextNode(document, root, PRE_PLOT_STRING, mainWindow.prePlotString.getText() + "");
			//addTextNode(document, root, PS_FILENAME, mainWindow.psFileName);
			//addTextNode(document, root, SETTINGS_FILENAME, mainWindow.settingsFileName);
			
			//add datasets
			Element datasets =
			      (Element) document.createElement(DATASET_ITEMS);

			for (int i = 0; i < mainWindow.dsTableModel.data.size(); i++){
				JGPPlotable ds = mainWindow.dsTableModel.data.get(i);
				Element dataset =
				      (Element) document.createElement(DATASET);
				dataset.setAttribute(ID, i + "");

				addTextNode(document, dataset, CLASS, ds.getClass().getName() + "");
				addTextNode(document, dataset, FILENAME, ds.getFileName() + "");
				addTextNode(document, dataset, DATASTRING, ds.getDataString() + "");
				addTextNode(document, dataset, DATASET_TITLE, ds.getTitle() + "");
				Color c = ds.getColor();
				String sc = "auto";
				if (c != null)  sc = ds.getColor().getHexString();
				addTextNode(document, dataset, COLOR,  sc + "");
				addTextNode(document, dataset, STYLE, ds.getStyle().name() + "");
				addTextNode(document, dataset, ADD_STYLE_OPT, ds.getAddStyleOpt() + "");
				addTextNode(document, dataset, DO_PLOT, ds.getDoPlot() + "");
				addTextNode(document, dataset, PRE_PROCESS_PROGRAM, ds.getPreProcessProgram()+ "");


				datasets.appendChild(dataset);
			}
			root.appendChild(datasets);

			//add labels
			Element labels =
			      (Element) document.createElement(LABEL_ITEMS);

			for (int i = 0; i < mainWindow.labelTableModel.data.size(); i++){
				JGPLabel l = mainWindow.labelTableModel.data.get(i);
				Element label =
				      (Element) document.createElement(LABEL);
				label.setAttribute(ID , i + "");

				addTextNode(document, label, TEXT , l.getText() + "");
				addTextNode(document, label, X_POS , l.getX() + "");
				addTextNode(document, label, Y_POS , l.getY() + "");
				addTextNode(document, label, RELATIVE_POS , l.getRelativePos() + "");
				addTextNode(document, label, DO_PLOT , l.getDoPlot() + "");

				labels.appendChild(label);
			}
			root.appendChild(labels);

			//add labels
			Element variables =
			      (Element) document.createElement(VARIABLE_ITEMS);

			for (int i = 0; i < mainWindow.variableTableModel.variables.size(); i++){
				JGPVariable v = mainWindow.variableTableModel.variables.get(i);
				Element variable =
				      (Element) document.createElement(VARIABLE);
				variable.setAttribute(ID , i + "");

				addTextNode(document, variable, CLASS, v.getClass().getName() + "");
				addTextNode(document, variable, NAME , v.getName() + "");
				addTextNode(document, variable, VALUE , v.getValue() + "");
				addTextNode(document, variable, ACTIVE , v.isActive() + "");

				variables.appendChild(variable);
			}
			root.appendChild(variables);
			
        } catch (ParserConfigurationException pce) {
            // Parser with specified options can't be built
            pce.printStackTrace();

        }
		
		return document;

	}
	
	/**
	 * Saves the current project to a file.
	 * 
	 * @param xmlFile Path and filename for the project.
	 */
	public void writeProjectFile(String xmlFile){
			try {
					FileOutputStream fos = new FileOutputStream(xmlFile);
					//	      XERCES 1 or 2 additionnal classes.
				    OutputFormat of = new OutputFormat("XML","ISO-8859-1",true);
				    of.setIndent(1);
				    of.setIndenting(true);
				    //of.setDoctype(null,"users.dtd");
				    XMLSerializer serializer = new XMLSerializer(fos,of);
				    //	      As a DOM Serializer
				    serializer.asDOMSerializer();
				    serializer.serialize( saveProjectXML().getDocumentElement() );
				    fos.close();
				    
			} catch (FileNotFoundException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
	}
	
	public String saveProjectString() throws IOException{
	    OutputFormat of = new OutputFormat("XML","ISO-8859-1",true);
	    of.setIndent(1);
	    of.setIndenting(true);

	    StringWriter stringWriter = new StringWriter();
		XMLSerializer serializer = new XMLSerializer(stringWriter,of);
	    //	      As a DOM Serializer
	    serializer.asDOMSerializer();
	    serializer.serialize( saveProjectXML().getDocumentElement() );
	    
	    stringWriter.flush();
	    return stringWriter.getBuffer().toString();
		
	}




}

