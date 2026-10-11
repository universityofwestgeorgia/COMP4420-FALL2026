package edu.westga.comp4420.containerdeck.test.model.docker_command_runner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.westga.comp4420.containerdeck.model.DockerCommandRunner;
import edu.westga.comp4420.containerdeck.model.Container;

class TestListContainers {

    @Test
    void testSuccessfulCommand() throws IOException, InterruptedException {
        DockerCommandRunner runner = new DockerCommandRunner(
                List.of("java", "-version")
        );

        String output = runner.listContainers();

        assertTrue(output.contains("version"));
    }

    @Test
    void testFailedCommand() {
        DockerCommandRunner runner = new DockerCommandRunner(
                List.of("java", "--invalid-container-deck-option")
        );

        IOException exception = assertThrows(
                IOException.class,
                () -> runner.listContainers()
        );

        assertTrue(exception.getMessage().startsWith("Docker command failed:"));
    }

    @Test
    void testGetContainersParsesCommandOutput()
            throws IOException, InterruptedException {
        DockerCommandRunner runner = new DockerCommandRunner(
                List.of(
                        "printf",
                        "abc123\tweb\tnginx:latest\tUp 2 hours\trunning"
                )
        );

        List<Container> containers = runner.getContainers();

        assertEquals(1, containers.size());
        assertEquals("abc123", containers.get(0).getIdentifier());
        assertEquals("web", containers.get(0).getName());
        assertEquals("running", containers.get(0).getState());
    }

    @Test
    void testDefaultConstructor() {
        DockerCommandRunner runner = new DockerCommandRunner();

        assertNotNull(runner);
    }
}
