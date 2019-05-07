package bretzj.completers;

import java.io.FileNotFoundException;

public abstract class BaseAutoCompleter implements AutoCompleter {

    protected long start;
    protected long end;
    protected boolean dictionaryLoaded = false;

    @Override
    public void initialize(String filename) throws FileNotFoundException {
        start = System.nanoTime();
        if (filename.endsWith(".txt")) {
            dictionaryLoaded = loadTextFile(filename);
        } else if (filename.endsWith(".csv")) {
            dictionaryLoaded = loadCSVFile(filename);
        }
        end = System.nanoTime();
        dictionaryLoaded = true;
    }

    public abstract boolean loadTextFile(String filename) throws FileNotFoundException;

    public abstract boolean loadCSVFile(String filename) throws FileNotFoundException;

    @Override
    public long getLastOperationTime() {
        if (dictionaryLoaded) {
            return end - start;
        }
        throw new IllegalStateException("Must call Initialize() prior to calling this method");
    }

    public abstract int size();
}
