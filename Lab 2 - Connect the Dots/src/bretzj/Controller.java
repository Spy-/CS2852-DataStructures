/*
 * Course: CS2852
 * Spring 2019
 * Lab 2 - Connect the Dots
 * Name: John Bretz
 * Created: 3/8/2019
 */
package bretzj;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static bretzj.Util.clearCanvas;
import static bretzj.Util.throwAlert;

/**
 * JavaFX controller class
 */
public class Controller {
    /**
     * The canvas
     */
    @FXML
    private Canvas canvas;

    /**
     * Stores the original data from the opened file
     */
    private Picture container = new Picture(new ArrayList<>());

    /**
     * Created using dots from `container` to manipulate the canvas
     */
    private Picture picture;

    /**
     * Closes the window
     */
    @FXML
    void close() {
        Platform.exit();
    }

    /**
     * Removes the lines from the canvas
     */
    @FXML
    void dotsOnly() {
        canvas.getGraphicsContext2D().clearRect(0, 0, Main.WIDTH, Main.HEIGHT);
        picture.drawDots(canvas);
    }

    /**
     * removes the dots from the canvas
     */
    @FXML
    void linesOnly() {
        canvas.getGraphicsContext2D().clearRect(0, 0, Main.WIDTH, Main.HEIGHT);
        picture.drawLines(canvas);
    }

    /**
     * Opens a file
     */
    @FXML
    void open() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open Dot File");
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Dot Files", "*.dot")
        );

        File selectedFile = fc.showOpenDialog(Main.stage);

        try {
            container.load(selectedFile.toPath());
            picture = new Picture(container, new ArrayList<>());
            clearCanvas(canvas);
            picture.drawDots(canvas);
            picture.drawLines(canvas);
        } catch (FileNotFoundException e) {
            Util.throwAlert(new Alert(Alert.AlertType.ERROR), "Error", "File not found",
                    "The file does not exist.").show();
        } catch (IOException e) {
            Util.throwAlert(new Alert(Alert.AlertType.ERROR), "Error", "Error while reading file",
                    "Encountered an error while reading the file").show();
        } catch (NullPointerException ignored) {
        }
    }

    /**
     * Saves a file
     */
    @FXML
    void save() {
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

    /**
     * Opens the remove dots dialog
     */
    @FXML
    void removeDots() {
        Dialog dialog = Util.throwAlert(new TextInputDialog(""), "Dot Remover", "Dot Remover",
                "How many dots do you want to stay");
        Optional result = dialog.showAndWait();

        int number;
        try {
            number = (int) Double.parseDouble((String) result.get());
            if (number < 3) {
                Util.throwAlert(new Alert(Alert.AlertType.ERROR), "Error", "Invalid Number", "Number must be greater than or equal to 3.").show();
                number = container.getDots().size();
            }
        } catch (NumberFormatException ignored) {
            number = container.getDots().size();
        }

        picture = new Picture(container, new ArrayList<>());

        System.out.println("Size: " + picture.getDots().size());

        picture.removeDots(number);
        clearCanvas(canvas);
        picture.drawDots(canvas);
        picture.drawLines(canvas);

        System.out.println("Size: " + picture.getDots().size());
    }

    /**
     * Initializes things for testing purposes
     */
    @FXML
    void initialize() {
        try {
            container.load(new File("dove.dot").toPath());
            picture = new Picture(container, new ArrayList<>());
            picture.drawDots(canvas);
            picture.drawLines(canvas);
        } catch (IOException ignored) {
        }
    }
}