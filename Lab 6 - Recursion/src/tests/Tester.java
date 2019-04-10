/*
 * Course: CS2852
 * Spring 2019
 * Lab 6 - Recursion
 * Name: John Bretz
 * Created: 4/5/2019
 */
package tests;

import bretzj.AutoComplete;
import bretzj.GameBoard;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Class that contains a bunch of tests
 */
public class Tester {
    private AutoComplete strategy;
    private GameBoard gameBoard;

    /**
     * Tests the algorithm based off a board from `fuzzylogicinc.net/boggle/`
     *
     * @throws FileNotFoundException if the file(s) don't exist
     */
    @Test
    public void testBoggleOnline() throws FileNotFoundException {
        strategy = AutoComplete.sortedArrayFactory();
        strategy.initialize("BoggleOnlineTest answers.txt");
        gameBoard = new GameBoard(strategy);
        gameBoard.load(Paths.get("BoggleOnlineTest grid.txt"));

        Set<String> result = new HashSet<>(gameBoard.findWords());
        assertEquals(202, result.size());
    }

    @Test
    public void testHighScoringBoard() throws FileNotFoundException {
        strategy = AutoComplete.linkedIteratorFactory();
        strategy.initialize("BoggleOnlineHigh answers.txt");
        gameBoard = new GameBoard(strategy);
        gameBoard.load(Paths.get("BoggleOnlineHigh grid.txt"));

        Set<String> result = new HashSet<>(gameBoard.findWords());
        assertEquals(1318, result.size());
    }

    /**
     * Tests a 3x3 grid with an Indexed ArrayList
     *
     * @throws FileNotFoundException if the file(s) don't exist
     */
    @Test
    public void test1() throws FileNotFoundException {
        strategy = AutoComplete.arrayIndexFactory();
        strategy.initialize("words.txt");
        gameBoard = new GameBoard(strategy);
        gameBoard.load(Paths.get("grid3x3.txt"));

        Set<String> result = new HashSet<>(gameBoard.findWords());
        assertEquals(67, result.size());
    }

    /**
     * Tests a 4x4 grid with a Iterated LinkedList
     *
     * @throws FileNotFoundException if the file(s) don't exist
     */
    @Test
    public void test2() throws FileNotFoundException {
        strategy = AutoComplete.linkedIteratorFactory();
        strategy.initialize("words.txt");
        gameBoard = new GameBoard(strategy);
        gameBoard.load(Paths.get("grid4x4.txt"));

        Set<String> result = new HashSet<>(gameBoard.findWords());
        assertEquals(204, result.size());
    }

    /**
     * Tests a 3x3 grid with a Sorted ArrayList
     *
     * @throws FileNotFoundException if the file(s) don't exist
     */
    @Test
    public void test3() throws FileNotFoundException {
        strategy = AutoComplete.sortedArrayFactory();
        strategy.initialize("words.txt");
        gameBoard = new GameBoard(strategy);
        gameBoard.load(Paths.get("grid3x3.txt"));

        Set<String> result = new HashSet<>(gameBoard.findWords());
        assertEquals(67, result.size());
    }
}
