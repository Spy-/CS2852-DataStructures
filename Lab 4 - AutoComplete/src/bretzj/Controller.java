package bretzj;

import javafx.fxml.FXML;
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
import static java.lang.Math.abs;

public class Controller {
    @FXML
    private TextField search;

    @FXML
    private TextArea Matches;

    @FXML
    private Label time;

    @FXML
    private Label matchesCount;

    private Stage stage;
    private AutoCompleter ac;
    private Strategy strategy = Strategy.ARRAYLIST_ENHANCED;

    @FXML
    void arrayListEnhanced() {
        strategy = Strategy.ARRAYLIST_ENHANCED;
        System.out.println(strategy);
    }

    @FXML
    void arrayListIndex() {
        strategy = Strategy.ARRAYLIST_INDEX;
        System.out.println(strategy);
    }

    @FXML
    void linkedListEnhanced() {
        strategy = Strategy.LINKEDLIST_ENHANCED;
        System.out.println(strategy);
    }

    @FXML
    void linkedListIndex() {
        strategy = Strategy.LINKEDLIST_INDEX;
        System.out.println(strategy);
    }

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
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void searchUpdate(KeyEvent event) {
        if (!search.getText().equalsIgnoreCase("")) {
            ArrayList<String> strings = ac.allThatBeginsWith(search.getText(), strategy);

            String output = "";
            for (String s : strings) {
                output += s + "\n";
            }

            Matches.setText(output);
            matchesCount.setText("Matches Found: " + strings.size());
            time.setText("Time Required: " + formatTime());
        } else {
            Matches.setText("");
            matchesCount.setText("Matches Found: 0");
            time.setText("Time Required: 00:00.000");
        }
    }

    @FXML
    void initialize() {
        inject(Main.stage);
        ac = new AutoCompleter(new ArrayList<>());
        try {
            ac.initialize("2000words.txt");
        } catch (FileNotFoundException ignored) {
        }
    }

    private void inject(Stage stage) {
        this.stage = stage;
    }

    private String formatTime() {
        // less then us -> show nanos
        // less then ms -> show micro
        // less then s  -> show ms
        // else         -> 00:00.000

        long nanos = ac.getLastOperationTime();

        if (nanos < 1000) {
            return nanos + " nanoseconds";
        } else if (nanos < 1000000) {
            return roundDecimal(nanos / 1000.0) + " microseconds";
        } else if (nanos < 1000000000) {
            return roundDecimal(nanos / 1000000.0) + " milliseconds";
        }
        return "XX:XX.xxx";
    }
}
