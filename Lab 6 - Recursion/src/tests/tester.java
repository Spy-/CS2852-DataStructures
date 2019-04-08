package tests;

import bretzj.AutoComplete;
import bretzj.GameBoard;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;


public class tester {
    private AutoComplete strategy;
    private GameBoard gameBoard;

    @Test
    public void testBoggleOnline() throws FileNotFoundException {
        strategy = AutoComplete.SortedArrayFactory();
        strategy.initialize("BoggleOnlineTest answers.txt");
        gameBoard = new GameBoard(strategy);
        gameBoard.load(Paths.get("BoggleOnlineTest grid.txt"));

        Set<String> result = new HashSet<>(gameBoard.findWords());
        assertEquals(202, result.size());
    }

    @Test
    public void test1() throws FileNotFoundException {
        strategy = AutoComplete.ArrayIndexFactory();
        strategy.initialize("words.txt");
        gameBoard = new GameBoard(strategy);
        gameBoard.load(Paths.get("grid3x3.txt"));

        Set<String> result = new HashSet<>(gameBoard.findWords());
        assertEquals(67, result.size());
    }

    @Test
    public void test2() throws FileNotFoundException {
        strategy = AutoComplete.LinkedIteratorFactory();
        strategy.initialize("words.txt");
        gameBoard = new GameBoard(strategy);
        gameBoard.load(Paths.get("grid4x4.txt"));

        Set<String> result = new HashSet<>(gameBoard.findWords());
        assertEquals(204, result.size());
    }

    @Test
    public void test3() throws FileNotFoundException {
        strategy = AutoComplete.SortedArrayFactory();
        strategy.initialize("words.txt");
        gameBoard = new GameBoard(strategy);
        gameBoard.load(Paths.get("grid3x3.txt"));

        Set<String> result = new HashSet<>(gameBoard.findWords());
        assertEquals(67, result.size());
    }
}
