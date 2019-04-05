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
        if (autoCompleter.contains(word) && !(word.length() < 3)) words.add(word);

        if (!autoCompleter.hasPrefix(word)) return;

        if (word.length() > 15) return;

        boolean[][] tmp = copyArray(visitedFlags);
        tmp[row][col] = true;

        //upper left
        if (0 <= row - 1 && 0 <= col - 1 && !tmp[row - 1][col - 1]) {
            recursiveSearch(row - 1, col - 1, tmp, word + grid[row - 1][col - 1]);
        }

        //up
        if (0 <= col - 1 && !tmp[row][col - 1]) {
            recursiveSearch(row, col - 1, tmp, word + grid[row][col - 1]);
        }

        //upper right
        if (row + 1 < grid.length && 0 <= col - 1 && !tmp[row + 1][col - 1]) {
            recursiveSearch(row + 1, col - 1, tmp, word + grid[row + 1][col - 1]);
        }

        //right
        if (row + 1 < grid.length && !tmp[row + 1][col]) {
            recursiveSearch(row + 1, col, tmp, word + grid[row + 1][col]);
        }

        //lower right
        if (row + 1 < grid.length && col + 1 < grid[0].length && !tmp[row + 1][col + 1]) {
            recursiveSearch(row + 1, col + 1, tmp, word + grid[row + 1][col + 1]);
        }

        //down
        if (col + 1 < grid[0].length && !tmp[row][col + 1]) {
            recursiveSearch(row, col + 1, tmp, word + grid[row][col + 1]);
        }

        //lower left
        if (0 <= row - 1 && col + 1 < grid[0].length && !tmp[row - 1][col + 1]) {
            recursiveSearch(row - 1, col + 1, tmp, word + grid[row - 1][col + 1]);
        }

        //left
        if (0 <= row - 1 && !tmp[row - 1][col]) {
            recursiveSearch(row - 1, col, tmp, word + grid[row - 1][col]);
        }
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

    private boolean[][] copyArray(boolean[][] orig) {
        boolean[][] remake = new boolean[orig.length][orig[0].length];
        for (int x = 0; x < orig.length; x++) {
            System.arraycopy(orig[x], 0, remake[x], 0, orig[0].length);
        }
        return remake;
    }
}
