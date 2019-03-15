/*
 * Course: CS2852
 * Spring 2019
 * Lab 3 - Connect the Dots Revisited
 * Name: John Bretz
 * Created: 3/8/2019
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

    /**
     * Width of the window
     */
    public static final double WIDTH = 600;

    /**
     * Height of the window
     */
    public static final double HEIGHT = 600;

    /**
     * The window's stage
     */
    static Stage stage;

    /**
     * Base title of the stage
     */
    static final String title = "Lab 3 - Connect the Dots Revisited";

    /**
     * JavaFX entry point
     *
     * @param primaryStage the window stage
     * @throws Exception some exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("generator.fxml"));
        primaryStage.setTitle(Main.title);
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Main method
     *
     * @param args cmd line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
