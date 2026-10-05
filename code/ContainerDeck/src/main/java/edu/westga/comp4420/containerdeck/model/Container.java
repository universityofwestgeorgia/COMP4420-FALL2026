package edu.westga.comp4420.containerdeck.model;

/**
 * Stores the Docker information displayed for one local container.
 *
 * @author Leo Gregori
 * @version Fall 2026
 */
public final class Container {
	private final String identifier;
	private final String name;
	private final String image;
	private final String status;
	private final String state;

	/**
	 * Creates a container with the information reported by Docker.
	 *
	 * @precondition identifier, name, image, status, and state must not be null
	 *               or blank
	 * @postcondition all container properties match the supplied values
	 *
	 * @param identifier the Docker container identifier
	 * @param name the Docker container name
	 * @param image the image used by the container
	 * @param status the human-readable status reported by Docker
	 * @param state the current Docker state
	 */
	public Container(String identifier, String name, String image, String status, String state) {
		this.identifier = requireText(identifier, "identifier");
		this.name = requireText(name, "name");
		this.image = requireText(image, "image");
		this.status = requireText(status, "status");
		this.state = requireText(state, "state");
	}

	/**
	 * Returns the Docker container identifier.
	 *
	 * @return the container identifier
	 */
	public String getIdentifier() {
		return this.identifier;
	}

	/**
	 * Returns the Docker container name.
	 *
	 * @return the container name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the image used by the container.
	 *
	 * @return the container image
	 */
	public String getImage() {
		return this.image;
	}

	/**
	 * Returns the human-readable status reported by Docker.
	 *
	 * @return the container status
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * Returns the current Docker state.
	 *
	 * @return the container state
	 */
	public String getState() {
		return this.state;
	}

	private static String requireText(String value, String propertyName) {
		if (value == null || value.isBlank()) {
			throw new IllegalArgumentException("Container " + propertyName + " must not be blank.");
		}
		return value;
	}
}
