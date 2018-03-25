package org.hopandfork.jgnuplot.model;

import static org.junit.Assert.*;

import java.io.IOException;

import org.hopandfork.jgnuplot.model.style.GnuplotColor;
import org.hopandfork.jgnuplot.model.style.PlottingStyle;
import org.junit.Test;

public class DataFileTest {

	@Test
	public void testPlotScript() {
		String plotString = "'test' using ($1*1.000000+0.000000):($2*1.000000+0.000000)  with dots  -lt 3  lc rgb '#333333' title \"test \" ";
		
		DataFile dataFile = new DataFile();
		dataFile.setFileName("test");
		dataFile.setColor(new GnuplotColor((float)0.2, (float)0.2, (float)0.2));
		try {
			dataFile.setDataSelection(new DataSelection2D(1, 2));
		} catch (IOException e) {
			e.printStackTrace();
		}
		dataFile.setTitle("test");
		dataFile.setEnabled(true);
		dataFile.setStyle(PlottingStyle.dots);
		dataFile.setAddStyleOpt("-lt 3");
		
		assertEquals(plotString, dataFile.toPlotString());
	}

}
