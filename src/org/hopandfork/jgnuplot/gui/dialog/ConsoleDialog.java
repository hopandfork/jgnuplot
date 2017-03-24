package org.hopandfork.jgnuplot.gui.dialog;

public class ConsoleDialog extends TextDialog{
	
	public ConsoleDialog(){
		super();
		this.setTitle("JGNUplot console");
		this.setLocationByPlatform(true);
		textArea.setEditable(true);
		setResizable(true);
	}
	
	public static ConsoleDialog showConsoleDialog(){
		ConsoleDialog cd = new ConsoleDialog();
		cd.setVisible(true);
		return cd;
	}

}
