/*
 * Course: CS2852
 * Spring 2019
 * Lab 4 - Auto Complete
 * Name: John Bretz
 * Created: 3/16/2019
 */
package bretzj;

import javafx.scene.control.Dialog;

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

    public static double roundDecimal(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
