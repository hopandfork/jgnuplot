/*
 * Copyright 2006, 2017 Maximilian H Fabricius, Hop and Fork.
 * 
 * This file is part of JGNUplot.
 * 
 * JGNUplot is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * JGNUplot is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JGNUplot.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.hopandfork.jgnuplot.gui.combobox;


import javax.swing.JComboBox;

import org.hopandfork.jgnuplot.model.style.PlottingStyle;


public class StyleComboBox extends JComboBox<PlottingStyle>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4300054880948406415L;

	public StyleComboBox(){
		
        for (PlottingStyle s : PlottingStyle.values())
        	this.addItem(s);
            
        this.setEditable(true);
		
	}
}
