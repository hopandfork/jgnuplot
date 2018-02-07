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

import java.io.IOException;
import java.util.List;
import java.util.Observable;

import org.apache.log4j.Logger;
import org.hopandfork.jgnuplot.model.Constant;
import org.hopandfork.jgnuplot.model.Function;
import org.hopandfork.jgnuplot.model.Plot;
import org.hopandfork.jgnuplot.model.Project;

public class ConstantController extends Observable{

	static private Logger LOG = Logger.getLogger(ConstantController.class);

	public List<Constant> getConstants(Function function){
		return function.getConstants();
	}
	
	/**
	 * This method allows to create a new Variable.
	 * 
	 * @param function
	 *            is the function target for the new Variable.
	 * @param name
	 *            of the Variable.
	 * @param value
	 *            of the Variable.
	 * 
	 * @return the new Variable.
	 * @throws IOException
	 *             if name or value is null an exception is thrown.
	 */
	public Constant addConstant(Function function, String name, String value) throws IOException {
		Constant constant = new Constant();

		if (name != null && value != null) {
			constant.setName(name);
			constant.setValue(value);

			function.addConstant(constant);
		} else {
			throw new IOException("Name or/and Value are null, please set a correct value");
		}
		
		notifyObservers(function);
		
		return constant;
	}
	
	public void deleteConstant(Function function, Constant constant) {
		function.rmConstant(constant);
		notifyObservers(function);
	}
	
	@Override
	public void notifyObservers(Object o) {
		this.setChanged();
		super.notifyObservers(o);
		this.clearChanged();
	}
}
