/*
 * Course: CS2852
 * Spring 2019
 * Lab 9 - Autocomplete Revisited
 * Name: John Bretz
 * Created: 5/7/19
 */
package bretzj.completers;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Interface for an Autocomplete object
 */
public interface AutoCompleter {
    /**
     * Initializes the object with dictionary
     *
     * @param filename the dictionary's file name
     * @throws FileNotFoundException if the file isn't found
     */
    void initialize(String filename) throws FileNotFoundException;

    /**
     * Finds all the words that start with the prefix
     *
     * @param prefix the prefix
     * @return a list of words
     */
    List<String> allThatBeginsWith(String prefix);

    /**
     * How long the last allThatBeginsWith() or initialize() took to execute
     *
     * @return the time in nanoseconds
     */
    long getLastOperationTime();
}
