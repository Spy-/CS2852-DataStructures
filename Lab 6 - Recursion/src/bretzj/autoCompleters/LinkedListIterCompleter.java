package bretzj.autoCompleters;

import java.util.ArrayList;
import java.util.LinkedList;

public class LinkedListIterCompleter extends BaseAutoCompleter {

    @Override
    public ArrayList<String> allThatBeginsWith(String prefix) {
        ArrayList<String> matches = new ArrayList<>();
        LinkedList<String> lwords = new LinkedList<>(words);

        for (String s : lwords) {
            if (s.startsWith(prefix)) {
                matches.add(s);
            }
        }

        return matches;
    }
}