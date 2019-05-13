/*
 * Course: CS2852
 * Spring 2019
 * Lab 9 - Autocomplete Revisited
 * Name: John Bretz
 * Created: 5/7/19
 */
package bretzj.completers;

import java.io.FileNotFoundException;

/**
 * The Basis for an AutoCompleter object
 */
public abstract class BaseAutoCompleter implements AutoCompleter {

    protected long start;
    protected long end;
    private boolean dictionaryLoaded = false;

    /**
     * Loads a dictionary file
     *
     * @param filename the filename
     * @throws FileNotFoundException if the file cant be found
     */
    @Override
    public void initialize(String filename) throws FileNotFoundException {
        start = System.nanoTime();
        if (filename.endsWith(".txt")) {
            dictionaryLoaded = loadTextFile(filename);
        } else if (filename.endsWith(".csv")) {
            dictionaryLoaded = loadCSVFile(filename);
        } else {
            throw new IllegalArgumentException("The File must be a .txt or .csv");
        }
        end = System.nanoTime();
        dictionaryLoaded = true;
    }

    /**
     * Loads a .txt file
     *
     * @param filename the file name
     * @return true if the file successfully loaded
     * @throws FileNotFoundException if the the file cant be found
     */
    public abstract boolean loadTextFile(String filename) throws FileNotFoundException;

    /**
     * Loads a .csv file
     *
     * @param filename the file name
     * @return true if the file successfully loaded
     * @throws FileNotFoundException if the the file cant be found
     */
    public abstract boolean loadCSVFile(String filename) throws FileNotFoundException;

    /**
     * How long the last allThatBeginsWith() or initialize() call took to execute
     *
     * @return the time in nanoseconds
     */
    @Override
    public long getLastOperationTime() {
        if (dictionaryLoaded) {
            return end - start;
        }
        throw new IllegalStateException("Must call Initialize() prior to calling this method");
    }

    /**
     * The number of words stored in the internal data structure
     *
     * @return the size
     */
    public abstract int size();

    /**
     * The Autocompleter's name
     *
     * @return the name
     */
    public abstract String getName();
}
