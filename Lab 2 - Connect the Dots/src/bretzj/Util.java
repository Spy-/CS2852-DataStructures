/*
 * Course: CS2852
 * Spring 2019
 * Lab 1 - Dot 2 Dot Generator
 * Name: John Bretz
 * Created: 3/4/2019
 */
package bretzj;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;

/**
 * Utility class for helper methods
 */
public class Util {

    public static Dialog throwAlert(Dialog type, String title, String header, String content) {
        type.setTitle(title);
        type.setHeaderText(header);
        type.setContentText(content);

        return type;
    }

    public static double dist(double x1, double y1, double x2, double y2) {
        double dx = x1 - x2;
        double dy = y1 - y2;

        return Math.sqrt(dx * dx + dy * dy);
    }

    public static void clearCanvas(Canvas canvas) {
        canvas.getGraphicsContext2D().clearRect(0, 0, Main.WIDTH, Main.HEIGHT);
    }
}
