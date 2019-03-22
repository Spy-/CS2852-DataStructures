package bretzj;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class AutoCompleter {

    private ArrayList<String> array;
    private LinkedList<String> linked;

    public AutoCompleter(List<String> list) {
        this.array = new ArrayList<>(list);
        this.linked = new LinkedList<>(list);
    }

    public void load(File file) throws FileNotFoundException {
        String fileName = file.toString().toLowerCase();

        try (Scanner fileScan = new Scanner(file)) {
            if (fileName.endsWith(".txt")) {
                loadTextFile(fileScan);
            } else if (fileName.endsWith(".csv")) {
                loadCSVFile(fileScan);
            }
        }
    }

    private void loadCSVFile(Scanner scan) {
        System.out.println("CSV");
    }

    private void loadTextFile(Scanner scan) {
        while (scan.hasNextLine()) {
            array.add(scan.nextLine());
        }
        System.out.println("Loaded " + array.size() + " elements.");
    }

    public ArrayList<String> search(String text, Strategy strategy) {
        ArrayList<String> strings = new ArrayList<>();

        switch (strategy) {
            case ARRAYLIST_INDEX:
                for (int i = 0; i < array.size(); i++) {
                    String word = array.get(i);
                    if (word.startsWith(text)) {
                        strings.add(word);
                    }
                }
                break;
            case LINKEDLIST_INDEX:
                for (int i = 0; i < linked.size(); i++) {
                    String word = linked.get(i);
                    if (word.startsWith(text)) {
                        strings.add(word);
                    }
                }
                break;
            case ARRAYLIST_ENHANCED:
                for (String s : array) {
                    if (s.startsWith(text)) {
                        strings.add(s);
                    }
                }
                break;
            case LINKEDLIST_ENHANCED:
                for (String s : linked) {
                    if (s.startsWith(text)) {
                        strings.add(s);
                    }
                }
                break;
        }
        return strings;
    }
}
