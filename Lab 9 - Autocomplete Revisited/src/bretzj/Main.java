package bretzj;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    static Stage stage;
    private final int WIDTH = 400;
    private final int HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("autocomplete.fxml"));
        primaryStage.setTitle("Auto Complete");
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();

        stage = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
