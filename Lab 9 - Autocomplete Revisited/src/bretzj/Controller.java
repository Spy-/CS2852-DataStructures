/*
 * Course: CS2852
 * Spring 2019
 * Lab 4 - AutoComplete
 * Name: John Bretz
 * Created: 3/22/2019
 */
package bretzj;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * class for the JavaFx Controller
 */
public class Controller {

    @FXML
    private TextField search;

    @FXML
    private TextArea matches;

    @FXML
    private Label time;

    @FXML
    private Label matchesCount;

    private Stage stage;
    private BaseAutoCompleter ac;
    private String dictionaryFileName;

    @FXML
    void prefixTree() throws FileNotFoundException {
        ac = new PrefixTreeCompleter();
        ac.initialize(dictionaryFileName);
        reset();
    }

    /**
     * opens a file
     */
    @FXML
    void open() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text & CSV Files", "*.txt", "*.csv"),
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                new FileChooser.ExtensionFilter("All Files", "*")
        );

        File selectedFile = fc.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                ac.initialize(selectedFile.toString());
                dictionaryFileName = selectedFile.toString();
                search.setDisable(false);
            } catch (FileNotFoundException fne) {
                throwAlert(new Alert(Alert.AlertType.ERROR), "File Not Found",
                        "File Not Found", "The file isn't there anymore!").show();
            }
        }
        reset();
        time.setText("Time Required: " + formatTime(ac.getLastOperationTime()));
        matchesCount.setText("Matches Loaded: " + ac.size());
    }

    /**
     * Called whenever a key is typed in the search field
     */
    @FXML
    void searchUpdate() {
        try {
            if (search.getText().length() >= 1) {
                List<String> strings = ac.allThatBeginsWith(search.getText().toLowerCase());

                StringBuilder output = new StringBuilder();
                for (String s : strings) {
                    output.append(s).append("\n");
                }

                matches.setText(output.toString());
                matchesCount.setText("matches Found: " + strings.size());
                time.setText("Time Required: " + formatTime(ac.getLastOperationTime()));
            } else {
                reset();
            }
        } catch (IllegalStateException ise) {
            throwAlert(new Alert(Alert.AlertType.ERROR), "Illegal State",
                    "The System entered an illegal state", "You must load a file first").show();
            search.setText("");
        }
    }

    /**
     * Called when program is launched
     */
    @FXML
    void initialize() {
        inject(Main.stage);
        ac = new PrefixTreeCompleter();
    }

    /**
     * Resets all of the text on screen to their default values
     */
    private void reset() {
        search.setText("");
        matches.setText("");
        matchesCount.setText("matches Found: 0");
        time.setText("Time Required: 0 nanoseconds");
    }

    /**
     * Gives this class a reference to the window
     *
     * @param stage the stage
     */
    private void inject(Stage stage) {
        this.stage = stage;
    }

    /**
     * Creates a dialog
     *
     * @param type    a dialog
     * @param title   the dialog's title
     * @param header  the header text
     * @param content the content text
     * @return the created dialog
     */
    private static Dialog throwAlert(Dialog type, String title, String header, String content) {
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
    private static double roundDecimal(double value) {
        final double modifier = 1000.0;
        return Math.round(value * modifier) / modifier;
    }

    /**
     * Turns the nanoTime into a readable format
     *
     * @param nanos the number of nanoseconds
     * @return a readable format
     */
    private static String formatTime(long nanos) {
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
