package edu.westga.comp4420.containerdeck.view.codebehind;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

import edu.westga.comp4420.containerdeck.Main;

/**
 * Handles presentation behavior for the Container Deck dashboard.
 *
 * @author	Comp 4420
 * @version Fall 2026
 */
public class MainWindow {   
	@FXML private AnchorPane guiPane;

	@FXML
	void openContainerDetails(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource(Main.CONTAINER_DETAILS_WINDOW_RESOURCE));
			loader.load();
			Parent parent = loader.getRoot();
			Scene scene = new Scene(parent);
			Stage detailsStage = new Stage();
			detailsStage.setTitle("Container Details");
			detailsStage.setScene(scene);
			detailsStage.initModality(Modality.APPLICATION_MODAL);
			detailsStage.showAndWait();
		} catch (IOException error) {
			Alert errorBox = new Alert(AlertType.ERROR);
			errorBox.setContentText("Unable to open the container details window.");
			errorBox.showAndWait();
		}
	}
	
	@FXML
	void initialize() {
		assert this.guiPane != null : "fx:id=\"guiPane\" was not injected: check your FXML file 'MainWindow.fxml'.";
	}
}
