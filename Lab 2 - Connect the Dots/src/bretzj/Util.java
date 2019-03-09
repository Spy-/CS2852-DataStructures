/*
 * Course: CS2852
 * Spring 2019
 * Lab 1 - Dot 2 Dot Generator
 * Name: John Bretz
 * Created: 3/4/2019
 */
package bretzj;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Dialog;

/**
 * Utility class for helper methods
 */
public class Util {

    /**
     * Creates a dialog
     * @param type a dialog
     * @param title the dialog's title
     * @param header the header text
     * @param content the content text
     * @return the created dialog
     */
    public static Dialog throwAlert(Dialog type, String title, String header, String content) {
        type.setTitle(title);
        type.setHeaderText(header);
        type.setContentText(content);

        return type;
    }

    /**
     * Calculates the distance between two points
     * @param x1 first x coordinate
     * @param y1 first y coordinate
     * @param x2 second x coordinate
     * @param y2 second y coordinate
     * @return the distance
     */
    public static double dist(double x1, double y1, double x2, double y2) {
        double dx = x1 - x2;
        double dy = y1 - y2;

        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Clears the given given canvas
     * @param canvas the canvas
     */
    public static void clearCanvas(Canvas canvas) {
        canvas.getGraphicsContext2D().clearRect(0, 0, Main.WIDTH, Main.HEIGHT);
    }
}
