package bretzj.completers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArrayIteratorCompleter extends BaseAutoCompleter {

    private List<String> words;

    public ArrayIteratorCompleter(List<String> list) {
        words = list;
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
            words.clear();
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
        start = System.nanoTime();
        for (String s : words) {
            if (s.startsWith(prefix)) {
                startsWith.add(s);
            }
        }
        end = System.nanoTime();
        return startsWith;
    }
}
