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

	private Container getSelectedContainer() {
		Container selectedContainer = this.containerTable
				.getSelectionModel()
				.getSelectedItem();

		if (selectedContainer == null) {
			Alert warningBox = new Alert(AlertType.WARNING);
			warningBox.setHeaderText("No container selected");
			warningBox.setContentText("Select a container from the table first.");
			warningBox.showAndWait();
		}

		return selectedContainer;
	}

	@FXML
	void openContainerDetails(ActionEvent event) {
		Container selectedContainer = this.getSelectedContainer();

		if (selectedContainer == null) {
			return;
		}

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource(Main.CONTAINER_DETAILS_WINDOW_RESOURCE));
			loader.load();
			ContainerDetailsWindow controller = loader.getController();
			controller.displayContainer(selectedContainer);
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
	void startSelectedContainer() {
		Container selectedContainer = this.getSelectedContainer();

		if (selectedContainer == null) {
			return;
		}

		DockerCommandRunner runner = new DockerCommandRunner();

		try {
			runner.startContainer(selectedContainer.getIdentifier());
			this.loadContainers();
		} catch (IOException error) {
			Alert errorBox = new Alert(AlertType.ERROR);
			errorBox.setHeaderText("Unable to start container");
			errorBox.setContentText(error.getMessage());
			errorBox.showAndWait();
		} catch (InterruptedException error) {
			Thread.currentThread().interrupt();

			Alert errorBox = new Alert(AlertType.ERROR);
			errorBox.setHeaderText("Start command interrupted");
			errorBox.setContentText("Container Deck could not start the container.");
			errorBox.showAndWait();
		}
	}

	@FXML
	void refreshContainers() {
		this.loadContainers();
	}

	private void loadContainers() {
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
	}

	@FXML
	void stopSelectedContainer() {
		Container selectedContainer = this.getSelectedContainer();

		if (selectedContainer == null) {
			return;
		}

		DockerCommandRunner runner = new DockerCommandRunner();

		try {
			runner.stopContainer(selectedContainer.getIdentifier());
			this.loadContainers();
		} catch (IOException error) {
			Alert errorBox = new Alert(AlertType.ERROR);
			errorBox.setHeaderText("Unable to stop container");
			errorBox.setContentText(error.getMessage());
			errorBox.showAndWait();
		} catch (InterruptedException error) {
			Thread.currentThread().interrupt();

			Alert errorBox = new Alert(AlertType.ERROR);
			errorBox.setHeaderText("Stop command interrupted");
			errorBox.setContentText("Container Deck could not stop the container.");
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

		this.loadContainers();

		assert this.guiPane != null : "fx:id=\"guiPane\" was not injected: check your FXML file 'MainWindow.fxml'.";
	}
}
