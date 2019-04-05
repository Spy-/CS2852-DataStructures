package bretzj.autoCompleters;

import java.util.ArrayList;

public class ArrayListIndexCompleter extends BaseAutoCompleter {

    @Override
    public ArrayList<String> allThatBeginsWith(String prefix) {
        ArrayList<String> matches = new ArrayList<>();

        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);
            if (word.startsWith(prefix)) {
                matches.add(word);
            }
        }

        return matches;
    }
}
