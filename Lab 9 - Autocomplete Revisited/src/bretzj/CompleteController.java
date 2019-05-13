/*
 * Course: CS2852
 * Spring 2019
 * Lab 9 - Autocomplete Revisited
 * Name: John Bretz
 * Created: 5/7/19
 */
package bretzj;

import bretzj.completers.ArrayIndexCompleter;
import bretzj.completers.ArrayIteratorCompleter;
import bretzj.completers.BaseAutoCompleter;
import bretzj.completers.PrefixTreeCompleter;
import bretzj.completers.SortedArrayCompleter;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * JavaFx Controller
 */
public class CompleteController {

    /**
     * The search field
     */
    public TextField search;
    /**
     * The matches text box
     */
    public TextArea matches;
    /**
     * The number of matches label
     */
    public Label matchCount;
    /**
     * The label for the Prefix Tree completer
     */
    public Label prefixTree;
    /**
     * The label for the SortedArrayList completer
     */
    public Label sortedArray;
    /**
     * The label for the ArrayList Index completer
     */
    public Label arrayIndex;

    /**
     * The label for the ArrayList Iterator completer
     */
    public Label arrayIterator;
    /**
     * The label for the LinkedList Index completer
     */
    public Label linkedIndex;
    /**
     * The label for the LinkedList Iterator completer
     */
    public Label linkedIterator;
    private final Label[] times = new Label[6];

    private final BaseAutoCompleter[] completers = new BaseAutoCompleter[] {
            new PrefixTreeCompleter(),
            new SortedArrayCompleter(),
            new ArrayIndexCompleter(new ArrayList<>()),
            new ArrayIteratorCompleter(new ArrayList<>()),
            new ArrayIndexCompleter(new LinkedList<>()),
            new ArrayIteratorCompleter(new LinkedList<>())
    };

    /**
     * Creates an open dialog to initialize the completers with a dictionary file
     */
    public void open() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Dict Files", "*.txt", "*.csv"),
                new FileChooser.ExtensionFilter("All Files", "*")
        );

        File selectedFile = fc.showOpenDialog(Main.stage);

        if (selectedFile != null) {
            try {
                for (BaseAutoCompleter b : completers) {
                    b.initialize(selectedFile.toString());
                }
                search.setDisable(false);
            } catch (FileNotFoundException e) {
                throwAlert(new Alert(Alert.AlertType.ERROR), "File Not Found",
                        "File Not Found", "The file isn't there anymore!").show();
            }
        }
        reset();
        matches.setText("Matches Loaded: " + completers[0].size());
        updateTimes();
    }

    /**
     * Resets all the text fields
     */
    private void reset() {
        search.setText("");
        matches.setText("Matches ");
        matchCount.setText("");
    }

    /**
     * Called by JavaFX at start of program
     */
    public void initialize() {
        times[0] = prefixTree;
        times[1] = sortedArray;
        times[2] = arrayIndex;
        times[3] = arrayIterator;
        times[4] = linkedIndex;
        times[5] = linkedIterator;
    }

    /**
     * Updates the time fields
     */
    private void updateTimes() {
        for (int i = 0; i < completers.length; i++) {
            BaseAutoCompleter b = completers[i];
            times[i].setText(b.getName() + " : " + formatTime(b.getLastOperationTime()));
        }
    }

    /**
     * calls allThatBeginsWith() on each completer when the search field is updated
     */
    public void searchUpdate() {
        try {
            String text = search.getText().toLowerCase();
            if (text.length() > 0) {
                List<String> foundWords = completers[0].allThatBeginsWith(text);
                IntStream.range(1, completers.length).forEach(
                        i -> completers[i].allThatBeginsWith(text)
                );
                StringBuilder sb = new StringBuilder();

                for (String s : foundWords) {
                    sb.append(s).append("\n");
                }

                matches.setText(sb.toString());
                matchCount.setText("Matches Found: " + foundWords.size());
                updateTimes();
            }
        } catch (IllegalStateException e) {
            throwAlert(new Alert(Alert.AlertType.ERROR), "Illegal State",
                    "The System entered an illegal state", "You must load a file first").show();
            search.setText("");
        }
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
            return nanos + " ns";
        } else if (nanos < milli) {
            return roundDecimal(nanos / micro) + " us";
        } else if (nanos < secon) {
            return roundDecimal(nanos / milli) + " ms";
        }

        return new SimpleDateFormat("mm:ss.SSS").format(new Date((long) (nanos / milli)));
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
}
