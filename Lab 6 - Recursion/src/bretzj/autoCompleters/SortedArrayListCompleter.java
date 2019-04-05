package bretzj.autoCompleters;

import edu.msoe.cs2852.SortedArrayList;

import java.util.ArrayList;

public class SortedArrayListCompleter extends BaseAutoCompleter {

    @Override
    public ArrayList<String> allThatBeginsWith(String prefix) {
        ArrayList<String> matches = new ArrayList<>();
        SortedArrayList<String> swords = new SortedArrayList<>();
        swords.addAll(words);

        for (String s : swords) {
            if (s.startsWith(prefix)) {
                matches.add(s);
            }
        }

        return matches;
    }
}
