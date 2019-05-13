/*
 * Course: CS2852
 * Spring 2019
 * Lab 9 - Autocomplete Revisited
 * Name: John Bretz
 * Created: 5/7/19
 */
package bretzj.completers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A completer that uses Iterators and list's
 */
public class ArrayIteratorCompleter extends BaseAutoCompleter {

    private List<String> words;

    /**
     * Constructor
     * @param list a list to use to store words
     */
    public ArrayIteratorCompleter(List<String> list) {
        words = list;
        words.clear();
    }

    /**
     * Loads a text file
     * @param filename the file name
     * @return true if load was successful
     * @throws FileNotFoundException if the file can't be found
     */
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

    /**
     * Loads a csv file
     * @param filename the file name
     * @return true if load was successful
     * @throws FileNotFoundException if the file can't be found
     */
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

    /**
     * The number of words in the structure
     * @return the size
     */
    @Override
    public int size() {
        return words.size();
    }

    /**
     * The name of the object
     * @return the name
     */
    @Override
    public String getName() {
        return words instanceof ArrayList ? "ArrayIterator" : "LinkedIterator";
    }

    /**
     * Finds all the words that start with the given prefix
     * @param prefix the prefix
     * @return a list of strings
     */
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
