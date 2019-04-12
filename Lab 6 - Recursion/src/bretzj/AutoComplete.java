/*
 * Course: CS2852
 * Spring 2019
 * Lab 6 - Recursion
 * Name: John Bretz
 * Created: 4/5/2019
 */
package bretzj;

import edu.msoe.cs2852.SortedArrayList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Class for AutoCompleting words
 */
public class AutoComplete {

    private ArrayList<String> words = new ArrayList<>();
    private SortedArrayList<String> sorted = new SortedArrayList<>();
    private boolean isInitialized = false;
    private AutoCompleter method;

    /**
     * Constructor. Used by the Factory methods to properly create objects
     *
     * @param method an AutoCompleter
     */
    private AutoComplete(AutoCompleter method) {
        this.method = method;
    }

    /**
     * Loads a dictionary file
     *
     * @param filename the file name
     * @throws FileNotFoundException if the file does not exist
     */
    public void initialize(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        words.clear();
        sorted.clear();

        while (scanner.hasNextLine()) {
            words.add(scanner.nextLine());
        }

        // get rid of duplicates
        Set<String> unique = new HashSet<>(words);

        words = new ArrayList<>(unique);
        sorted.addAll(unique);

        isInitialized = true;
    }

    /**
     * Returns true if the dictionary has the given word
     *
     * @param target the word
     * @return true if the word is in the dictionary
     */
    public boolean contains(String target) {
        return method.useSorted() ? method.contains(target, sorted) : method.contains(target, words);
    }

    /**
     * does the dictionary have at least one word that starts with the prefix
     *
     * @param prefix the prefix
     * @return true if the prefix is valid
     */
    public boolean hasPrefix(String prefix) {
        if (isInitialized) {
            return method.allThatBeginsWith(prefix, words).size() > 0;
        } else {
            throw new IllegalStateException("Must call initialize");
        }
    }

    /**
     * Checks if a dictionary was loaded
     *
     * @return true if it was loaded
     */
    public boolean isInitialized() {
        return isInitialized;
    }

    /**
     * Creates an AutoComplete object that uses an Indexed ArrayList for searching
     *
     * @return the created object
     */
    public static AutoComplete arrayIndexFactory() {
        return new AutoComplete((prefix, words) -> {
            ArrayList<String> matches = new ArrayList<>();
            for (int i = 0; i < words.size(); i++) {
                String word = words.get(i);
                if (word.startsWith(prefix)) {
                    matches.add(word);
                }
            }
            return matches;
        });
    }

    /**
     * Creates an AutoComplete object that uses an Iterated ArrayList for searching
     *
     * @return the created object
     */
    public static AutoComplete arrayIteratorFactory() {
        return new AutoComplete((prefix, words) -> {
            ArrayList<String> matches = new ArrayList<>();
            for (String word : words) {
                if (word.startsWith(prefix)) {
                    matches.add(word);
                }
            }
            return matches;
        });
    }

    /**
     * Creates an AutoComplete object that uses an Indexed LinkedList for searching
     *
     * @return the created object
     */
    public static AutoComplete linkedIndexFactory() {
        return new AutoComplete((prefix, words) -> {
            ArrayList<String> matches = new ArrayList<>();
            LinkedList<String> linkedList = new LinkedList<>(words);
            for (int i = 0; i < linkedList.size(); i++) {
                String word = linkedList.get(i);
                if (word.startsWith(prefix)) {
                    matches.add(word);
                }
            }
            return matches;
        });
    }

    /**
     * Creates an AutoComplete object that uses an Iterated LinkedList for searching
     *
     * @return the created object
     */
    public static AutoComplete linkedIteratorFactory() {
        return new AutoComplete((prefix, words) -> {
            ArrayList<String> matches = new ArrayList<>();
            LinkedList<String> linkedList = new LinkedList<>(words);

            for (String word : linkedList) {
                if (word.startsWith(prefix)) {
                    matches.add(word);
                }
            }
            return matches;
        });
    }

    /**
     * Creates an AutoComplete object that uses the SortedArrayList for searching
     *
     * @return the created object
     */
    public static AutoComplete sortedArrayFactory() {
        return new AutoComplete(new AutoCompleter() {
            @Override
            public ArrayList<String> allThatBeginsWith(String prefix, List<String> words) {
                ArrayList<String> matches = new ArrayList<>();

                for (String word : words) {
                    if (word.startsWith(prefix)) {
                        matches.add(word);
                    }
                }
                return matches;
            }

            @Override
            public boolean contains(String target, List<String> words) {
                return words.contains(target);
            }

            @Override
            public boolean useSorted() {
                return true;
            }
        });
    }
}
