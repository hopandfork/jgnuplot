package org.hopandfork.jgnuplot.model;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class DataSelection2DTest {

	@Test(expected=IOException.class)
	public void withoutLabelErrorXTest() throws IOException {
		new DataSelection2D(0, 2);
	}
	
	@Test(expected=IOException.class)
	public void withoutLabelErrorYTest() throws IOException {
		new DataSelection2D(2, 0);
	}
	
	@Test
	public void withoutLabelTest() throws IOException {
		new DataSelection2D(2, 3);
	}
	
	@Test
	public void setLabelTest() throws IOException {
		DataSelection2D dataSelection2D = new DataSelection2D(2, 3);
		dataSelection2D.setLabels(2);
		dataSelection2D.setLabelled(false);
		
		assertEquals(false, dataSelection2D.isLabelled());
		assertEquals(2, dataSelection2D.getLabels());
	}
	
	@Test(expected=IOException.class)
	public void withLabelErrorTest() throws IOException {
		new DataSelection2D(1, 2, 0);
	}

	@Test
	public void withLabelTest() throws IOException {
		DataSelection2D dataSelection2D = new DataSelection2D(1, 2, 3);
		assertEquals(true, dataSelection2D.isLabelled());
		assertEquals(3, dataSelection2D.getLabels());
	}
	
	@Test
	public void toPlotStringTest() throws IOException {
		DataSelection2D dataSelection2D = new DataSelection2D(1, 2, 3);
		assertEquals("($1*1.000000+0.000000):($2*1.000000+0.000000):3", dataSelection2D.toPlotString());
	}
}
