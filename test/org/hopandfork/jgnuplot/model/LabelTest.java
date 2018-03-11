package org.hopandfork.jgnuplot.model;

import org.junit.Assert;
import org.junit.Test;

public class LabelTest {

	@Test
	public void testPlotScript() {
		Label label = new Label();
		label.setEnabled(true);
		label.setText("test");
		label.setX(1.0);
		label.setY(1.0);
		label.setRelativePos(RelativePosition.FIRST);
		
		Assert.assertEquals("set label \"test\" at first 1.0,1.0\n", label.toPlotString());
	}

}
