/*
 * Course: CS2852
 * Spring 2019
 * Lab 4 - AutoComplete
 * Name: John Bretz
 * Created: 3/26/2019
 */
package bretzj;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/*
 * Benchmark Discussion
 *
 * For results see Benchmark.md
 *
 * The testing was done simply be running the program, picking a strategy and seeing the time
 * required to finish the search. Unsurprisingly the both index methods were slower than the
 * enhanced for loops. A bit more surprisingly was the the iterator for the ArrayList was
 * almost twice as fast as it's LinkedList counterpart.
 */

/**
 * Interface for required methods for an auto complete implementation;
 */
public interface AutoComplete {
    /**
     * Loads a dictionary file
     *
     * @param filename the filename
     * @throws FileNotFoundException if the file isn't found
     */
    void initialize(String filename) throws FileNotFoundException;

    /**
     * Returns a list of strings that start with the given prefix.
     *
     * @param prefix      the prefix
     * @param strategy    how the program should search for valid strings
     * @param isBackspace was the key a backspace?
     * @return a list of strings
     */
    ArrayList<String> allThatBeginsWith(String prefix, Strategy strategy, boolean isBackspace);

    /**
     * How long is took to do the last search
     *
     * @return the time in nanoseconds
     */
    long getLastOperationTime();
}
