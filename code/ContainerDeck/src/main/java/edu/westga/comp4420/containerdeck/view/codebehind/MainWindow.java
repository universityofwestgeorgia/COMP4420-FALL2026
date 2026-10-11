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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

import edu.westga.comp4420.containerdeck.Main;
import edu.westga.comp4420.containerdeck.model.Container;
import edu.westga.comp4420.containerdeck.model.DockerCommandRunner;

/**
 * Handles presentation behavior for the Container Deck dashboard.
 *
 * @author	Comp 4420
 * @version Fall 2026
 */
public class MainWindow {
	@FXML private AnchorPane guiPane;
	@FXML private TableView<Container> containerTable;
	@FXML private TableColumn<Container, String> nameColumn;
	@FXML private TableColumn<Container, String> imageColumn;
	@FXML private TableColumn<Container, String> statusColumn;
	@FXML private TableColumn<Container, String> stateColumn;

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
		this.nameColumn.setCellValueFactory(
			new PropertyValueFactory<>("name")
		);
		this.imageColumn.setCellValueFactory(
			new PropertyValueFactory<>("image")
		);
		this.statusColumn.setCellValueFactory(
			new PropertyValueFactory<>("status")
		);
		this.stateColumn.setCellValueFactory(
			new PropertyValueFactory<>("state")
		);

		DockerCommandRunner runner = new DockerCommandRunner();

		try {
			this.containerTable.getItems().setAll(runner.getContainers());
		} catch (IOException error) {
			Alert errorBox = new Alert(AlertType.ERROR);
			errorBox.setHeaderText("Docker is unavailable");
			errorBox.setContentText(error.getMessage());
			errorBox.showAndWait();
		} catch (InterruptedException error) {
			Thread.currentThread().interrupt();

			Alert errorBox = new Alert(AlertType.ERROR);
			errorBox.setHeaderText("Docker command interrupted");
			errorBox.setContentText("Container Deck could not load the containers.");
			errorBox.showAndWait();
		}

		assert this.guiPane != null : "fx:id=\"guiPane\" was not injected: check your FXML file 'MainWindow.fxml'.";
	}
}
