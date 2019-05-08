package bretzj.completers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class SortedArrayCompleter extends BaseAutoCompleter {

    private List<String> words = new ArrayList<>();

    @Override
    public boolean loadTextFile(String filename) throws FileNotFoundException {
        try (Scanner scan = new Scanner(new File(filename))) {
            words.clear();
            while (scan.hasNextLine()) {
                words.add(scan.nextLine());
            }
            Collections.sort(words);
            return true;
        }
    }

    @Override
    public boolean loadCSVFile(String filename) throws FileNotFoundException {
        try (Scanner scan = new Scanner(new File(filename))) {
            words.clear();
            while (scan.hasNextLine()) {
                String[] part = scan.nextLine().split(",");
                words.add(part[1]);
            }
            Collections.sort(words);
            return true;
        }
    }

    @Override
    public int size() {
        return words.size();
    }

    @Override
    public List<String> allThatBeginsWith(String prefix) {
        start = System.nanoTime();
        int first = firstIndex(prefix);
        int last = lastIndex(prefix);
        List<String> foundWords = new ArrayList<>();

        if (first != -1 && last != -1) {
            foundWords = words.subList(first, last + 1);
        }
        end = System.nanoTime();

        return foundWords;
    }

    private int firstIndex(String prefix) {
        if (words.size() <= 0) {
            return -1;
        }

        int min = 0;
        int max = words.size() - 1;
        int middle;
        while (min <= max) {
            middle = (max - min) / 2 + min;
            if (compare(prefix, words.get(middle)) > 0) {
                min = middle + 1;
            } else if (compare(prefix, words.get(middle)) < 0) {
                max = middle - 1;
            } else {
                if (middle == 0) {
                    return middle;
                } else if (compare(prefix, words.get(middle - 1)) > 0) {
                    return middle;
                } else {
                    max = middle - 1;
                }
            }
        }
        return -1;
    }

    private int lastIndex(String prefix) {
        if (words.size() <= 0) {
            return -1;
        }
        int length = words.size() - 1;
        int min = 0;
        int max = length;
        int middle;
        while (min <= max) {
            middle = (max - min) / 2 + min;
            if (compare(prefix, words.get(middle)) > 0) {
                min = middle + 1;
            } else if (compare(prefix, words.get(middle)) < 0) {
                max = middle - 1;
            } else {
                if (middle == length) {
                    return middle;
                } else if (compare(prefix, words.get(middle + 1)) < 0) {
                    return middle;
                } else {
                    min = middle + 1;
                }
            }
        }
        return -1;
    }

    private int compare(String prefix, String word) {
        int len = word.length() < prefix.length() ? word.length() : prefix.length();

        return prefix.compareTo(word.substring(0, len));
    }
}
