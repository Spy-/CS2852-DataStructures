package bretzj.autoCompleters;

import java.util.ArrayList;

public class ArrayListIterCompleter extends BaseAutoCompleter {

    @Override
    public ArrayList<String> allThatBeginsWith(String prefix) {
        ArrayList<String> matches = new ArrayList<>();

        for (String s : words) {
            if (s.startsWith(prefix)) {
                matches.add(s);
            }
        }

        return matches;
    }
}
