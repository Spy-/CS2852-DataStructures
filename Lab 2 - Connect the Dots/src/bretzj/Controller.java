/*
 * Course: CS2852
 * Spring 2019
 * Lab 1 - Dot 2 Dot Generator
 * Name: John Bretz
 * Created: 3/4/2019
 */
package bretzj;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * JavaFX controller class
 */
public class Controller {
    @FXML
    private Canvas picture;

    @FXML
    void close(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void dotsOnly(ActionEvent event) {
        picture.getGraphicsContext2D().clearRect(0, 0, Main.WIDTH, Main.HEIGHT);
        Picture.drawDots(picture);
    }

    @FXML
    void linesOnly(ActionEvent event) {
        picture.getGraphicsContext2D().clearRect(0, 0, Main.WIDTH, Main.HEIGHT);
        Picture.drawLines(picture);
    }

    @FXML
    void open(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open Dot File");
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Dot Files", "*.dot")
        );

        File selectedFile = fc.showOpenDialog(Main.stage);

        try {
            Picture.readDotFile(selectedFile);
            picture.getGraphicsContext2D().clearRect(0,0,Main.WIDTH,Main.HEIGHT);
            Picture.drawDots(picture);
            Picture.drawLines(picture);
        } catch (FileNotFoundException e) {
            Util.throwAlert("File not found", "The file does not exist.").show();
        } catch (NullPointerException ignored) { }
    }

    @FXML
    void initialize() {
        try {
            Picture.readDotFile(new File("balloon.dot"));
            Picture.drawDots(picture);
            Picture.drawLines(picture);
        } catch (FileNotFoundException ignored) { }
    }
}