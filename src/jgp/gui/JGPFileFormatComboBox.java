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


import javax.swing.JComboBox;

import jgp.JGPFileFormat;

public class JGPFileFormatComboBox extends JComboBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JGPFileFormatComboBox() {
		super();

			
	        for (JGPFileFormat ff : JGPFileFormat.values())
	        	this.addItem(ff);
	        this.setEditable(true);
	            
	}

}
//VWS
//aed512
//aed767
//aifm
//amiga
//apollo
//aqua
//atari
//be
//bitgraph
//cgi
//cgm
//corel
//debug
//dospc
//dumb
//dxf
//dxy800a
//eepic
//egalib
//egamono
//emf
//emtex
//emxvesa
//emxvga
//epslatex
//epson-180dpi
//epson-60dpi
//epson-lx800
//excl
//fig
//ggi
//gif
//gnugraph
//gpic
//gpr
//grass
//hcgi
//hercules
//hp2623a
//hp2648
//hp500c
//hpdj
//hpgl
//hpljii
//hppj
//imagen
//iris4d
//jpeg
//kc-tek40xx
//km-tek40xx
//kyo
//latex
//linux
//macintosh
//mf
//mgr
//mif
//mp
//mtos
//nec-cp6
//next
//okidata
//openstep
//pbm
//pcl5
//pdf
//pm
//png
//postscript
//prescribe
//pslatex
//pstex
//pstricks
//qms
//regis
//rgip
//selanar
//ssvgalib
//starc
//sun
//svg
//svga
//svgalib
//table
//tandy-60dpi
//tek40xx
//tek410x
//texdraw
//tgif
//tkcanvas
//tpic
//uniplex
//unixpc
//vdi
//vgagl
//vgal
//vgalib
//vgamono
//vttek
//vx384
//windows
//x11
//xlib