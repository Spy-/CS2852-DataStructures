package bretzj.autoCompleters;

import java.util.ArrayList;
import java.util.LinkedList;

public class LinkedListIndexCompleter extends BaseAutoCompleter {

    @Override
    public ArrayList<String> allThatBeginsWith(String prefix) {
        ArrayList<String> matches = new ArrayList<>();
        LinkedList<String> lwords = new LinkedList<>(words);

        for (int i = 0; i < lwords.size(); i++) {
            String word = lwords.get(i);
            if (word.startsWith(prefix)) {
                matches.add(word);
            }
        }

        return matches;
    }
}
