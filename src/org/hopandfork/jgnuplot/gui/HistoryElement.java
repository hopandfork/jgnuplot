package org.hopandfork.jgnuplot.gui;

import org.w3c.dom.Document;

public class HistoryElement {
	public String description;
	public Document state;

	public HistoryElement(String description, Document state) {
		this.description = description;
		this.state = state;
	}
}