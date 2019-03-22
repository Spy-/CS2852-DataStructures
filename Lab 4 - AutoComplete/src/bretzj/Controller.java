package bretzj;

import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private ToggleGroup strat;

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
    private String previousSearch = "";

    @FXML
    void arrayListEnhanced() {
        strategy = Strategy.ARRAYLIST_ENHANCED;
    }

    @FXML
    void arrayListIndex() {
        strategy = Strategy.ARRAYLIST_INDEX;
    }

    @FXML
    void linkedListEnhanced() {
        strategy = Strategy.LINKEDLIST_ENHANCED;
    }

    @FXML
    void linkedListIndex() {
        strategy = Strategy.LINKEDLIST_INDEX;
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
        if (abs(previousSearch.length() - search.getText().length()) <= 1) {
            ArrayList<String> strings = ac.allThatBeginsWith(search.getText(), strategy, event.getCharacter().equals("\b"));

            String output = "";
            for (String s : strings) {
                output += s + "\n";
            }

            previousSearch = search.getText();
            Matches.setText(output);
            matchesCount.setText("Matches Found: " + strings.size());
            time.setText("Time Required: " + formatTime(ac.getLastOperationTime()));
        } else {
            search.setText("");
            Matches.setText("");
            matchesCount.setText("Matches Found: 0");
            time.setText("Time Required: 0 nanoseconds");
            previousSearch = "";
            ac.flush();
        }
    }

    @FXML
    void initialize() {
        inject(Main.stage);
        ac = new AutoCompleter(new ArrayList<>());
        try {
            ac.initialize("words.txt");
        } catch (FileNotFoundException ignored) {
        }
    }

    private void inject(Stage stage) {
        this.stage = stage;
    }
}
