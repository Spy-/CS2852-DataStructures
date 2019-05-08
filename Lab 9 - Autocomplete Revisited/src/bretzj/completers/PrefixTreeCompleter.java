package bretzj.completers;

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

    public boolean loadTextFile(String filename) throws FileNotFoundException {
        try (Scanner scan = new Scanner(new File(filename))) {
            tree = new PrefixTree();
            while (scan.hasNextLine()) {
                tree.insert(scan.nextLine());
            }
            return true;
        }
    }

    public boolean loadCSVFile(String filename) throws FileNotFoundException {
        try (Scanner scan = new Scanner(new File(filename))) {
            tree = new PrefixTree();
            while (scan.hasNextLine()) {
                String[] part = scan.nextLine().split(",");
                tree.insert(part[1]);
            }
            return true;
        }
    }

    @Override
    public List<String> allThatBeginsWith(String prefix) {
        start = System.nanoTime();
        List<String> list = tree.getWordsFromPrefix(prefix);
        end = System.nanoTime();
        return list;
    }

    @Override
    public int size() {
        return tree.size();
    }
}
