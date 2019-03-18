/*
 * Course: CS2852
 * Spring 2019
 * Lab 3 - Connect the Dots Revisited
 * Name: John Bretz
 * Created: 3/8/2019
 */
package bretzj;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Class that handles reading dot files and rendering the information to the screen
 */
public class Picture {

    /**
     * List of dot objects that are loaded from a file
     */
    private List<Dot> dots;

    /**
     * Constructor for a Picture
     *
     * @param emptyList a list to store the dots
     */
    public Picture(List<Dot> emptyList) {
        emptyList.clear();
        dots = emptyList;
    }

    /**
     * Constructs a Picture by copying the dots from the given picture into the new one
     *
     * @param original  the original picture
     * @param emptyList a list to store the dots
     */
    public Picture(Picture original, List<Dot> emptyList) {
        this(emptyList);
        emptyList.addAll(original.getDots());
    }

    /**
     * Setup code for the removeDots methods
     *
     * @param container the container method
     * @return a correct number
     */
    public static int sendRemoveDotsDialog(Picture container) {
        Dialog dialog = Util.throwAlert(new TextInputDialog(""), "Dot Remover", "Dot Remover",
                "How many dots do you want to stay");
        Optional result = dialog.showAndWait();

        int number;
        try {
            number = (int) Double.parseDouble((String) result.get());
            if (number < 3) {
                Util.throwAlert(new Alert(Alert.AlertType.ERROR), "Error", "Invalid Number",
                        "Number must be greater than or equal to 3.").show();
                number = container.getDots().size();
            }
        } catch (NumberFormatException ignored) {
            Util.throwAlert(new Alert(Alert.AlertType.ERROR), "Error", "Invalid Number",
                    "Number must be greater than or equal to 3.").show();
            number = container.getDots().size();
        }
        return number;
    }

    /**
     * Reads the given file and creates a list of Dot objects
     *
     * @param path the file's path
     * @throws IOException if the file isn't there
     */
    public void load(Path path) throws IOException {
        try (Scanner scan = new Scanner(path.getFileName())) {
            dots.clear();
            double x, y;
            while (scan.hasNextLine()) {
                String[] coord = scan.nextLine().split(",");
                x = Double.parseDouble(coord[0]);
                y = Double.parseDouble(coord[1]);

                if ((x >= 0.0 && x <= 1.0) && (y >= 0.0 && y <= 1.0)) {
                    dots.add(new Dot(x, y));
                }
            }
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            throw new IOException("Corrupted File");
        }
    }

    /**
     * Saves the current canvas to the given file
     *
     * @param file the file
     * @throws FileNotFoundException if the file doesn't exist
     */
    public void saveDotFile(File file) throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(file)) {
            for (Dot d : dots) {
                pw.println(d.getX() / Main.WIDTH + "," + (1.0 - (d.getY() / Main.HEIGHT)));
            }
        }
    }

    /**
     * Renders the dots to the given canvas
     *
     * @param canvas the canvas
     */
    public void drawDots(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        final double size = 7;

        for (Dot d : dots) {
            gc.fillOval(d.getX() - size / 2, d.getY() - size / 2, size, size);
        }

        resetTitle();
    }

    /**
     * Renders the lines between dots to the given canvas
     *
     * @param canvas the canvas
     */
    public void drawLines(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        if (dots.size() > 0) {
            gc.beginPath();
            gc.moveTo(dots.get(0).getX(), dots.get(0).getY());
            for (int i = 1; i < dots.size(); i++) {
                Dot dot = dots.get(i);
                gc.lineTo(dot.getX(), dot.getY());
            }
            gc.closePath();
            gc.stroke();
        }
    }

    /**
     * Removes dots until a certain amount of dots are left
     *
     * @param numberDesired the number of dots to remain
     * @param useIterator   true if an Iterator should be used
     */
    public void removeDots(int numberDesired, boolean useIterator) {
        while (dots.size() > numberDesired) {
            dots.remove(useIterator ? getLowestCriticalDotIterator() : getLowestCriticalDot());
        }
    }

    /**
     * Updates the TITLE of the window with how many dots are displayed
     */
    private void resetTitle() {
        Main.stage.setTitle(Main.TITLE + " | " + dots.size() + " dots");
    }

    /**
     * gets the dot with the lowest critical value using index based methods
     *
     * @return the dot
     */
    private Dot getLowestCriticalDot() {
        double lowestValue = Double.MAX_VALUE, criticalValue;
        Dot toRemove = null, current, prev, next;

        for (int i = 1; i < dots.size() - 1; i++) {
            current = dots.get(i);
            prev = dots.get(i - 1);
            next = dots.get(i + 1);
            criticalValue = current.calculateCriticalValue(prev, next);

            if (criticalValue < lowestValue) {
                lowestValue = criticalValue;
                toRemove = current;
            }
        }
        return toRemove;
    }

    /**
     * gets the dot with the lowest critical value using an iterator
     *
     * @return the dot
     */
    private Dot getLowestCriticalDotIterator() {
        Iterator itr = dots.iterator();
        double lowestValue = Double.MAX_VALUE, criticalValue;
        Dot toRemove = null;
        Dot prev = (Dot) itr.next(), current = (Dot) itr.next(), next = (Dot) itr.next();

        criticalValue = current.calculateCriticalValue(prev, next);

        if (criticalValue < lowestValue) {
            lowestValue = criticalValue;
            toRemove = current;
        }

        while (itr.hasNext()) {
            prev = current;
            current = next;
            next = (Dot) itr.next();

            criticalValue = current.calculateCriticalValue(prev, next);

            if (criticalValue < lowestValue) {
                lowestValue = criticalValue;
                toRemove = current;
            }
        }

        return toRemove;
    }

    public List<Dot> getDots() {
        return new ArrayList<>(dots);
    }
}
