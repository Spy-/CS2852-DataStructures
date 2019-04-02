package bretzj;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class BufferedInputStream {

    private static final int DEFAULT_SIZE = 8;

    private InputStream in;
    private int bitsRead = 0;
    private int bitsPosition;
    private int pos;
    private byte[] buffer;

    public BufferedInputStream(InputStream in) {
        this(in, DEFAULT_SIZE);
    }

    public BufferedInputStream(InputStream in, int size) {
        this.in = in;
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be > 0");
        }
        buffer = new byte[size];
        pos = size;
    }

    public int read() throws IOException {
        checkIfBitsLeftover();
        if (pos == buffer.length) {
            flush();
            refill();
        }

        return buffer[pos++];
    }

    public int read(byte[] b) throws IOException {
        checkIfBitsLeftover();
        return read(b, 0, b.length);
    }

    private int read(byte[] b, int off, int len) throws IOException {
        checkIfBitsLeftover();
        int count = 0;
        if (pos == buffer.length) {
            flush();
            refill();
        }
        for (int i = off; i < len; i++) {
            if (pos == buffer.length) {
                flush();
                refill();
            }
            if (buffer[pos] == -1) {
                return count;
            }
            b[i] = buffer[pos++];
            count++;
        }
        return count;
    }

    public int readBit() throws IOException {
        if (pos == buffer.length) {
            flush();
            refill();
        }

        switch (bitsRead % 8) {
            case 0:
                bitsRead++;
                bitsPosition = pos++;
                return buffer[bitsPosition] >> 7 & 0b00000001;
            case 1:
                bitsRead++;
                return buffer[bitsPosition] >> 6 & 0b00000001;
            case 2:
                bitsRead++;
                return buffer[bitsPosition] >> 5 & 0b00000001;
            case 3:
                bitsRead++;
                return buffer[bitsPosition] >> 4 & 0b00000001;
            case 4:
                bitsRead++;
                return buffer[bitsPosition] >> 3 & 0b00000001;
            case 5:
                bitsRead++;
                return buffer[bitsPosition] >> 2 & 0b00000001;
            case 6:
                bitsRead++;
                return buffer[bitsPosition] >> 1 & 0b00000001;
            default:
                bitsRead++;
                return buffer[bitsPosition] & 0b00000001;
        }
    }

    private void checkIfBitsLeftover() {
        System.out.println("bitsRead = " + bitsRead);
        if (bitsRead % 8 != 0) {
            throw new IllegalStateException();
        }
    }

    private void refill() throws IOException {
        in.read(buffer);
    }

    public void flush() {
        Arrays.fill(buffer, (byte) -1);
        pos = 0;
    }
}
