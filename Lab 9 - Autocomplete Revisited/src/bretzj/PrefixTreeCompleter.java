package bretzj;

import bretzj.dataStructure.PrefixTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class PrefixTreeCompleter extends BaseAutoCompleter {

    private PrefixTree tree;

    public PrefixTreeCompleter() {
        tree = new PrefixTree();
    }

    @Override
    public void initialize(String filename) throws FileNotFoundException {
        start = System.nanoTime();
        if (filename.endsWith(".txt")) {
            dictionaryLoaded = loadTextFile(filename);
        } else if (filename.endsWith(".csv")) {
            dictionaryLoaded = loadCSVFile(filename);
        }
        end = System.nanoTime();
    }

    private boolean loadTextFile(String filename) throws FileNotFoundException {
        try (Scanner scan = new Scanner(new File(filename))) {
            while (scan.hasNextLine()) {
                tree.insert(scan.nextLine());
            }
            return true;
        }
    }

    private boolean loadCSVFile(String filename) throws FileNotFoundException {
        try (Scanner scan = new Scanner(new File(filename))) {
            while (scan.hasNextLine()) {
                tree.insert(scan.nextLine());
            }
            return true;
        }
    } //todo

    @Override
    public List<String> allThatBeginsWith(String prefix) {
        start = System.nanoTime();
        List<String> list = tree.getWordsFromPrefix(prefix);
        end = System.nanoTime();
        return list;
    }
}
