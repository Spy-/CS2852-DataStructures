package bretzj.dataStructure;

import java.util.*;

public class PrefixTree {
    private static class Node {
        char c;
        Node parent;
        HashMap<Character, Node> children = new HashMap<>();
        boolean isLeaf;

        private Node() {
        }

        private Node(char c) {
            this.c = c;
        }
    }

    private Node root;
    private Node prefixRoot;
    private String currentPrefix;
    private List<String> foundWords = new ArrayList<>();

    public PrefixTree() {
        root = new Node();
    }

    // adds a word to tree
    public void insert(String word) {
        HashMap<Character, Node> children = root.children;
        Node parent = root;

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            Node t;
            if (children.containsKey(c)) {
                t = children.get(c);
            } else {
                t = new Node(c);
                t.parent = parent;
                children.put(c, t);
            }
            children = t.children;
            parent = t;

            if (i == word.length() - 1) {
                t.isLeaf = true;
            }
        }
    }

    // returns true if any word starts with the prefix
    private boolean startsWith(String prefix) {
        return searchNode(prefix) != null;
    }

    private Node searchNode(String str) {
        Map<Character, Node> children = root.children;
        Node t = null;
        char[] chars = str.toCharArray();

        for (char c : chars) {
            if (children.containsKey(c)) {
                t = children.get(c);
                children = t.children;
            } else {
                return null;
            }
        }

        prefixRoot = t;
        currentPrefix = str;
        foundWords.clear();
        return t;
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

        Iterator itr = node.children.keySet().iterator();
        LinkedList<Character> others = new LinkedList<>();

        while (itr.hasNext()) {
            others.addLast((Character) itr.next());
        }

        // recurse for every other letter from prefix root
        for (Character character : others) {
            wordTraversal(node.children.get(character));
        }
    }

    public List<String> getWordsFromPrefix(String prefix) {
        foundWords.clear();
        if (startsWith(prefix)) {
            wordTraversal(searchNode(prefix));
        }
        return foundWords;
    }
}
