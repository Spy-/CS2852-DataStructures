/*
 * Course: CS2852
 * Spring 2019
 * Lab 1 - Dot 2 Dot Generator
 * Name: John Bretz
 * Created: 3/4/2019
 */
package bretzj;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

/**
 * Class that handles reading dot files and rendering the information to the screen
 */
public class Picture {

    private List<Dot> dots;

    public Picture(List<Dot> emptyList) {
        emptyList.clear();
        dots = emptyList;
    }

    public Picture(Picture original, List<Dot> emptyList) {
        emptyList.clear();
        emptyList.addAll(original.getDots());
        dots = emptyList;
    }

    /**
     * Reads the given file and creates a list of Dot objects
     *
     * @param file the file
     * @throws FileNotFoundException if the file isn't there
     */
    public void readDotFile(File file) throws FileNotFoundException {
        Scanner scan = new Scanner(file);
        dots.clear();

        try {
            while (scan.hasNextLine()) {
                String[] coord = scan.nextLine().split(",");
                dots.add(new Dot(Double.parseDouble(coord[0]), Double.parseDouble(coord[1])));
            }
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            Util.throwAlert(new Alert(Alert.AlertType.ERROR), "Error", "Error while reading file",
                    "Encountered an error while reading the file").show();
        }
    }

    public void saveDotFile(File file) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(file);

        for (Dot d : dots) {
            pw.println(d.getX() + "," + d.getY());
        }
        pw.close();
    }

    /**
     * Renders the dots to the given canvas
     *
     * @param canvas the canvas
     */
    public void drawDots(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        final double size = 7;

        if (dots.size() > 0) {
            for (Dot d : dots) {
                gc.fillOval(d.getX() - size / 2, d.getY() - size / 2, size, size);
            }
        }
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

    public void removeDots(int numberDesired) { // TODO cleanup

        while (dots.size() > numberDesired) {
            double lowestValue = Double.MAX_VALUE;
            Dot toRemove = null;

            for (int i = 1; i < dots.size() - 1; i++) {
                Dot current = dots.get(i);
                Dot prev = dots.get(i - 1);
                Dot next = dots.get(i + 1);
                double criticalValue = current.calculateCriticalValue(prev, next);

                if (criticalValue < lowestValue) {
                    lowestValue = criticalValue;
                    toRemove = current;
                }
            }

            dots.remove(toRemove);
        }
    }

    public List<Dot> getDots() {
        return dots;
    }
}
