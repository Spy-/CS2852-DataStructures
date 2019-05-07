package bretzj.dataStructure;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PrefixTree {
    private static class Node {
        char c;
        Node[] children;
        boolean isLeaf;
        Node parent;

        private Node() {
            this.children = new Node[38];
        }

        private Node(char c) {
            this.children = new Node[26];
            this.c = c;
        }
    }

    private Node root;
    private Node prefixRoot;
    private String currentPrefix;
    private List<String> foundWords = new ArrayList<>();
    private int size = 0;

    public PrefixTree() {
        root = new Node();
    }

    // adds a word to tree
    public void insert(String word) {
        Node parent = root;
        char[] chars = word.toCharArray();
        try {
            for (char c : chars) {
                int index = Character.isLetter(c) ? c - 'a' : Character.isDigit(c) ? c - 'a' + 52 : (c == '.' || c == '-') ? c - 'a' + 53 : -1;

                if (index < 0) {
                    break;
                }
                if (parent.children[index] == null) {
                    Node temp = new Node(c);
                    parent.children[index] = temp;
                    temp.parent = parent;
                    parent = temp;
                    ++size;
                } else {
                    parent = parent.children[index];
                }
            }
            parent.isLeaf = true;
        } catch (IndexOutOfBoundsException e) {
            System.out.println(word);
        }
    }

    // returns true if any word starts with the prefix
    private boolean startsWith(String prefix) {
        return searchNode(prefix) != null;
    }

    private Node searchNode(String str) {
        Node parent = root;
        char[] chars = str.toCharArray();

        try {
            for (char c : chars) {
                int index = Character.isLetter(c) ? c - 'a' : Character.isDigit(c) ? c - 'a' + 52 : (c == '.' || c == '-') ? c - 'a' + 53 : -1;
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

        LinkedList<Node> others = new LinkedList<>();

        for (Node n : node.children) {
            if (n != null) {
                others.addLast(n);
            }
        }

        // recurse for every other letter from prefix root
        for (Node n : others) {
            wordTraversal(n);
        }
    }

    public List<String> getWordsFromPrefix(String prefix) {
        foundWords.clear();
        if (startsWith(prefix)) {
            wordTraversal(searchNode(prefix));
        }
        return foundWords;
    }

    public int size() {
        return size;
    }
}
