/*
 * Course:  CS2852
 * Spring 2019
 * Lab 7 - Morse Code Decoder
 * Name: John Bretz
 * Created: 4/23/2019
 */
package tests;

import bretzj.MorseTree;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tester Class
 */
public class MorseTester {

    private static MorseTree<String> tree = new MorseTree<>();

    /**
     * loads the dictionary
     */
    @BeforeAll
    public static void setup() {
        try (Scanner scan = new Scanner(new File("MorseDictionary"))) {
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                if (line.charAt(0) != '\\') {
                    tree.add(line.substring(0, 1), line.substring(1));
                } else {
                    tree.add(line.substring(0, 2), line.substring(2));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("The file does not exist");
            System.exit(0);
        }
    }

    /**
     * Tests a simple encoding
     */
    @Test
    public void testDecode1() {
        String encode = ".... .. .-... - .... . .-. .";
        String decode = "HI THERE";
        StringBuilder answer = new StringBuilder();

        for (String s : encode.split(" ")) {
            answer.append(tree.decode(s));
        }

        assertEquals(answer.toString(), decode);
    }

    /**
     * Tests a long encoding
     */
    @Test
    public void testDecodeFile() {

        StringBuilder decode = new StringBuilder();
        String answer = "    A SPACE SHOULD BE PLACED BETWEEN EACH ENCODED CHARACTER.\n" +
                "     \n" +
                "    A  SHOULD BE PLACED BETWEEN EACH WORD.\n" +
                "     \n" +
                "    LINE BREAKS IN THE INPUT FILE SHOULD BE REPLICATED IN THE ENCODED FILE.";

        try (Scanner inputScan = new Scanner(new File("input.txt"))) {
            while (inputScan.hasNextLine()) {
                String[] patterns = inputScan.nextLine().split(" ");
                for (String pattern : patterns) {
                    try {
                        String decoded = tree.decode(pattern);
                        if (!decoded.equals("\\n")) {
                            decode.append(decoded);
                        } else {
                            decode.append("\n");
                        }
                    } catch (NullPointerException ignored) {
                    } catch (IllegalArgumentException iae) {
                        System.out.println(iae.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Some IOException occurred");
            System.exit(0);
        }

        assertEquals(answer, decode.toString());
    }
}
