package bretzj;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tester {

    private LookupTable table;
    private static List<String[]> dictionary;

    @BeforeAll
    public static void setupDictionary() {
        dictionary = new ArrayList<>();

        try (Scanner scan = new Scanner(new File("MorseDictionary.txt"))) {
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String key = line.substring(0, 1);
                String value = line.substring(1);

                if (line.charAt(0) == '\\') {
                    key = line.substring(0, 2);
                    value = line.substring(2);
                }

                dictionary.add(new String[]{key, value});
            }
        } catch (FileNotFoundException ignored) {
        }
    }

    @BeforeEach
    public void setup() {
        table = new LookupTable();
        table.putAll(dictionary);
    }

    @Test
    public void testAddDictionary() {
        assertEquals(42, table.size());

        System.out.println(table);
    }

    @Test
    public void basicEncode() {
        assertEquals(".-", table.get("A"));
    }

    @Test
    public void advancedEncode() {
        String answer = ".... .. .-... - .... . .-. . ";
        String input = "HI THERE";
        StringBuilder output = new StringBuilder();

        char[] in = input.toUpperCase().toCharArray();

        for (char c : in) {
            output.append(table.get(String.valueOf(c))).append(" ");
        }

        assertEquals(answer, output.toString());
    }

    @Test
    public void fileEncode() {
        File input = new File("input.txt");
        File output = new File("output.txt");
        File answer = new File("answer.txt");
        MorseEncoder.loadDictionary(new File("MorseDictionary.txt"));
        MorseEncoder.encodeFile(input, output);

        StringBuilder expected = new StringBuilder();
        StringBuilder actual = new StringBuilder();

        try (Scanner scan = new Scanner(output); Scanner returned = new Scanner(answer)) {
            while (scan.hasNextLine()) {
                expected.append(scan.nextLine());
            }

            while (returned.hasNextLine()) {
                actual.append(returned.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(expected.toString(), actual.toString());
    }
}
