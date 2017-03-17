package jgp.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class JGPSettingsManager extends JGPXMLManager {
	
	public static final String RECENT_PROJECTS = "recent_projects";

	public static final String PROJECT = "project";

	public static final String FILENAME = "filename";
	
	public static final String JGNUPLOT_SETTINGS = "jgnuplot_settings";
	
	public static final String VERSION = "version";
	
	public  ArrayList<String> projectFiles;
	
	private  JGP jgnuplot;

	public JGPSettingsManager(JGP jgnuplot) {
		this.jgnuplot = jgnuplot;
		projectFiles = new ArrayList<String>();
	}


	public void readSettingsXML(String xmlFile) throws ParserConfigurationException, SAXException, IOException{
			projectFiles = new ArrayList<String>();
			
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        Document document = null;
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(new File(xmlFile));
			
			NodeList recent_projects =
			      document.getElementsByTagName(PROJECT);

			for (int i = 0; i < recent_projects.getLength(); i++){
				
				Element n = (Element) recent_projects.item(i);

										
				if (n.getElementsByTagName(FILENAME).getLength() != 0){
					projectFiles.add( n.getElementsByTagName(FILENAME).item(0).getTextContent());
				}
 					

			}
			
			
	}
	
	
	public void writeSettingsXML(String xmlFile){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document document = null;
        try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();  // Create from whole cloth

			Element root =
			      (Element) document.createElement(JGNUPLOT_SETTINGS);
			document.appendChild(root);

			addTextNode(document, root, VERSION, JGP.getVersion());
			
			//add datasets
			Element recent_projects =
			      (Element) document.createElement(RECENT_PROJECTS);

			for (int i = 0; i < projectFiles.size(); i++){

				Element project =
				      (Element) document.createElement(PROJECT);


				addTextNode(document, project, FILENAME, projectFiles.get(i) + "");



				recent_projects.appendChild(project);
			}
			root.appendChild(recent_projects);

		
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
				    serializer.serialize( document.getDocumentElement() );
				    fos.close();
			} catch (FileNotFoundException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}


        } catch (ParserConfigurationException pce) {
            // Parser with specified options can't be built
            pce.printStackTrace();

        }


	}

}
