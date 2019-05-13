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
 * An Autocompleter object that uses indexes to traverse through a list
 */
public class ArrayIndexCompleter extends BaseAutoCompleter {

    private List<String> words;

    /**
     * Constructor
     *
     * @param list an empty list
     */
    public ArrayIndexCompleter(List<String> list) {
        words = list;
    }

    /**
     * Loads a .txt file
     *
     * @param filename the file name
     * @return true if the file successfully loaded
     * @throws FileNotFoundException if the the file cant be found
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
     * Loads a .csv file
     *
     * @param filename the file name
     * @return true if the file successfully loaded
     * @throws FileNotFoundException if the the file cant be found
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
     * The number of words stored in the internal data structure
     *
     * @return the size
     */
    @Override
    public int size() {
        return words.size();
    }

    /**
     * The Autocompleter's name
     *
     * @return the name
     */
    @Override
    public String getName() {
        return words instanceof ArrayList ? "ArrayIndex" : "LinkedIndex";
    }

    /**
     * Finds all the words that start with the prefix
     *
     * @param prefix the prefix
     * @return a list of all valid words
     */
    @Override
    public List<String> allThatBeginsWith(String prefix) {
        ArrayList<String> startsWith = new ArrayList<>();
        start = System.nanoTime();
        for (int i = 0; i < words.size(); i++) {
            String s = words.get(i);
            if (s.startsWith(prefix)) {
                startsWith.add(s);
            }
        }
        end = System.nanoTime();
        return startsWith;
    }
}

