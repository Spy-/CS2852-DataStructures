/*
 * Course: CS2852
 * Spring 2019
 * Lab 9 - Autocomplete Revisited
 * Name: John Bretz
 * Created: 5/7/19
 */
package bretzj;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main Class
 */
public class Main extends Application {

    static Stage stage;

    /**
     * Entry point for javaFX
     *
     * @param stage the main stage
     * @throws Exception some exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("complete.fxml"));
        stage.setTitle("Auto Complete");
        stage.setScene(new Scene(root));
        stage.show();

        Main.stage = stage;
    }

    /**
     * Main entry point into program
     *
     * @param args some cmd line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
