package bretzj;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;

public class Controller {
    @FXML
    private Canvas picture;

    @FXML
    void close(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void dotsOnly(ActionEvent event) {
        picture.getGraphicsContext2D().clearRect(0, 0, Main.WIDTH, Main.HEIGHT);
        Picture.drawDots(picture);
    }

    @FXML
    void linesOnly(ActionEvent event) {
        picture.getGraphicsContext2D().clearRect(0, 0, Main.WIDTH, Main.HEIGHT);
        Picture.drawLines(picture);
    }

    @FXML
    void open(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open Dot File");
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Dot Files", "*.dot")
        );

        File selectedFile = fc.showOpenDialog(Main.stage);

        try {
            Picture.readDotFile(selectedFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        try {
//            Picture.readDotFile(new File("balloon100.dot"));
//            Picture.readDotFile(new File("circle.dot"));
            Picture.readDotFile(new File("magician.dot"));
            Picture.drawDots(picture);
            Picture.drawLines(picture);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}