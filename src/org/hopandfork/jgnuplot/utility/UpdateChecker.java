package org.hopandfork.jgnuplot.utility;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.hopandfork.jgnuplot.JGP;
import org.hopandfork.jgnuplot.control.PlottableDataController;
import org.hopandfork.jgnuplot.model.DataFile;
import org.hopandfork.jgnuplot.model.PlottableData;

@Deprecated
public class UpdateChecker implements Runnable {
	private JGP owner;

	public boolean isCheckForUpdate() {
		return checkForUpdate;
	}

	public void setCheckForUpdate(boolean checkForUpdate) {
		this.checkForUpdate = checkForUpdate;
	}

	private boolean checkForUpdate;
	private PlottableDataController plottableDataController;

	public UpdateChecker(JGP owner, PlottableDataController plottableDataController) {
		this.owner = owner;
		this.plottableDataController = plottableDataController;
	}

	public void run() {
		checkForUpdate = true;

		while (checkForUpdate) {
			// check wether one of the files has changed
			List<PlottableData> allPlottableData = plottableDataController.getPlottableData();
			for (PlottableData plottableData : allPlottableData) {
				if (plottableData instanceof DataFile && plottableData.isEnabled()) {
					DataFile ds = (DataFile) plottableData;
					// if dataset source file has changed, replot
					File f = new File(ds.getFileName());
					if (f.lastModified() > ds.getLastChanged()) {
						try {
							owner.acPlot();
							ds.setLastChanged(f.lastModified());
						} catch (IOException e) {
							e.printStackTrace();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}