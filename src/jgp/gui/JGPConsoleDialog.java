package jgp.gui;

public class JGPConsoleDialog extends JGPTextDialog{
	
	JGPConsoleDialog(){
		super();
		this.setTitle("JGNUplot console");
		this.setLocationByPlatform(true);
		textArea.setEditable(true);
		setResizable(true);
	}
	
	public static JGPConsoleDialog showConsoleDialog(){
		JGPConsoleDialog cd = new JGPConsoleDialog();
		cd.setVisible(true);
		return cd;
	}

}
