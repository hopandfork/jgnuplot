package org.hopandfork.jgnuplot.utility;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Parses the first lines of a data file to identify its columns.
 */
public class FileColumnsParser {

	/** Data file name. */
	private String filename;

	/** String containing the allowed column separators. */
	private String separators = " \t;";

	/** Number of lines to parse. */
	private static final int LINES = 10;


	public FileColumnsParser (String filename)
	{
		this.filename = filename;
	}

	public FileColumnsParser (String filename, char separators[])
	{
		this(filename);
		initSeparatorsString(separators);
	}

	private void initSeparatorsString(char separators[])
	{
		StringBuilder sb = new StringBuilder();
		for (char c : separators) {
			sb.append(c);
		}
		this.separators = sb.toString();
	}


	/**
	 * Parses the data file and returns its first lines split in columns.
	 * @return First lines of the file split in columns.
	 */
	public List<String[]> parse() throws IOException {
		File file = new File(filename);
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		int parsedLines = 0;
		List<String[]> rows = new ArrayList<String[]>(LINES);

		while (parsedLines < LINES) {
			String line = bufferedReader.readLine();
			if (line == null)
				break;

			line = line.trim();
			if (line.length() < 1 || line.startsWith("#"))
				continue;

			rows.add(split(line));
			parsedLines++;
		}

		bufferedReader.close();
		return rows;
	}

	/**
	 * Splits a line into columns.
	 * @param line Line to split.
	 * @return Array of column entries.
	 */
	private String[] split (String line) {
		StringTokenizer st = new StringTokenizer(line, separators);
		String row[] = new String[st.countTokens()];

		int i = 0;
		while (st.hasMoreTokens()) {
			row[i++] = st.nextToken();
		}

		return row;
	}

}
