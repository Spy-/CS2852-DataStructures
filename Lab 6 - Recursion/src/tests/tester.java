package tests;

import bretzj.AutoCompleter;
import bretzj.GameBoard;
import bretzj.autoCompleters.ArrayListIndexCompleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class tester {
    List<String> goodWords, badWords, dictionary;
    AutoCompleter strategy = new ArrayListIndexCompleter();
    GameBoard gameBoard;

    /**
     * @throws Exception Description: create the dictionaries.
     */
    @Before
    public void setUp() throws Exception {

        strategy.initialize("testDict.txt");
        gameBoard = new GameBoard(strategy);

        goodWords = Arrays.asList("readied", "reaved", "randie", "navaid",
                "larned", "errand", "envied", "endive", "earned", "darnel",
                "darned", "dandle", "daidle", "adread", "vexed", "veena",
                "varna", "varan", "vaned", "reran", "reave", "raved",
                "ranee", "narre", "naled", "lande", "evade", "erned",
                "eland", "eaved", "eaned", "drear", "dread", "drave",
                "dived", "divan", "denar", "darre", "darer", "arear",
                "aread", "alane", "aland", "adrad", "vied", "vide", "vare",
                "vara", "vane", "vade", "rear", "rean", "read", "rave",
                "rare", "rand", "rana", "rale", "rade", "need", "nave",
                "nare", "nard", "nala", "nada", "lend", "leed", "larn",
                "lare", "lane", "land", "lana", "idle", "idee", "exed",
                "erne", "elan", "eide", "eevn", "eave", "earn", "eard",
                "drad", "dive", "diva", "died", "deva", "deid", "deev",
                "deen", "darn", "dare", "dada", "avid", "arna", "area",
                "arar", "anal", "alee", "alar", "alan", "aide", "vie",
                "vid", "via", "vex", "vee", "var", "van", "vae", "ran",
                "rad", "nee", "ned", "nae", "lex", "lee", "led", "lar",
                "ide", "err", "ern", "era", "end", "eld", "een", "eel",
                "ear", "ean", "div", "die", "did", "dex", "dev", "den",
                "del", "dei", "dee", "dan", "dae", "dad", "ave", "ava",
                "are", "ard", "ane", "and", "ana", "ale", "ala", "aid",
                "ade", "aal");

        badWords = Arrays.
                asList("amazon", "forest", "dangerous", "lovely", "resistible");

        dictionary = new ArrayList<>();
        dictionary.addAll(goodWords);
        dictionary.addAll(badWords);
    }

    /**
     * @throws Exception Description: Destroy all resources after use.
     */
    @After
    public void tearDown() throws Exception {
        goodWords = badWords = null;
        gameBoard = null;
    }

    /**
     * Test of solver method, of class BoggleSolver.
     */
    @Test
    public void testSolver() {
        System.out.println("solver");

        gameBoard.load(Paths.get("testGrid.txt"));

        Set<String> result = new HashSet<>(gameBoard.findWords());

        assertEquals(goodWords.size(), result.size());
        for (String g : goodWords) {
            if (!result.contains(g)) {
                fail("Boggle solver fails to find " + g);
            }
        }
        for (String b : badWords) {
            if (result.contains(b)) {
                fail("Boggle solver should not have found " + b);
            }
        }
    }
}
