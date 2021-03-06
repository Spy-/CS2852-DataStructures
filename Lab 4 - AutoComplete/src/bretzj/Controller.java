/*
 * Course: CS2852
 * Spring 2019
 * Lab 4 - AutoComplete
 * Name: John Bretz
 * Created: 3/22/2019
 */
package bretzj;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static bretzj.Util.formatTime;
import static bretzj.Util.throwAlert;
import static java.lang.Math.abs;

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
    private AutoCompleter ac;
    private Strategy strategy = Strategy.ARRAYLIST_ENHANCED;
    private String previousSearch = "";

    /**
     * use ArrayList & enhanced for loop for search
     */
    @FXML
    void arrayListEnhanced() {
        strategy = Strategy.ARRAYLIST_ENHANCED;
        ac.reload();
        reset();
    }

    /**
     * use ArrayList & iteration loop for search
     */
    @FXML
    void arrayListIndex() {
        strategy = Strategy.ARRAYLIST_INDEX;
        ac.reload();
        reset();
    }

    /**
     * use LinkedList & enhanced for loop for search
     */
    @FXML
    void linkedListEnhanced() {
        strategy = Strategy.LINKEDLIST_ENHANCED;
        ac.reload();
        reset();
    }

    /**
     * use LinkedList & iteration loop for search
     */
    @FXML
    void linkedListIndex() {
        strategy = Strategy.LINKEDLIST_INDEX;
        ac.reload();
        reset();
    }

    /**
     * opens a file
     */
    @FXML
    void open() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                new FileChooser.ExtensionFilter("All Files", "*")
        );

        File selectedFile = fc.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                ac.initialize(selectedFile.toString());
            } catch (FileNotFoundException fne) {
                throwAlert(new Alert(Alert.AlertType.ERROR), "File Not Found",
                        "File Not Found", "The file isn't there anymore!").show();
            }
        }
        reset();
    }

    /**
     * Called whenever a key is typed in the search field
     *
     * @param event the JavaFx event
     */
    @FXML
    void searchUpdate(KeyEvent event) {
        try {
            if (abs(previousSearch.length() - search.getText().length()) <= 1) {
                ArrayList<String> strings = ac.allThatBeginsWith(search.getText(),
                        strategy, event.getCharacter().equals("\b"));

                StringBuilder output = new StringBuilder();
                for (String s : strings) {
                    output.append(s).append("\n");
                }

                previousSearch = search.getText();
                matches.setText(output.toString());
                matchesCount.setText("matches Found: " + strings.size());
                time.setText("Time Required: " + formatTime(ac.getLastOperationTime()));
            } else {
                reset();
                ac.flush();
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
        ac = new AutoCompleter(new ArrayList<>());
//        try {
//            ac.initialize("words.txt");
//        } catch (FileNotFoundException ignored) {
//        }
    }

    /**
     * Resets all of the text on screen to their default values
     */
    private void reset() {
        search.setText("");
        matches.setText("");
        matchesCount.setText("matches Found: 0");
        time.setText("Time Required: 0 nanoseconds");
        previousSearch = "";
    }

    /**
     * Gives this class a reference to the window
     *
     * @param stage the stage
     */
    private void inject(Stage stage) {
        this.stage = stage;
    }
}
