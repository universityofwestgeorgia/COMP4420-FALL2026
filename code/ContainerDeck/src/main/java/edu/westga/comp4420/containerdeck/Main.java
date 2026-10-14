package edu.westga.comp4420.containerdeck;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Configures and starts the Container Deck JavaFX application.
 *
 * @author Leo Gregori
 * @version Fall 2026
 */
public class Main extends Application {
	/** The title displayed by the primary application window. */
	public static final String WINDOW_TITLE = "Container Deck";
	/** The location of the dashboard FXML resource. */
	public static final String MAIN_WINDOW_RESOURCE = "view/codebehind/MainWindow.fxml";
	/** The location of the container details FXML resource. */
	public static final String CONTAINER_DETAILS_WINDOW_RESOURCE = "view/codebehind/ContainerDetailsWindow.fxml";

	/**
	 * JavaFX entry point.
	 *
	 * @precondition none
	 * @postcondition none
	 *
	 * @throws IOException if the dashboard FXML resource cannot be loaded
	 */
	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource(Main.MAIN_WINDOW_RESOURCE));
		Scene scene = new Scene(parent);
		primaryStage.setTitle(WINDOW_TITLE);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Primary Java entry point.
	 *
	 * @precondition none
	 * @postcondition none
	 *
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		Main.launch(args);
	}
}
