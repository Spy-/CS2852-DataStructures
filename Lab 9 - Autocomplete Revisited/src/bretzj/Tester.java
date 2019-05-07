package bretzj;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Tester {

    private static int iterations = 100;
    private static ArrayList<Long> times = new ArrayList<>(iterations);

    public static void main(String[] args) {
        try {
            testCSV();
        } catch (FileNotFoundException ignored) {
        }
    }

    private static void testCSV() throws FileNotFoundException {
        BaseAutoCompleter treeCompleter = new PrefixTreeCompleter();
        BaseAutoCompleter arrayIteratorCompleter = new ArrayIteratorCompleter();
        BaseAutoCompleter arrayIndexCompleter = new ArrayIndexCompleter();
        treeCompleter.initialize("top-1m.csv");
        arrayIteratorCompleter.initialize("top-1m.csv");
        arrayIndexCompleter.initialize("top-1m.csv");

        for (int i = 0; i < iterations; i++) {
            arrayIndexCompleter.allThatBeginsWith("v");
            times.add(arrayIndexCompleter.getLastOperationTime());
        }
        analyzeTimes("CSV - ArrayIndex:");

        for (int i = 0; i < iterations; i++) {
            arrayIteratorCompleter.allThatBeginsWith("v");
            times.add(arrayIteratorCompleter.getLastOperationTime());
        }
        analyzeTimes("CSV - ArrayIterator:");

        for (int i = 0; i < iterations; i++) {
            treeCompleter.allThatBeginsWith("v");
            times.add(treeCompleter.getLastOperationTime());
        }
        analyzeTimes("CSV - PrefixTree:");
    }

    private static void analyzeTimes(String title) {
        long average;
        long min = times.get(0);
        long max = times.get(0);
        long total = 0;

        for (long l : times) {
            total += l;
            if (min > l) {
                min = l;
            }
            if (max < l) {
                max = l;
            }
        }
        average = total / iterations;

        times.clear();

        System.out.println(title);
        System.out.println("\tMin     : " + formatTime(min));
        System.out.println("\tAverage : " + formatTime(average));
        System.out.println("\tMax     : " + formatTime(max));
    }

    private static double roundDecimal(double value) {
        final double modifier = 1000.0;
        return Math.round(value * modifier) / modifier;
    }

    private static String formatTime(long nanos) {
        final float micro = 1000;
        final float milli = 1000000;
        final float secon = 1000000000;

        if (nanos < micro) {
            return nanos + " nanoseconds";
        } else if (nanos < milli) {
            return roundDecimal(nanos / micro) + " microseconds";
        } else if (nanos < secon) {
            return roundDecimal(nanos / milli) + " milliseconds";
        }

        return new SimpleDateFormat("mm:ss.SSS").format(new Date((long) (nanos / milli)));
    }
}
