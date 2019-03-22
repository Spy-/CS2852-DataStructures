/*
 * Course: CS2852
 * Spring 2019
 * Lab 4 - AutoComplete
 * Name: John Bretz
 * Created: 3/22/2019
 */
package bretzj;

/**
 * Enumeration for the possible search strategies
 */
public enum Strategy {
    /**
     * use an ArrayList in an enhanced for loop
     */
    ARRAYLIST_ENHANCED,
    /**
     * use an ArrayList in an iteration loop
     */
    ARRAYLIST_INDEX,
    /**
     * use a LinkedList in an enhanced for loop
     */
    LINKEDLIST_ENHANCED,
    /**
     * use a LinkedList in an iteration loop
     */
    LINKEDLIST_INDEX
}
