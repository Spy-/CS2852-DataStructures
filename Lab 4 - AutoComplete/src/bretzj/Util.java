/*
 * Course: CS2852
 * Spring 2019
 * Lab 4 - Auto Complete
 * Name: John Bretz
 * Created: 3/16/2019
 */
package bretzj;

import javafx.scene.control.Dialog;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for helper methods
 */
public class Util {

    /**
     * Creates a dialog
     *
     * @param type    a dialog
     * @param title   the dialog's title
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
     * Rounds a number to two decimal places
     *
     * @param value the number
     * @return the number rounded
     */
    public static double roundDecimal(double value) {
        final double modifier = 1000.0;
        return Math.round(value * modifier) / modifier;
    }

    /**
     * Turns the nanoTime into a readable format
     *
     * @param nanos the number of nanoseconds
     * @return a readable format
     */
    public static String formatTime(long nanos) {
        final float micro = 1000;
        final float milli = 1000000;
        final float secon = 1000000000;

        if (nanos < micro) {
            return nanos + " nanoseconds";
        } else if (nanos < milli) {
            return roundDecimal(nanos / micro) + " microseconds";
        } else if (nanos < secon) {
            return roundDecimal(nanos / milli) + " milliseconds";
        }

        return new SimpleDateFormat("mm:ss.SSS").format(new Date((long) (nanos / milli)));
    }
}
