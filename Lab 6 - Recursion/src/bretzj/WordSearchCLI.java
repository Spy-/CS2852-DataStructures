/*
 * Course: CS2852
 * Spring 2019
 * Lab 6 - Recursion
 * Name: John Bretz
 * Created: 4/5/2019
 */
package bretzj;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;

import static bretzj.AutoComplete.*;

/**
 * Main class for Lab 6
 */
public class WordSearchCLI {

    private static File grid;
    private static File dictionary;
    private static Strategy strategy;

    private static AutoComplete autoCompleter;
    private static GameBoard gameBoard;
    private static List<String> words;
    private static long start, end;

    /**
     * Main entry point into program
     *
     * @param args some cmd line arguments
     * @throws FileNotFoundException if the desired files are not found
     */
    public static void main(String[] args) throws FileNotFoundException {
        if (args != null && args.length == 3) {
            readArgs(args);
            setupAutoCompleter();
            setupBoard();
            findWords();
            printStats();
        } else {
            System.out.println("Must pass a grid file, a dictionary, and a sort method");
        }
    }

    /**
     * Reads the arguments and creates the required variables
     *
     * @param args the cmd line arguments
     */
    private static void readArgs(String[] args) {
        System.out.println(Arrays.toString(args));

        grid = new File(args[0]);
        dictionary = new File(args[1]);

        switch (args[2]) {
            case "SortedArrayList":
                strategy = Strategy.SORTED_LIST;
                break;
            case "ArrayListIndexed":
                strategy = Strategy.ARRAYLIST_INDEX;
                break;
            case "LinkedListIndexed":
                strategy = Strategy.LINKEDLIST_INDEX;
                break;
            case "ArrayListIterated":
                strategy = Strategy.ARRAYLIST_ENHANCED;
                break;
            case "LinkedListIterated":
                strategy = Strategy.LINKEDLIST_ENHANCED;
                break;
            default:
                System.out.println("Invalid Search Strategy");
                System.out.println("Valid strategies are:");
                System.out.println("\tSortedArrayList");
                System.out.println("\tArrayListIndexed");
                System.out.println("\tLinkedListIndexed");
                System.out.println("\tArrayListIterated");
                System.out.println("\tLinkedListIterated");
                System.exit(0);
        }
    }

    /**
     * Creates the proper AutoCompleter and initializes it with the dictionary file
     *
     * @throws FileNotFoundException if the dictionary file does not exist
     */
    private static void setupAutoCompleter() throws FileNotFoundException {
        switch (strategy) {

            case ARRAYLIST_ENHANCED:
                autoCompleter = arrayIteratorFactory();
                break;
            case ARRAYLIST_INDEX:
                autoCompleter = arrayIndexFactory();
                break;
            case LINKEDLIST_ENHANCED:
                autoCompleter = linkedIteratorFactory();
                break;
            case LINKEDLIST_INDEX:
                autoCompleter = linkedIndexFactory();
                break;
            case SORTED_LIST:
                autoCompleter = sortedArrayFactory();
                break;
        }

        autoCompleter.initialize(dictionary.getName());
    }

    /**
     * Creates and loads the gameBoard
     */
    private static void setupBoard() {
        gameBoard = new GameBoard(autoCompleter);
        gameBoard.load(grid.toPath());
    }

    /**
     * Tells the game board to find the words
     */
    private static void findWords() {
        start = System.currentTimeMillis();
        words = gameBoard.findWords();
        end = System.currentTimeMillis();
    }

    /**
     * prints the words, word count, and time it took to execute
     */
    private static void printStats() {
        Set<String> unique = new HashSet<>(words);
        for (String s : words) {
            System.out.println(s);
        }

        System.out.println("Found " + words.size() + " words of which " +
                unique.size() + " are unique.");

        System.out.println(new SimpleDateFormat("mm:ss.SSS").format(new Date(end - start)));
    }
}
