/*
 * Course: CS2852
 * Spring 2019
 * Lab 1 - Dot 2 Dot Generator
 * Name: John Bretz
 * Created: 3/4/2019
 */
package bretzj;

import javafx.scene.control.Alert;

/**
 * Utility class for helper methods
 */
public class Util {

    /**
     * Creates an Alert
     * @param header the header text
     * @param content the content text
     * @return the alert
     */
    public static Alert throwAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(header);
        alert.setContentText(content);

        return alert;
    }
}
