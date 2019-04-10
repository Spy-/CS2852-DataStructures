/*
 * Course: CS2852
 * Spring 2019
 * Lab 6 - Recursion
 * Name: John Bretz
 * Created: 4/5/2019
 */
package bretzj;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class for the boggle game board
 */
public class GameBoard {

    private AutoComplete autoCompleter;
    private char[][] grid;
    private ArrayList<String> words = new ArrayList<>();
    private static final int MAX_LENGTH = 15;
    private static final int MIN_LENGTH = 3;

    /**
     * Constructor for the game board
     *
     * @param strategy the object that does the actual searching
     */
    public GameBoard(AutoComplete strategy) {
        if (strategy == null || !strategy.isInitialized()) {
            throw new IllegalArgumentException("AutoCompleter can't be null");
        }
        autoCompleter = strategy;
    }

    /**
     * Loads the desired grid file
     *
     * @param path path to the file
     */
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
            System.out.println("That File does not exist");
            System.exit(0);
        }
    }

    /**
     * Recursively finds words on the grid
     *
     * @param row          the row
     * @param col          the col
     * @param visitedFlags a 2d array of booleans that say if the method has been there before
     * @param word         the current word to search
     */
    private void recursiveSearch(int row, int col, boolean[][] visitedFlags, String word) {
        if (autoCompleter.contains(word) && !(word.length() < MIN_LENGTH)) {
            words.add(word);
        }

        if (!autoCompleter.hasPrefix(word)) {
            return;
        }

        if (word.length() > MAX_LENGTH) {
            return;
        }

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

    /**
     * Finds words from all starting points
     *
     * @return a list of words found
     */
    public List<String> findWords() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                recursiveSearch(i, j, getInitialFlags(), String.valueOf(grid[i][j]));
            }
        }

        return words;
    }

    /**
     * Creates a 2d array the same size as the grid
     *
     * @return the created array
     */
    private boolean[][] getInitialFlags() {
        boolean[][] flags = new boolean[grid.length][grid[0].length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                flags[i][j] = false;
            }
        }

        return flags;
    }

    /**
     * Creates a copy of a 2d boolean array
     *
     * @param orig the array to copy
     * @return an identical array
     */
    private boolean[][] copyArray(boolean[][] orig) {
        boolean[][] remake = new boolean[orig.length][orig[0].length];
        for (int x = 0; x < orig.length; x++) {
            System.arraycopy(orig[x], 0, remake[x], 0, orig[0].length);
        }
        return remake;
    }
}
