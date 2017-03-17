package jgp.gui;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class JGPXMLManager {

	public void addTextNode(Document document, Element parent, String nodeName, String value) {
		Element e = document.createElement(nodeName);
		//Text n = document.createTextNode(value);
		e.setTextContent(value);
		//e.appendChild(n);
		parent.appendChild(e);
	}

}
