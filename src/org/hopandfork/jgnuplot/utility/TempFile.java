package org.hopandfork.jgnuplot.utility;

import java.io.File;

@Deprecated // TODO use File.createTempFile()
public class TempFile {

	private static int tmpFilnameCounter = 0;
	private static final String TEMP_DIR = "/tmp";
	
	/**
	 * Generates a filename that can be use for temporary files. The file will
	 * be located in the TEMP_DIR directory.
	 * 
	 * @return
	 */
	public static String getTempFileName() {
		String s = TEMP_DIR + "/jGNUplot";
		String fn = s + tmpFilnameCounter + ".tmp";

		while (new File(fn).exists()) {
			tmpFilnameCounter++;
			fn = s + tmpFilnameCounter + ".tmp";
		}

		return fn;
	}

}
