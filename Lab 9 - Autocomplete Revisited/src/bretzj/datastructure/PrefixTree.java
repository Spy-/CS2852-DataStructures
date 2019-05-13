/*
 * Course: CS2852
 * Spring 2019
 * Lab 9 - Autocomplete Revisited
 * Name: John Bretz
 * Created: 5/7/19
 */
package bretzj.datastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.toIntExact;
import static java.util.Arrays.stream;

/**
 * Prefix Tree class
 */
public class PrefixTree {
    /**
     * Inner class for Nodes
     */
    private static class Node {
        private static final int CHILDSIZE = 38;
        char c;
        Node[] children;
        boolean isLeaf;
        Node parent;

        /**
         * Default constructor
         */
        private Node() {
            this.children = new Node[CHILDSIZE];
        }

        /**
         * Constructor
         *
         * @param c the character to store
         */
        private Node(char c) {
            this.children = new Node[CHILDSIZE];
            this.c = c;
        }
    }

    private Node root;
    private Node prefixRoot;
    private String currentPrefix;
    private List<String> foundWords = new ArrayList<>();
    private int size = 0;

    private static final int ASCII_CHARACTER = 52;
    private static final int ASCII_SYMBOL = 53;

    /**
     * Constructor
     */
    public PrefixTree() {
        root = new Node();
    }

    /**
     * Adds a word to the tree
     *
     * @param word the word
     */
    public void insert(String word) {
        Node parent = root;
        char[] chars = word.toCharArray();
        try {
            for (char c : chars) {
                int index = getIndex(c);

                if (index < 0) {
                    break;
                }
                if (parent.children[index] == null) {
                    Node temp = new Node(c);
                    parent.children[index] = temp;
                    temp.parent = parent;
                    parent = temp;
                } else {
                    parent = parent.children[index];
                }
            }
            ++size;
            parent.isLeaf = true;
        } catch (IndexOutOfBoundsException e) {
            System.out.println(word);
        }
    }

    /**
     * Does the any word start with the prefix
     *
     * @param prefix the prefix to search for
     * @return true if any word starts with the prefix
     */
    private boolean startsWith(String prefix) {
        return searchNode(prefix) != null;
    }

    /**
     * Finds the last node for the given string
     *
     * @param str the string to search for
     * @return the last Node in the string
     */
    private Node searchNode(String str) {
        Node parent = root;
        char[] chars = str.toCharArray();

        try {
            for (char c : chars) {
                int index = getIndex(c);
                if (parent.children[index] != null) {
                    parent = parent.children[index];
                } else {
                    return null;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }

        if (parent == root) {
            return null;
        }

        prefixRoot = parent;
        currentPrefix = str;
        return parent;
    }

    /**
     * Recursively traverses the tree to find words based off a prefix
     *
     * @param node the node to start the search from
     */
    private void wordTraversal(Node node) {
        // stop if dead end
        if (node == null) {
            return;
        }

        // add word if at leaf
        if (node.isLeaf) {
            Node current = node;
            StringBuilder suffix = new StringBuilder();

            while (current != prefixRoot) {
                suffix.insert(0, current.c);
                current = current.parent;
            }

            foundWords.add(currentPrefix + suffix.toString());
        }

        Node[] other = new Node[toIntExact(stream(node.children).filter(Objects::nonNull).count())];
        int othersIndex = 0;

        for (int i = 0, childrenLength = node.children.length; i < childrenLength; i++) {
            Node n = node.children[i];
            if (n != null) {
                other[othersIndex] = n;
                ++othersIndex;
            }
        }

        // recurse for every other letter from prefix root
        for (Node n : other) {
            wordTraversal(n);
        }
    }

    /**
     * Finds all the words in the tree that start with the given prefix
     *
     * @param prefix the prefix to search for
     * @return a list of valid words
     */
    public List<String> getWordsFromPrefix(String prefix) {
        foundWords.clear();
        if (startsWith(prefix)) {
            wordTraversal(searchNode(prefix));
        }
        return foundWords;
    }

    /**
     * The number of words in the tree
     *
     * @return the number of words in the tree
     */
    public int size() {
        return size;
    }

    /**
     * Finds the proper index where a child should be
     *
     * @param c the character
     * @return the proper index for the child to go
     */
    private int getIndex(char c) {
        if (Character.isLetter(c)) {
            return c - 'a';
        } else if (Character.isDigit(c)) {
            return c - 'a' + ASCII_CHARACTER;
        } else if (c == '.' || c == '-') {
            return c - 'a' + ASCII_SYMBOL;
        }
        return -1;
    }
}
