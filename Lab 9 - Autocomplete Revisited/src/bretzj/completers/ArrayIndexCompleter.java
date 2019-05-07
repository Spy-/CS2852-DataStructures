package bretzj.completers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArrayIndexCompleter extends BaseAutoCompleter {

    private ArrayList<String> words;

    public ArrayIndexCompleter() {
        words = new ArrayList<>();
    }

    @Override
    public boolean loadTextFile(String filename) throws FileNotFoundException {
        try (Scanner scan = new Scanner(new File(filename))) {
            words.clear();
            while (scan.hasNextLine()) {
                words.add(scan.nextLine());
            }
            return true;
        }
    }

    @Override
    public boolean loadCSVFile(String filename) throws FileNotFoundException {
        try (Scanner scan = new Scanner(new File(filename))) {
            while (scan.hasNextLine()) {
                String[] part = scan.nextLine().split(",");
                words.add(part[1]);
            }
            return true;
        }
    }

    @Override
    public int size() {
        return words.size();
    }

    @Override
    public List<String> allThatBeginsWith(String prefix) {
        ArrayList<String> startsWith = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            String s = words.get(i);
            if (s.startsWith(prefix)) {
                startsWith.add(s);
            }
        }
        return startsWith;
    }
}

