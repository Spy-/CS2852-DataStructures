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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that handles reading dot files and rendering the information to the screen
 */
public class Picture {

    private static ArrayList<Dot> dots = new ArrayList<>();

    /**
     * Reads the given file and creates a list of Dot objects
     *
     * @param file the file
     * @throws FileNotFoundException if the file isn't there
     */
    public static void readDotFile(File file) throws FileNotFoundException {
        Scanner scan = new Scanner(file);
        dots.clear();

        try {
            while (scan.hasNextLine()) {
                String[] coord = scan.nextLine().split(",");
                dots.add(new Dot(Double.parseDouble(coord[0]), Double.parseDouble(coord[1])));
            }
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            Util.throwAlert("Error while reading file", "Encountered an error while reading the file").show();
        }
    }

    /**
     * Renders the dots to the given canvas
     *
     * @param canvas the canvas
     */
    public static void drawDots(Canvas canvas) {
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
    public static void drawLines(Canvas canvas) {
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
}
