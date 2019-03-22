package bretzj;

import javafx.event.ActionEvent;
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

import static bretzj.Util.roundDecimal;

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
    void arrayListEnhanced(ActionEvent event) {
        strategy = Strategy.ARRAYLIST_ENHANCED;
        System.out.println(strategy);
    }

    @FXML
    void arrayListIndex(ActionEvent event) {
        strategy = Strategy.ARRAYLIST_INDEX;
        System.out.println(strategy);
    }

    @FXML
    void linkedListEnhanced(ActionEvent event) {
        strategy = Strategy.LINKEDLIST_ENHANCED;
        System.out.println(strategy);
    }

    @FXML
    void linkedListIndex(ActionEvent event) {
        strategy = Strategy.LINKEDLIST_INDEX;
        System.out.println(strategy);
    }

    @FXML
    void open(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                new FileChooser.ExtensionFilter("All Files", "*")
        );

        File selectedFile = fc.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                ac.load(selectedFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void searchUpdate(KeyEvent event) {
        long start, end;
        if (!search.getText().equalsIgnoreCase("")) {
            start = System.nanoTime();
            ArrayList<String> strings = ac.search(search.getText(), strategy);
            end = System.nanoTime();

            String output = "";
            for (String s : strings) {
                output += s + "\n";
            }

            Matches.setText(output);
            matchesCount.setText("Matches Found: " + strings.size());
            time.setText("Time Required: " + formatTime(end - start));
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
            ac.load(new File("words.txt"));
        } catch (FileNotFoundException ignored) {
        }
    }

    private void inject(Stage stage) {
        this.stage = stage;
    }

    private String formatTime(long nanos) {
        return "TODO";
    }
}
