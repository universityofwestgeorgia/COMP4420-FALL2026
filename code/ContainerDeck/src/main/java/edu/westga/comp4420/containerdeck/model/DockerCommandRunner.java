package edu.westga.comp4420.containerdeck.model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Executes Docker commands needed by Container Deck.
 *
 * @author Leo Gregori
 * @version Fall 2026
 */
public class DockerCommandRunner {
    private final List<String> command;

    /**
     * Creates a runner configured to list Docker containers.
     */
    public DockerCommandRunner() {
        this(List.of(
            "docker",
            "ps",
            "--all",
            "--format",
            "{{.ID}}\t{{.Names}}\t{{.Image}}\t{{.Status}}\t{{.State}}"
        ));
    }

    /**
     * Creates a runner using the specified command.
     *
     * @param command the command and its arguments
     */
    public DockerCommandRunner(List<String> command) {
        this.command = command;
    }

    /**
     * Retrieves the text representation of all local Docker containers.
     *
     * @return the output produced by Docker
     * @throws IOException if the Docker command cannot be executed
     * @throws InterruptedException if the command execution is interrupted
     */
    public String listContainers() throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(this.command);

        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        String output = new String(
            process.getInputStream().readAllBytes(),
            StandardCharsets.UTF_8
        );

        int exitCode = process.waitFor();

        if (exitCode != 0) {
            throw new IOException("Docker command failed: " + output.trim());
        }

        return output;
    }

    /**
     * Retrieves and parses all local Docker containers.
     *
     * @return the local Docker containers
     * @throws IOException if the Docker command cannot be executed
     * @throws InterruptedException if the command execution is interrupted
     */
    public List<Container> getContainers() throws IOException, InterruptedException {
        String output = this.listContainers();
        ContainerParser parser = new ContainerParser();
        return parser.parse(output);
    }
}
