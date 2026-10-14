package edu.westga.comp4420.containerdeck.view.codebehind;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;

import edu.westga.comp4420.containerdeck.model.Container;

/**
 * Handles presentation behavior for the container details window.
 *
 * @author Leo Gregori
 * @version Fall 2026
 */
public class ContainerDetailsWindow {
	@FXML private AnchorPane guiPane;
	@FXML private Label nameLabel;
	@FXML private Label imageLabel;
	@FXML private Label statusLabel;
	@FXML private Label stateLabel;
	@FXML private Label identifierLabel;

	/**
	 * Displays the selected container information.
	 *
	 * @precondition container != null
	 * @postcondition all detail labels display the supplied container's values
	 *
	 * @param container the container selected on the dashboard
	 */
	public void displayContainer(Container container) {
		this.nameLabel.setText(container.getName());
		this.imageLabel.setText(container.getImage());
		this.statusLabel.setText(container.getStatus());
		this.stateLabel.setText(container.getState());
		this.identifierLabel.setText(container.getIdentifier());
	}

	/**
	 * Verifies that the FXML root pane was injected.
	 */
	@FXML
	void initialize() {
		assert this.guiPane != null : "fx:id=\"guiPane\" was not injected: check ContainerDetailsWindow.fxml.";
	}
}
