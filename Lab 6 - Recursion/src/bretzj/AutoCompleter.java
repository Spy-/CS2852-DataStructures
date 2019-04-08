package bretzj;

import java.util.ArrayList;
import java.util.List;

public interface AutoCompleter {
    ArrayList<String> allThatBeginsWith(String prefix, List<String> list);
}
