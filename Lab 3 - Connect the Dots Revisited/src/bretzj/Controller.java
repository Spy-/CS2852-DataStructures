/*
 * Course: CS2852
 * Spring 2019
 * Lab 3 - Connect the Dots Revisited
 * Name: John Bretz
 * Created: 3/8/2019
 */
package bretzj;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import static bretzj.Util.clearCanvas;
import static bretzj.Util.throwAlert;

/**
 * JavaFX controller class
 */
public class Controller {
    /**
     * The draw menu
     */
    @FXML
    private Menu drawMenu;

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

    private static boolean useLinkedListInstead = false;

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

        if (selectedFile != null) {
            try {
                container.load(selectedFile.toPath());
                picture = new Picture(container, new ArrayList<>());
                clearCanvas(canvas);
                picture.drawDots(canvas);
                picture.drawLines(canvas);
                drawMenu.setDisable(false);
            } catch (FileNotFoundException e) {
                Util.throwAlert(new Alert(Alert.AlertType.ERROR), "Error", "File not found",
                        "The file does not exist.").show();
            } catch (IOException e) {
                Util.throwAlert(new Alert(Alert.AlertType.ERROR), "Error", "Error while reading file",
                        "Encountered an error while reading the file").show();
            }
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
     * Removes dots using index based methods
     */
    @FXML
    void removeDots() {
        picture = Util.createPicture(container, useLinkedListInstead);

        picture.removeDots(Picture.removeDotsStart(container), false);
        clearCanvas(canvas);
        picture.drawDots(canvas);
        picture.drawLines(canvas);
    }

    /**
     * Removes dots using an iterator
     */
    @FXML
    void removeDotsIterator() {
        picture = Util.createPicture(container, useLinkedListInstead);

        picture.removeDots(Picture.removeDotsStart(container), true);
        clearCanvas(canvas);
        picture.drawDots(canvas);
        picture.drawLines(canvas);
    }

    /**
     * Use LinkedLists instead of ArrayLists for constructors
     */
    @FXML
    void useLinked() {
        useLinkedListInstead = !useLinkedListInstead;
        System.out.println("useLinkedListInstead = " + useLinkedListInstead);
    }

    /**
     * Initializes things for testing purposes
     */
    @FXML
    void initialize() {
        try {
            container.load(new File("test.dot").toPath());
            picture = new Picture(container, new ArrayList<>());
            picture.drawLines(canvas);
            picture.drawDots(canvas);
            drawMenu.setDisable(false);
        } catch (IOException ignored) {
        }
    }

    private void testPicture(Picture p, int dots, String name) {
        long start, end;
        StringBuilder output = new StringBuilder();
        Picture[] tests = {
                new Picture(p, new ArrayList<>()),
                new Picture(p, new LinkedList<>()),
                new Picture(p, new ArrayList<>()),
                new Picture(p, new LinkedList<>())
        };

        for (int i = 0; i < tests.length; i++) {
            start = System.currentTimeMillis();
            if (i < 2) { // index
                tests[i].removeDots(dots, false);
            } else { // iterator
                tests[i].removeDots(dots, true);
            }
            end = System.currentTimeMillis();

            switch (i) {
                case 0:
                    output.append("\tIndexed ArrayList:   ").append(formatTime(end - start)).append("\n");
                    break;
                case 1:
                    output.append("\tIndexed LinkedList:  ").append(formatTime(end - start)).append("\n");
                    break;
                case 2:
                    output.append("\tIterated ArrayList:  ").append(formatTime(end - start)).append("\n");
                    break;
                case 3:
                    output.append("\tIterated LinkedList: ").append(formatTime(end - start)).append("\n");
                    break;
            }
        }
        throwAlert(new Alert(Alert.AlertType.INFORMATION), "Test Result", name, output.toString()).showAndWait();
    }

    private String formatTime(long millis) {
        long remain = millis % 1000;
        millis = (millis - remain) / 1000;
        long seconds = millis % 60;
        millis = (millis - seconds) / 60;
        long minutes = millis % 60;
        long hours = (millis - minutes) / 60;
        return String.format("%02d:%02d:%02d:%03d", hours, minutes, seconds, remain);
    }

    public void runTests() throws IOException {
        Picture balloon100 = new Picture(new ArrayList<>());
        Picture balloon1000 = new Picture(new ArrayList<>());
        Picture skull = new Picture(new ArrayList<>());

        balloon100.load(new File("balloon.dot").toPath());
        balloon1000.load(new File("balloon.dot").toPath());
        skull.load(new File("skull.dot").toPath());

        testPicture(balloon100, 100, "Balloon100");
        testPicture(balloon100, 1000, "Balloon1000");
        testPicture(skull, 9000, "Skull9000");
    }
}