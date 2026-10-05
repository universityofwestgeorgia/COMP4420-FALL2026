package edu.westga.comp4420.containerdeck.test.model.container;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import edu.westga.comp4420.containerdeck.model.Container;

class TestConstructor {
	private static final String IDENTIFIER = "a1b2c3d4e5f6";
	private static final String NAME = "web-server";
	private static final String IMAGE = "nginx:latest";
	private static final String STATUS = "Up 2 hours";
	private static final String STATE = "running";

	@Test
	void testValidContainer() {
		Container container = new Container(IDENTIFIER, NAME, IMAGE, STATUS, STATE);

		assertAll(
				() -> assertEquals(IDENTIFIER, container.getIdentifier()),
				() -> assertEquals(NAME, container.getName()),
				() -> assertEquals(IMAGE, container.getImage()),
				() -> assertEquals(STATUS, container.getStatus()),
				() -> assertEquals(STATE, container.getState()));
	}

	@Test
	void testNullIdentifier() {
		assertThrows(IllegalArgumentException.class,
				() -> new Container(null, NAME, IMAGE, STATUS, STATE));
	}

	@ParameterizedTest
	@ValueSource(strings = {"", " ", "\t"})
	void testBlankIdentifier(String identifier) {
		assertThrows(IllegalArgumentException.class,
				() -> new Container(identifier, NAME, IMAGE, STATUS, STATE));
	}

	@Test
	void testInvalidName() {
		assertThrows(IllegalArgumentException.class,
				() -> new Container(IDENTIFIER, null, IMAGE, STATUS, STATE));
	}

	@Test
	void testInvalidImage() {
		assertThrows(IllegalArgumentException.class,
				() -> new Container(IDENTIFIER, NAME, "", STATUS, STATE));
	}

	@Test
	void testInvalidStatus() {
		assertThrows(IllegalArgumentException.class,
				() -> new Container(IDENTIFIER, NAME, IMAGE, " ", STATE));
	}

	@Test
	void testInvalidState() {
		assertThrows(IllegalArgumentException.class,
				() -> new Container(IDENTIFIER, NAME, IMAGE, STATUS, null));
	}
}
