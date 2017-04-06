package org.hopandfork.jgnuplot.management.gnuplot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Runnable used to asynchronously run commands.
 */
public abstract class ProcessRunner implements Runnable {

	/** JGPPrintWriter for logging. */
	protected JGPPrintWriter out = null;

	/** Launched process. */
	protected static Process p = null;
	
	public ProcessRunner (JGPPrintWriter printWriter) {
		this.out = printWriter;
	}

	/**
	 * Executes a command on a shell.
	 * @param cmdline CLI command to run.
	 */
	public void cmdExec(String cmdline) {
		try {
			String line = null;
			String errline = null;

			/* If there is an older process still running, destroy it. */
			synchronized (this) {
				if (p != null)
					p.destroy();
			}

			/* Runs given command. */
			p = Runtime.getRuntime().exec(cmdline);

			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader err = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			// TODO following code is a bit weird!
			try {
				while ((line = input.readLine()) != null || (errline = err.readLine()) != null) {
					if (line != null && !line.trim().equals("") && out != null)
						out.println(line);
					if (errline != null && !errline.trim().equals("") && out != null)
						out.printerrln(errline);
				}
			} catch (IOException e) {
				System.out.println("readLine() failed");
			}
			
			/* Waits for process to terminate. */
			p.waitFor();

			input.close();
			err.close();
			
			synchronized (this) {
				p = null;
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

}