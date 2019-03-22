package bretzj;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class AutoCompleter {

    private Stack<ArrayList<String>> stack = new Stack<>();
    private long start, end;
    private boolean dictionaryLoaded = false;

    public AutoCompleter(ArrayList<String> list) {
        stack.push(list);
    }

    public void initialize(String fileName) throws FileNotFoundException {
        File file = new File(fileName);

        try (Scanner fileScan = new Scanner(file)) {
            if (fileName.endsWith(".txt")) {
                loadTextFile(fileScan);
            } else if (fileName.endsWith(".csv")) {
                loadCSVFile(fileScan);
            }
        }
    }

    private void loadCSVFile(Scanner scan) {
        stack.clear();
        ArrayList<String> words = new ArrayList<>();
        String[] part;

        while (scan.hasNextLine()) {
            part = scan.nextLine().split(",");
            words.add(part[1] + ": " + part[0]);
        }
        dictionaryLoaded = true;
        stack.push(words);

        System.out.println("Loaded " + words.size() + " elements.");
    }

    private void loadTextFile(Scanner scan) {
        stack.clear();
        ArrayList<String> words = new ArrayList<>();

        while (scan.hasNextLine()) {
            words.add(scan.nextLine());
        }
        dictionaryLoaded = true;
        stack.push(words);

        System.out.println("Loaded " + words.size() + " elements.");
    }

    public ArrayList<String> allThatBeginsWith(String text, Strategy strategy, boolean isBackspace) {
        ArrayList<String> words;

        if (isBackspace) {
            if (stack.size() > 1) {
                stack.pop();
            }
            start = System.nanoTime();
            words = text.length() > 0 ? stack.peek() : new ArrayList<>();
            end = System.nanoTime();
        } else {
            words = search(text, strategy, stack.peek());
            stack.push(words);
        }
        System.out.println(stack.size());
        return words;
    }

    private ArrayList<String> search(String text, Strategy strategy, ArrayList<String> container) {
        ArrayList<String> words = new ArrayList<>();
        ArrayList<String> array = new ArrayList<>(container);
        LinkedList<String> linked = new LinkedList<>(container);

        start = System.nanoTime();
        if (!text.equals("")) {
            switch (strategy) {
                case ARRAYLIST_INDEX:
                    for (int i = 0; i < array.size(); i++) {
                        if (array.get(i).startsWith(text)) {
                            words.add(array.get(i));
                        }
                    }
                    break;
                case LINKEDLIST_INDEX:
                    for (int i = 0; i < linked.size(); i++) {
                        if (linked.get(i).startsWith(text)) {
                            words.add(linked.get(i));
                        }
                    }
                    break;
                case ARRAYLIST_ENHANCED:
                    for (String s : array) {
                        if (s.startsWith(text)) {
                            words.add(s);
                        }
                    }
                    break;
                case LINKEDLIST_ENHANCED:
                    for (String s : linked) {
                        if (s.startsWith(text)) {
                            words.add(s);
                        }
                    }
                    break;
            }
        }
        end = System.nanoTime();

        return words;
    }

    public long getLastOperationTime() {
        if (dictionaryLoaded) {
            return end - start;
        } else {
            throw new IllegalStateException("Must call Initialize() prior to calling this method");
        }
    }

    public void flush() {
        while (stack.size() > 1) {
            stack.pop();
        }
        System.out.println("The stack was flushed.");
    }
}
