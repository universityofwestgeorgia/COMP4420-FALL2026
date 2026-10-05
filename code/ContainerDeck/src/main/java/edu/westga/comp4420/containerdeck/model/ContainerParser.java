package edu.westga.comp4420.containerdeck.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Converts formatted Docker CLI output into container model objects.
 *
 * @author Leo Gregori
 * @version Fall 2026
 */
public final class ContainerParser {
	private static final int EXPECTED_COLUMN_COUNT = 5;

	/**
	 * Parses output formatted as identifier, name, image, status, and state,
	 * separated by tab characters with one container per line.
	 *
	 * @precondition output != null
	 * @postcondition the returned list contains one container for each line
	 *
	 * @param output the formatted output from the Docker CLI
	 * @return an unmodifiable list of parsed containers
	 */
	public List<Container> parse(String output) {
		if (output == null) {
			throw new IllegalArgumentException("Docker output must not be null.");
		}
		if (output.isBlank()) {
			return List.of();
		}

		List<Container> containers = new ArrayList<>();
		for (String line : output.split("\\R")) {
			containers.add(this.parseLine(line));
		}
		return Collections.unmodifiableList(containers);
	}

	private Container parseLine(String line) {
		String[] columns = line.split("\\t", -1);
		if (columns.length != EXPECTED_COLUMN_COUNT) {
			throw new IllegalArgumentException("Docker output must contain five tab-separated columns per line.");
		}
		return new Container(columns[0], columns[1], columns[2], columns[3], columns[4]);
	}
}
