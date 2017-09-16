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
