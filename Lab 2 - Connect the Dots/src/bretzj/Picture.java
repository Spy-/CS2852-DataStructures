/*
 * Course: CS2852
 * Spring 2019
 * Lab 2 - Connect the Dots
 * Name: John Bretz
 * Created: 3/8/2019
 */
package bretzj;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.List;
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
        emptyList.clear();
        emptyList.addAll(original.getDots());
        dots = emptyList;
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
        PrintWriter pw = new PrintWriter(file);

        for (Dot d : dots) {
            pw.println(d.getX() / Main.WIDTH + "," + (1.0 - (d.getY() / Main.HEIGHT)));
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

    /**
     * Removes dots until a certain amount of dots are left
     *
     * @param numberDesired the number of dots to remain
     */
    public void removeDots(int numberDesired) {
        double lowestValue, criticalValue;
        Dot toRemove, current, prev, next;

        while (dots.size() > numberDesired) {
            toRemove = null;
            lowestValue = Double.MAX_VALUE;

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

            dots.remove(toRemove);
        }
    }

    public List<Dot> getDots() {
        return dots;
    }
}
