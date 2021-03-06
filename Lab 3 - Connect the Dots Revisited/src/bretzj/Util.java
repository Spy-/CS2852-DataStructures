/*
 * Course: CS2852
 * Spring 2019
 * Lab 3 - Connect the Dots Revisited
 * Name: John Bretz
 * Created: 3/8/2019
 */
package bretzj;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Dialog;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Utility class for helper methods
 */
public class Util {

    /**
     * Creates a dialog
     *
     * @param type    a dialog
     * @param title   the dialog's TITLE
     * @param header  the header text
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
     *
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
     *
     * @param canvas the canvas
     */
    public static void clearCanvas(Canvas canvas) {
        canvas.getGraphicsContext2D().clearRect(0, 0, Main.WIDTH, Main.HEIGHT);
    }

    /**
     * Creates a new Picture with an ArrayList or a LinkedList
     *
     * @param container            the original Picture
     * @param useLinkedListInstead uses LinkedList if true else ArrayList
     * @return the new Picture
     */
    public static Picture createPicture(Picture container, boolean useLinkedListInstead) {
        LinkedList<Dot> link = new LinkedList<>();
        ArrayList<Dot> array = new ArrayList<>();
        return useLinkedListInstead ? new Picture(container, link) : new Picture(container, array);
    }
}
