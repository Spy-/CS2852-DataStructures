package bretzj;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Lab 5 - Buffered IO");

        RunBufferedInputStreamTests();


//        java.io.BufferedInputStream in = new java.io.BufferedInputStream(new FileInputStream(new File("steam recovery code.txt")));
//        System.out.println(in.read());
    }

    private static void RunBufferedInputStreamTests() throws IOException {
        BufferedInputStream input = new BufferedInputStream(new FileInputStream(new File("steam recovery code.txt")));

        System.out.println("Should be 82: " + input.read());
        System.out.print(input.readBit());
        System.out.print(input.readBit());
        System.out.print(input.readBit());
        System.out.print(input.readBit());
        System.out.print(input.readBit());
        System.out.print(input.readBit());
        System.out.print(input.readBit());
        System.out.println(input.readBit());

        System.out.println(input.read());

        byte[] b = new byte[10];
        System.out.println(input.read(b));
        System.out.println(arrayToString(b));

        System.out.println("-----------------------------------------");
        input = new BufferedInputStream(new FileInputStream(new File("steam recovery code.txt")));
        System.out.println(input.read());
        System.out.println(input.read());
        System.out.println(input.read());
        System.out.println(input.read());
        System.out.println(input.read());
        System.out.println(input.read());
    }

    private static String arrayToString(Object array) {
        StringBuilder out = new StringBuilder("[ ");

        switch (array.getClass().getName().charAt(1)) {
            case 'B':
                byte[] bb = (byte[]) array;
                for (byte b : bb) {
                    out.append(b).append(" ");
                }
                break;

            default:
                break;
        }

        out.append("]");

        return out.toString();
    }
}
