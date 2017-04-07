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

package org.hopandfork.jgnuplot.runtime;

/**
 * Interface for plugins which can pre-process data files.
 */
public interface PreProcessPlugin {
	
	/**
	 * Returns the description of the plugin.
	 * @return Description of the plugin.
	 */
	public String getDescription();
	
	/**
	 * Returns the name of the plugin.
	 * @return Name of the plugin.
	 */
	public String getName();
	
	/**
	 * Pre-process specified file.
	 * @param inFileName Name of the file to pre-process.
	 * @param outFileName Output file name.
	 */
	public void preProcess(String inFileName, String outFileName);
}
