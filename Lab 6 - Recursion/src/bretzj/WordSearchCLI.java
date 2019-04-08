package bretzj;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;

import static bretzj.AutoComplete.*;

public class WordSearchCLI {

    private static File grid;
    private static File dictionary;
    private static Strategy strategy;

    private static AutoComplete autoCompleter;
    private static GameBoard gameBoard;
    private static List<String> words;
    private static long start, end;

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

    private static void setupAutoCompleter() throws FileNotFoundException {
        switch (strategy) {

            case ARRAYLIST_ENHANCED:
                autoCompleter = ArrayIteratorFactory();
                break;
            case ARRAYLIST_INDEX:
                autoCompleter = ArrayIndexFactory();
                break;
            case LINKEDLIST_ENHANCED:
                autoCompleter = LinkedIteratorFactory();
                break;
            case LINKEDLIST_INDEX:
                autoCompleter = LinkedIndexFactory();
                break;
            case SORTED_LIST:
                autoCompleter = SortedArrayFactory();
                break;
        }

        autoCompleter.initialize(dictionary.getName());
    }

    private static void setupBoard() {
        gameBoard = new GameBoard(autoCompleter);
        gameBoard.load(grid.toPath());
    }

    private static void findWords() {
        start = System.currentTimeMillis();
        words = gameBoard.findWords();
        end = System.currentTimeMillis();
    }

    private static void printStats() {
        Set<String> unique = new HashSet<>(words);
        for (String s : words) {
            System.out.println(s);
        }

        System.out.println("Found " + words.size() + " words with " + unique.size() + " unique words.");

        System.out.println(new SimpleDateFormat("mm:ss.SSS").format(new Date(end - start)));
    }
}
