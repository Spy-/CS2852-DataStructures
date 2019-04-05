package bretzj;

import java.io.File;
import java.util.Arrays;

public class Main {

    private static File grid;
    private static File dictionary;
    private static Strategy strategy;

    public static void main(String[] args) {
        if (args != null && args.length == 3) {
            readArgs(args);


            


        } else {
            System.out.println("Must pass a grid file, a dictionary, and a sort method");
        }
    }

    private static void readArgs(String[] args) {
        System.out.println(Arrays.toString(args));

        grid = new File(args[0]);
        dictionary = new File(args[1]);

        if (args[2].toLowerCase().contains("sort")) {
            strategy = Strategy.SORTED_LIST;
        } else if (args[2].toLowerCase().contains("iter")) {
            if (args[2].toLowerCase().contains("array")) {
                strategy = Strategy.ARRAYLIST_ENHANCED;
            } else {
                strategy = Strategy.LINKEDLIST_ENHANCED;
            }
        } else {
            if (args[2].toLowerCase().contains("array")) {
                strategy = Strategy.ARRAYLIST_INDEX;
            } else {
                strategy = Strategy.LINKEDLIST_INDEX;
            }
        }
    }
}
