package bretzj;

import edu.msoe.cs2852.SortedArrayList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

public class AutoComplete {

    protected ArrayList<String> words = new ArrayList<>();
    private boolean isInitialized = false;
    private AutoCompleter method;

    private AutoComplete(AutoCompleter method) {
        this.method = method;
    }

    public void initialize(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        words.clear();

        while (scanner.hasNextLine()) {
            words.add(scanner.nextLine());
        }

        words = new ArrayList<>(new HashSet<>(words));

        isInitialized = true;
    }

    public boolean hasPrefix(String prefix) {
        return method.allThatBeginsWith(prefix, words).size() > 0;
    }

    public boolean contains(String target) {
        return method.allThatBeginsWith(target, words).contains(target);
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public static AutoComplete ArrayIndexFactory() {
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

    public static AutoComplete ArrayIteratorFactory() {
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

    public static AutoComplete LinkedIndexFactory() {
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

    public static AutoComplete LinkedIteratorFactory() {
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

    public static AutoComplete SortedArrayFactory() {
        return new AutoComplete((prefix, words) -> {
            ArrayList<String> matches = new ArrayList<>();
            SortedArrayList<String> sorted = new SortedArrayList<>();
            sorted.addAll(words);

            for (String word : sorted) {
                if (word.startsWith(prefix)) {
                    matches.add(word);
                }
            }
            return matches;
        });
    }

    private int BinarySearch(SortedArrayList<String> list, String key) {
        int low = 0;
        int high = list.size() - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            String midVal = list.get(mid);
            int cmp = midVal.startsWith(key) ? 0 : midVal.compareTo(key);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid; // key found
        }
        return -1;  // key not found
    }
}
