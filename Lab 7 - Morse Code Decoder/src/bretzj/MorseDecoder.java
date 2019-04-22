/*
 * Course:  CS2852
 * Spring 2019
 * Lab 7 - Morse Code Decoder
 * Name: John Bretz
 * Created: 4/5/2019
 */
package bretzj;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Main Class
 */
public class MorseDecoder {

    private static MorseTree<String> tree = new MorseTree<>();
    private static final boolean DEV_MODE = false;

    /**
     * Main entry point
     *
     * @param args some cmd line arguments
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter an input filename:");
        String inputFile = DEV_MODE ? "input.txt" : input.nextLine();

        System.out.println("Enter an output filename:");
        String outputFile = DEV_MODE ? "output.txt" : input.nextLine();

        loadDecoder(Paths.get("MorseDictionary.txt"));
        decode(new File(inputFile), new File(outputFile));
    }

    /**
     * Loads the morse code dictionary
     *
     * @param path the path to the file
     */
    private static void loadDecoder(Path path) {
        try (Scanner scan = new Scanner(path.toFile())) {
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
     * Decodes the given input and writes the output to the file
     *
     * @param input  the input file
     * @param output the output file
     */
    private static void decode(File input, File output) {
        try (Scanner inputScan = new Scanner(input)) {
            FileWriter writer = new FileWriter(output);

            while (inputScan.hasNextLine()) {
                String[] patterns = inputScan.nextLine().split(" ");
                for (String pattern : patterns) {
                    try {
                        String decoded = tree.decode(pattern);
                        if (!decoded.equals("\\n")) {
                            writer.write(decoded);
                        } else {
                            writer.write("\n");
                        }
                    } catch (NullPointerException ignored) {
                    } catch (IllegalArgumentException iae) {
                        System.out.println(iae.getMessage());
                    }
                }
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("Some IOException occurred");
            System.exit(0);
        }
    }
}
