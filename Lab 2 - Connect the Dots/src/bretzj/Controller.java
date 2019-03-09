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
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Optional;

import static bretzj.Util.clearCanvas;
import static bretzj.Util.throwAlert;

/**
 * JavaFX controller class
 */
public class Controller {
    @FXML
    private Canvas canvas;

    private Picture picture;

    @FXML
    void close(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void dotsOnly(ActionEvent event) {
        canvas.getGraphicsContext2D().clearRect(0, 0, Main.WIDTH, Main.HEIGHT);
        picture.drawDots(canvas);
    }

    @FXML
    void linesOnly(ActionEvent event) {
        canvas.getGraphicsContext2D().clearRect(0, 0, Main.WIDTH, Main.HEIGHT);
        picture.drawLines(canvas);
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
            picture.readDotFile(selectedFile);
            clearCanvas(canvas);
            picture.drawDots(canvas);
            picture.drawLines(canvas);
        } catch (FileNotFoundException e) {
            Util.throwAlert(new Alert(Alert.AlertType.ERROR), "Error", "File not found",
                    "The file does not exist.").show();
        } catch (NullPointerException ignored) {
        }
    }

    @FXML
    void save(ActionEvent event) { //TODO get it to be on a scale of 0 to 1 and origin being in the bottom left corner
        FileChooser fc = new FileChooser();
        fc.setTitle("Open Dot File");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Dot Files", "*.dot"),
                new FileChooser.ExtensionFilter("All Files", "*")
        );

        File selectedFile = fc.showOpenDialog(Main.stage);

        try {
            picture.saveDotFile(selectedFile);
        } catch (FileNotFoundException e) {
            throwAlert(new Alert(Alert.AlertType.ERROR), "Error", "File not found",
                    "The file doesn't exist.").show();
        }
    }

    @FXML
    public void removeDots(ActionEvent actionEvent) {
        Dialog dialog = Util.throwAlert(new TextInputDialog(""), "Dot Remover", "Dot Remover", "How many dots do you want to stay");
        Optional result = dialog.showAndWait();

        int number;
        try {
            number = (int) Double.parseDouble((String) result.get());
            if (number < 3) {
                number = 3;
            }
        } catch (NumberFormatException ignored) {
            number = picture.getDots().size();
        }

        Picture temp = new Picture(picture, new ArrayList<>());

        System.out.println("Size: " + temp.getDots().size());

        temp.removeDots(number);
        clearCanvas(canvas);
        temp.drawDots(canvas);
        temp.drawLines(canvas);

        System.out.println("Size: " + temp.getDots().size());
    }

    @FXML
    void initialize() {
        picture = new Picture(new ArrayList<>());
        try {
            picture.readDotFile(new File("skull.dot"));
            picture.drawDots(canvas);
            picture.drawLines(canvas);
        } catch (FileNotFoundException ignored) {
        }
    }
}