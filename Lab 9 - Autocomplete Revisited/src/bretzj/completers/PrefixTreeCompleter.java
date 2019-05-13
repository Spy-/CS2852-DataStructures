/*
 * Course: CS2852
 * Spring 2019
 * Lab 9 - Autocomplete Revisited
 * Name: John Bretz
 * Created: 5/7/19
 */
package bretzj.completers;

import bretzj.datastructure.PrefixTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * Completer object that uses a prefix tree to store and find words
 */
public class PrefixTreeCompleter extends BaseAutoCompleter {

    private PrefixTree tree;

    /**
     * Constructor
     */
    public PrefixTreeCompleter() {
        tree = new PrefixTree();
    }

    /**
     * Loads a text file
     * @param filename the file name
     * @return true if load was successful
     * @throws FileNotFoundException if the file can't be found
     */
    public boolean loadTextFile(String filename) throws FileNotFoundException {
        try (Scanner scan = new Scanner(new File(filename))) {
            tree = new PrefixTree();
            while (scan.hasNextLine()) {
                tree.insert(scan.nextLine());
            }
            return true;
        }
    }

    /**
     * Loads a csv file
     * @param filename the file name
     * @return true if load was successful
     * @throws FileNotFoundException if the file can't be found
     */
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

    /**
     * Finds all the words that start with the given prefix
     * @param prefix the prefix
     * @return a list of strings
     */
    @Override
    public List<String> allThatBeginsWith(String prefix) {
        start = System.nanoTime();
        List<String> list = tree.getWordsFromPrefix(prefix);
        end = System.nanoTime();
        return list;
    }

    /**
     * the number of words in the structure
     * @return the size
     */
    @Override
    public int size() {
        return tree.size();
    }

    /**
     * The name of the object
     * @return the name
     */
    @Override
    public String getName() {
        return "PrefixTree";
    }
}
