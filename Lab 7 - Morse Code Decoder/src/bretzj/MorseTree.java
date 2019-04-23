/*
 * Course:  CS2852
 * Spring 2019
 * Lab 7 - Morse Code Decoder
 * Name: John Bretz
 * Created: 4/22/2019
 */
package bretzj;

/**
 * A modified binary tree to hold a morse code dictionary
 *
 * @param <E> some type
 */
public class MorseTree<E> {
    private static final class Node<T> {
        private T value;
        private Node<T> left;
        private Node<T> right;

        private Node(T value, Node<T> right, Node<T> left) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        private Node(T value) {
            this(value, null, null);
        }
    }

    private Node<E> root;

    /**
     * Constructor
     */
    public MorseTree() {
        this.root = new Node<>(null);
    }

    /**
     * Creates a new Node for the given symbol
     *
     * @param symbol the symbol
     * @param code   the morse code pattern
     */
    public void add(E symbol, String code) {
        Node<E> subroot = root;
        char character;

        for (int i = 0; i < code.length(); i++) {
            character = code.charAt(i);

            if (character == '-') {
                if (subroot.right == null) {
                    subroot.right = new Node<>(null);
                }
                subroot = subroot.right;
            } else if (character == '.') {
                if (subroot.left == null) {
                    subroot.left = new Node<>(null);
                }
                subroot = subroot.left;
            } else {
                throw new IllegalArgumentException("The morse code has an invalid character");
            }
        }
        subroot.value = symbol;
    }

    /**
     * Traverses the tree based off the pattern and returns the nodes value
     * @param pattern the morse code pattern
     * @return the node's value
     */
    public E decode(String pattern) {
        Node<E> subroot = root;
        boolean found = false;

        try {
            for (int i = 0; i < pattern.length(); i++) {
                char character = pattern.charAt(i);

                switch (character) {
                    case '-':
                        subroot = subroot.right;
                        break;
                    case '.':
                        subroot = subroot.left;
                        break;
                    default:
                        throw new IllegalArgumentException("Warning: skipping: " + character);
                }
            }
            found = true;
        } catch (NullPointerException ignored) {
        }
        return found ? subroot.value : null;
    }
}
