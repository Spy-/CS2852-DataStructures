package bretzj;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Picture {

    private static ArrayList<Dot> dots = new ArrayList<>();

    public static void readDotFile(File file) throws FileNotFoundException {
        Scanner scan = new Scanner(file);
        dots.clear();

        while (scan.hasNextLine()) {
            String[] coord = scan.nextLine().split(",");
            dots.add(new Dot(Double.parseDouble(coord[0]), Double.parseDouble(coord[1])));
        }
    }

    public static void drawDots(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        for (Dot d : dots) {
            gc.fillOval(d.getX() - 3.5, d.getY() - 3.5, 7, 7);
        }
    }

    public static void drawLines(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.beginPath();
        gc.moveTo(dots.get(0).getX(), dots.get(0).getY());

        for (int i = 1; i < dots.size(); i++) {
            Dot dot = dots.get(i);
            gc.lineTo(dot.getX(), dot.getY());
        }

        gc.closePath();
        gc.stroke();
    }
}
