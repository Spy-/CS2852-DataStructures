package bretzj;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MorseEncoder {

    private static LookupTable table = new LookupTable();
    private static final String dictionary = "MorseDictionary.txt";
    private static final boolean DEV_MODE = true;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter an input filename:");
        String inputFile = DEV_MODE ? "input.txt" : input.nextLine();

        System.out.println("Enter an output filename:");
        String outputFile = DEV_MODE ? "output.txt" : input.nextLine();

        loadDictionary(new File(dictionary));
        encodeFile(new File(inputFile), new File(outputFile));
    }

    public static void loadDictionary(File file) {
        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                String line = scan.nextLine();

                if (line.charAt(0) != '\\') {
                    table.put(line.substring(0, 1), line.substring(1));
                } else {
                    table.put(line.substring(0, 2), line.substring(2));
                }
            }
        } catch (FileNotFoundException fnfe) {
            System.err.println("The file does not exist.");
        }
    }

    public static void encodeFile(File input, File output) {
        try (Scanner scan = new Scanner(input)) {
            FileWriter writer = new FileWriter(output);

            while (scan.hasNextLine()) {
                char[] symbols = scan.nextLine().toUpperCase().toCharArray();

//                System.out.println(Arrays.toString(symbols));

                for (char c : symbols) {
                    String pattern = String.valueOf(table.get(String.valueOf(c)));
                    if (!pattern.equals("null")) {
                        writer.write(pattern);
                        writer.write(" ");
                    } else {
                        System.out.println("Warning: skipping " + c);
                    }
                }
                writer.write(String.valueOf(table.get("\\n")));
                writer.write("\n");
            }

            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
