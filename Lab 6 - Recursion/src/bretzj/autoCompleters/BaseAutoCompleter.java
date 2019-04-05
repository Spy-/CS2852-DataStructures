package bretzj.autoCompleters;

import bretzj.AutoCompleter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public abstract class BaseAutoCompleter implements AutoCompleter {

    protected ArrayList<String> words = new ArrayList<>();
    private boolean isInitialized = false;

    @Override
    public void initialize(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));

        while (scanner.hasNextLine()) {
            words.add(scanner.nextLine());
        }

        words = new ArrayList<>(new HashSet<>(words));

        isInitialized = true;
    }

    public boolean hasPrefix(String prefix) {
        return allThatBeginsWith(prefix).size() > 0;
    }

    @Override
    public boolean contains(String target) {
        return allThatBeginsWith(target).contains(target);
    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }
}
