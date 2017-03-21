package org.hopandfork.jgnuplot.runtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class ProcessRunner  implements Runnable {

	private JGPPrintWriter out = null;


	protected GnuplotExecutor owner;
	protected static Process p;


	public void cmdExec(String cmdline) {
		try {
			String line = null;
			String errline = null;

			//if there is an older process still running, destroy it.
			if (p != null) p.destroy();

			p = Runtime.getRuntime().exec(cmdline);
			//InputStreamReader inputStreamReader;
			BufferedReader input =
				new BufferedReader
				(new InputStreamReader(p.getInputStream() ));

			//InputStreamReader errInputStreamReader;

			BufferedReader err =
				new BufferedReader
				(new InputStreamReader(p.getErrorStream() ));

			try {
				while ( (line = input.readLine()) != null || (errline = err.readLine()) != null) {
					if (line != null && !line.trim().equals("") && out != null) 
						out.println(line);
					if (errline != null &&  !errline.trim().equals("")  && out != null)  
						out.printerrln(errline);
				}
			} catch (IOException e) {
				System.out.println("readLine() failed");
			}
			p.waitFor();
			input.close();
			err.close();
		}
		catch (Exception err) {
			err.printStackTrace();
		}
	}



	public JGPPrintWriter getOut() {
		return out;
	}

	public void setOut(JGPPrintWriter out) {
		this.out = out;
	}



}