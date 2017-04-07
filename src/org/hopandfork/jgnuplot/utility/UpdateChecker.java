package org.hopandfork.jgnuplot.utility;

import java.io.File;
import java.io.IOException;

import org.hopandfork.jgnuplot.JGP;
import org.hopandfork.jgnuplot.model.DataFile;
import org.hopandfork.jgnuplot.model.PlottableData;

public class UpdateChecker implements Runnable {
	JGP owner;

	public boolean checkForUpdate;

	public UpdateChecker(JGP owner) {
		this.owner = owner;
	}

	public void run() {
		checkForUpdate = true;

		while (checkForUpdate) {
			// check wether one of the files has changed
			for (int i = 0; i < owner.dsTableModel.data.size(); i++) {
				if (owner.dsTableModel.data.get(i).getClass().equals(DataFile.class)
						&& ((PlottableData) owner.dsTableModel.data.get(i)).isEnabled()) {
					DataFile ds = (DataFile) owner.dsTableModel.data.get(i);
					// if dataset source file has chenged, replot
					File f = new File(ds.getFileName());
					if (f.lastModified() > ds.getLastChanged()) {
						try {
							owner.acPlot();
							ds.setLastChanged(f.lastModified());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}