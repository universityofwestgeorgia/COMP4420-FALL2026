package edu.westga.comp4420.containerdeck.test.model.container_parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import edu.westga.comp4420.containerdeck.model.Container;
import edu.westga.comp4420.containerdeck.model.ContainerParser;

class TestParse {
	private final ContainerParser parser = new ContainerParser();

	@Test
	void testTwoValidContainers() {
		String output = "abc123\tweb\tnginx:latest\tUp 2 hours\trunning\n"
				+ "def456\tdatabase\tpostgres:16\tExited (0) 1 hour ago\texited";

		List<Container> containers = this.parser.parse(output);

		assertEquals(2, containers.size());
		assertEquals("web", containers.get(0).getName());
		assertEquals("running", containers.get(0).getState());
		assertEquals("database", containers.get(1).getName());
		assertEquals("Exited (0) 1 hour ago", containers.get(1).getStatus());
	}

	@Test
	void testBlankOutputReturnsEmptyList() {
		assertTrue(this.parser.parse(" \n\t").isEmpty());
	}

	@Test
	void testReturnedListIsUnmodifiable() {
		List<Container> containers = this.parser.parse("abc123\tweb\tnginx\tUp\trunning");

		assertThrows(UnsupportedOperationException.class, () -> containers.clear());
	}

	@Test
	void testNullOutput() {
		assertThrows(IllegalArgumentException.class, () -> this.parser.parse(null));
	}

	@Test
	void testMissingColumn() {
		assertThrows(IllegalArgumentException.class,
				() -> this.parser.parse("abc123\tweb\tnginx\trunning"));
	}

	@Test
	void testBlankColumn() {
		assertThrows(IllegalArgumentException.class,
				() -> this.parser.parse("abc123\t\tnginx\tUp\trunning"));
	}
}
