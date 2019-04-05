package bretzj;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameBoard {

    private AutoCompleter autoCompleter;
    private char[][] grid;
    private ArrayList<String> words = new ArrayList<>();
    private static int count = 0;

    public GameBoard(AutoCompleter strategy) {
        if (strategy == null || !strategy.isInitialized()) {
            throw new IllegalArgumentException("AutoCompleter can't be null");
        }
        autoCompleter = strategy;
    }

    public void load(Path path) {
        try {
            Scanner scanner = new Scanner(new File(String.valueOf(path)));
            ArrayList<String> temp = new ArrayList<>();
            while (scanner.hasNextLine()) {
                temp.add(scanner.nextLine());
            }

            grid = new char[temp.size()][temp.get(0).length()];

            for (int i = 0; i < temp.size(); i++) {
                String word = temp.get(i).toLowerCase();
                for (int j = 0; j < word.length(); j++) {
                    grid[i][j] = word.charAt(j);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();//TODO
        }
    }

    private void recursiveSearch(int row, int col, boolean[][] visitedFlags, String word) {

    }

    public List<String> findWords() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                recursiveSearch(i, j, getInitialFlags(i, j), (String.valueOf(grid[i][j])));
            }
        }
        System.out.println(count);

        return words;
    }

    private boolean[][] getInitialFlags(int row, int col) {
        boolean[][] flags = new boolean[grid.length][grid[0].length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                flags[i][j] = false;
            }
        }

        return flags;
    }
}
