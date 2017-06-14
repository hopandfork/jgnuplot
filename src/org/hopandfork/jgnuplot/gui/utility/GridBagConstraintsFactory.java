package org.hopandfork.jgnuplot.gui.utility;

import java.awt.*;

public class GridBagConstraintsFactory {

	static public GridBagConstraints create (int gridx, int gridy, int gridwidth, int gridheight, int weightx, int weighty, int fill, int anchor) {
		GridBagConstraints gbc = create(gridx, gridy, gridwidth, gridheight, weightx, weighty, fill);
		gbc.anchor = anchor;
		return gbc;
	}

	static public GridBagConstraints create (int gridx, int gridy, int gridwidth, int gridheight, int weightx, int weighty, int fill) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.gridwidth = gridwidth;
		gbc.gridheight = gridheight;
		gbc.fill = fill;
		gbc.weighty = weighty;
		gbc.weightx = weightx;
		gbc.insets = new Insets(2,2,2,2);

		return gbc;
	}
}
