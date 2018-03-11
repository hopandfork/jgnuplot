package org.hopandfork.jgnuplot.model;

import org.junit.Assert;
import org.junit.Test;

public class ConstantTest {

	@Test
	public void testPlotString() {
		Constant constant = new Constant();
		constant.setName("c1");
		constant.setValue("0.03");
		
		Assert.assertEquals(constant.getPlotString(), "c1=0.03");
		Assert.assertEquals(constant.toPlotString(), "c1=0.03");
	}

}
