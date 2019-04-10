/*
 * Course: CS2852
 * Spring 2019
 * Lab 6 - Recursion
 * Name: John Bretz
 * Created: 4/5/2019
 */
package bretzj;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface for the required methods in for every autoCompleter
 */
public interface AutoCompleter {
    /**
     * Returns a list of all words that start with the given prefix inside the given list
     *
     * @param prefix the prefix to search for
     * @param list   the list of words to search through
     * @return a list of matches
     */
    ArrayList<String> allThatBeginsWith(String prefix, List<String> list);

    /**
     * Does the dictionary have the desired word
     *
     * @param target the word
     * @return true if the word is in the list
     */
    default boolean contains(String target, List<String> words) {
        return words.contains(target);
    }
}
