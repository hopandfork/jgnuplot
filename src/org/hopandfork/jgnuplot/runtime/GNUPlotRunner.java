package org.hopandfork.jgnuplot.runtime;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GNUPlotRunner   extends ProcessRunner{

	private String gpPlotString;

	private String PLOTFILENAME = "work.gnuplot";

	public void run(){
		try {
			PrintWriter logWriter = new PrintWriter(new BufferedWriter(new FileWriter(PLOTFILENAME,
					false)));

			logWriter.write(gpPlotString);
			logWriter.flush();
			logWriter.close();

			//System.out.println(GNUplot.GNUPLOT_CMD + PLOTFILENAME);
			cmdExec(GnuplotExecutor.GNUPLOT_CMD + PLOTFILENAME);

			System.out.println("GNUplot exited with value: " + p.exitValue()); 
			System.out.println("GNUplot has finished.");
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String getGpPlotString() {
		return gpPlotString;
	}

	public void setGpPlotString(String gpPlotString) {
		this.gpPlotString = gpPlotString;
	}

}