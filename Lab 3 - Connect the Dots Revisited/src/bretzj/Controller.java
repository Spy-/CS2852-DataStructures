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
import java.nio.file.Paths;
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
                Util.throwAlert(new Alert(Alert.AlertType.ERROR), "Error",
                        "Error while reading file",
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

        picture.removeDots(Picture.sendRemoveDotsDialog(container), false);
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

        picture.removeDots(Picture.sendRemoveDotsDialog(container), true);
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

    /**
     * Tests a given Picture
     *
     * @param p    the picture object
     * @param dots the number of dots to remain
     * @param name the name of the picture
     */
    private void testPicture(Picture p, int dots, String name) {
        long start, end;
        String output = "";
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
                    output += "\tIndexed ArrayList:   " + formatTime(end - start) + "\n";
                    break;
                case 1:
                    output += "\tIndexed LinkedList:  " + formatTime(end - start) + "\n";
                    break;
                case 2:
                    output += "\tIterated ArrayList:  " + formatTime(end - start) + "\n";
                    break;
                case 3:
                    output += "\tIterated LinkedList: " + formatTime(end - start) + "\n";
                    break;
            }
        }
        throwAlert(new Alert(Alert.AlertType.INFORMATION), "Test Result",
                name, output).showAndWait();
    }

    /**
     * Formats the time into a readable format
     *
     * @param millis the number of millis
     * @return a readable format
     */
    private String formatTime(long millis) {
        final long second = 1000, minute = 60;

        long remain = millis % second;
        millis = (millis - remain) / second;
        long seconds = millis % minute;
        millis = (millis - seconds) / minute;
        long minutes = millis % minute;
        long hours = (millis - minutes) / minute;
        return String.format("%02d:%02d:%02d:%03d", hours, minutes, seconds, remain);
    }

    /**
     * Runs all the tests
     * @throws IOException if the files don't exist
     */
    @FXML
    void runTests() throws IOException {
        final int hundred = 100, thousand = 1000, nthousand = 9000;
        Picture balloon100 = new Picture(new ArrayList<>());
        Picture balloon1000 = new Picture(new ArrayList<>());
        Picture skull = new Picture(new ArrayList<>());

        balloon100.load(new File("balloon.dot").toPath());
        balloon1000.load(new File("balloon.dot").toPath());
        skull.load(new File("skull.dot").toPath());

        testPicture(balloon100, hundred, "Balloon100");
        testPicture(balloon100, thousand, "Balloon1000");
        testPicture(skull, nthousand, "Skull9000");
    }
}