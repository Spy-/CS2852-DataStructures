package bretzj;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface AutoCompleter {
    void initialize(String filename) throws FileNotFoundException;
    ArrayList<String> allThatBeginsWith(String prefix);
    boolean hasPrefix(String toString);
    boolean contains(String target);
    boolean isInitialized();
}
