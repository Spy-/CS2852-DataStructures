package bretzj.completers;

import java.io.FileNotFoundException;
import java.util.List;

public interface AutoCompleter {
    void initialize(String filename) throws FileNotFoundException;

    List<String> allThatBeginsWith(String prefix);

    long getLastOperationTime();
}
