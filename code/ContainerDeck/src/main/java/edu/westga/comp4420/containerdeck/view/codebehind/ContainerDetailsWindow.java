package edu.westga.comp4420.containerdeck.view.codebehind;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
/**
 * Handles presentation behavior for the container details window.
 *
 * @author	Comp 4420
 * @version Fall 2026
 */
public class ContainerDetailsWindow {
	@FXML private AnchorPane guiPane;

	@FXML
	void initialize() {
		assert this.guiPane != null : "fx:id=\"guiPane\" was not injected: check ContainerDetailsWindow.fxml.";
	}
}
