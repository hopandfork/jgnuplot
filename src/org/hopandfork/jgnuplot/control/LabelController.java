package org.hopandfork.jgnuplot.control;

import org.apache.log4j.Logger;
import org.hopandfork.jgnuplot.model.Label;
import org.hopandfork.jgnuplot.model.Plot;
import org.hopandfork.jgnuplot.model.Project;
import org.hopandfork.jgnuplot.model.RelativePosition;

import java.util.List;
import java.util.Observable;


public class LabelController extends Observable {


	static private final Logger LOG = Logger.getLogger(LabelController.class);

	public Label addLabel(String text, double x, double y, RelativePosition relativePosition) {
		Plot plot = Project.currentProject().getPlot();

		Label label = new Label(text, x, y, relativePosition);
		plot.addLabel(label);

		notifyObservers(label);
		return label;
	}

	public Label updateLabel(Label label, String text, double x, double y, RelativePosition relativePosition) {
		label.setText(text);
		label.setX(x);
		label.setY(y);
		label.setRelativePos(relativePosition);

		notifyObservers(label);
		return label;
	}

	public List<Label> getLabels() {
		Plot plot = Project.currentProject().getPlot();
		return plot.getLabels();
	}

	public void delete(Label label) {
		Plot plot = Project.currentProject().getPlot();
		plot.deleteLabel(label);
		notifyObservers(label);
	}

	public void deleteAll() {
		Plot plot = Project.currentProject().getPlot();
		plot.deleteAllLabels();
		notifyObservers(null);
	}

	@Override
	public void notifyObservers(Object o) {
		this.setChanged();
		super.notifyObservers(o);
		this.clearChanged();
	}


}
