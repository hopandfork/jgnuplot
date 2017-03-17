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


import java.io.File;
import java.util.ArrayList;

import javax.swing.JComboBox;

import jgp.JGPPreProcessPlugin;

public class JGPPluginComboBox extends JComboBox {
	boolean DEBUG = true;
	
	
	ArrayList<JGPPreProcessPlugin> plugins;
	
	public void loadPlugins(){
		if (DEBUG) System.out.println("Loading plugins...");
		
		String list[], result;
		
	    File file = new File("./plugins");
	    
	    if (file.isDirectory()) {
			if (DEBUG) System.out.println("Examining plugins directory...");

			list = file.list();
		    
	    	for (int i = 0; i < list.length; i++){
			    File classFile = new File(list[i]);
			    String classFileName = classFile.getName();
				if (DEBUG) System.out.println("Examining file " + classFileName + " ...");

				//chop off .class suffix
			    String className = classFileName.substring(0, classFileName.length() - 5);
			    try {
					if (DEBUG) System.out.println("Adding plugin " + className + " ...");
					if (DEBUG) System.out.println("Loading class " + className + " ...");
					Class c = Class.forName(className);
					if (DEBUG) System.out.println("Loaded class " + className + " ...");
					JGPPreProcessPlugin plugin = 
						(JGPPreProcessPlugin) c.newInstance();
					//plugins.add(plugin);
					this.addItem(plugin.getName());
				} catch (ClassNotFoundException e) {
					if (DEBUG) System.out.println(e.getMessage());
				} catch (InstantiationException e) {
					if (DEBUG) System.out.println(e.getMessage());
				} catch (IllegalAccessException e) {
					if (DEBUG) System.out.println(e.getMessage());
				}
			    
		    	
		    	
		    }
	    }
	    

	}
	
	public JGPPluginComboBox() {
		super();
		// TODO Auto-generated constructor stub
	}

}
