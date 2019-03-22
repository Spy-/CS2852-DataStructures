/*
 * Course: CS2852
 * Spring 2019
 * Lab 4 - AutoComplete
 * Name: John Bretz
 * Created: 3/22/2019
 */
package bretzj;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class
 */
public class Main extends Application {

    static Stage stage;
    private final int width = 400;
    private final int height = 600;

    /**
     * Entry point for JavaFX
     *
     * @param primaryStage the window
     * @throws Exception some error
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("autocomplete.fxml"));
        primaryStage.setTitle("Auto Complete");
        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.show();

        stage = primaryStage;
    }


    /**
     * Main entry point into the program
     *
     * @param args cmdline arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
