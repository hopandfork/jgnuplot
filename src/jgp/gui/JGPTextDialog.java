package jgp.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public abstract class JGPTextDialog  extends JGPDialog  implements ActionListener{

	protected JTextArea textArea;

	public JGPTextDialog() throws HeadlessException {
		setPreferredSize(new Dimension(480, 360));
		this.setResizable(false);
		add(createMainPanel());
		pack();
		
	
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("ok")){
			this.setVisible(false);
		}
	}

	protected JPanel createMainPanel() {
		// Create the panel.
		JGPPanel jp = new JGPPanel();
		// Set the default panel layout.
		GridBagLayout gbl = new GridBagLayout();
		jp.setLayout(gbl);
		
		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		
	    JScrollPane scrollPane = new JScrollPane(textArea);
	    //scrollPane.setPreferredSize(new Dimension(400, 300));
	
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.gridx = 0;
		gbc2.gridy = 0;
		//gbc2.gridwidth = 4;
		gbc2.weightx = 1.0;
		gbc2.weighty = 1.0;
		gbc2.fill = GridBagConstraints.BOTH;
	
		JButton bOk = new JButton("Ok");
		bOk.setActionCommand("ok");
		bOk.addActionListener(this);
		
		//this works, the other version does not...don't know why
		jp.add(scrollPane, gbc2);
		//jp.add(scrollPane, 0, 0, 2, 1, GridBagConstraints.BOTH, GridBagConstraints.WEST);
		jp.add(bOk, 0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.EAST);
		return jp;			
	}
	
	public String getText(){
		return textArea.getText();
	}

	public void setText(String t) {
		textArea.setText(t);
	}

}
