package edu.westga.comp4420.containerdeck.test.model.docker_command_runner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import edu.westga.comp4420.containerdeck.model.DockerCommandRunner;

class TestStopContainer {

    @Test
    void testValidIdentifier() {
        DockerCommandRunner runner = new DockerCommandRunner(
                List.of("true")
        );

        assertDoesNotThrow(() -> runner.stopContainer("abc123"));
    }

    @Test
    void testNullIdentifier() {
        DockerCommandRunner runner = new DockerCommandRunner(
                List.of("true")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> runner.stopContainer(null)
        );
    }

    @Test
    void testBlankIdentifier() {
        DockerCommandRunner runner = new DockerCommandRunner(
                List.of("true")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> runner.stopContainer(" ")
        );
    }
}
