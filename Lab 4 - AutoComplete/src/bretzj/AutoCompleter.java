package bretzj;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class AutoCompleter {

    private ArrayList<String> words;
    private long start, end;

    public AutoCompleter(List<String> list) {
        this.words = new ArrayList<>(list);
    }

    public void initialize(String fileName) throws FileNotFoundException {
        File file = new File(fileName);

        try (Scanner fileScan = new Scanner(file)) {
            if (fileName.endsWith(".txt")) {
                loadTextFile(fileScan);
            } else if (fileName.endsWith(".csv")) {
                loadCSVFile(fileScan);
            }
        }
    }

    private void loadCSVFile(Scanner scan) {
        System.out.println("CSV TODO");
    }

    private void loadTextFile(Scanner scan) {
        words.clear();
        String word;

        while (scan.hasNextLine()) {
            words.add(scan.nextLine());
        }
        System.out.println("Loaded " + words.size() + " elements.");
    }

    public ArrayList<String> allThatBeginsWith(String text, Strategy strategy) {
        ArrayList<String> strings = new ArrayList<>();
        ArrayList<String> array = new ArrayList<>(words);
        LinkedList<String> linked = new LinkedList<>(words);

        start = System.nanoTime();
        switch (strategy) {
            case ARRAYLIST_INDEX:
                System.out.println("Using " + strategy + " to find " + text);
                for (int i = 0; i < array.size(); i++) {
                    String word = array.get(i);
                    if (word.startsWith(text)) {
                        strings.add(word);
                    }
                }
                break;
            case LINKEDLIST_INDEX:
                System.out.println("Using " + strategy + " to find " + text);
                for (int i = 0; i < linked.size(); i++) {
                    String word = linked.get(i);
                    if (word.startsWith(text)) {
                        strings.add(word);
                    }
                }
                break;
            case ARRAYLIST_ENHANCED:
                System.out.println("Using " + strategy + " to find " + text);
                for (String s : array) {
                    if (s.startsWith(text)) {
                        strings.add(s);
                    }
                }
                break;
            case LINKEDLIST_ENHANCED:
                System.out.println("Using " + strategy + " to find " + text);
                for (String s : linked) {
                    if (s.startsWith(text)) {
                        strings.add(s);
                    }
                }
                break;
        }
        end = System.nanoTime();
        return strings;
    }

    public long getLastOperationTime() {
        return end - start;
    }
}
