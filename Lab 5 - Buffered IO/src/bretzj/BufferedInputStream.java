package bretzj;

import java.io.IOException;
import java.io.InputStream;

public class BufferedInputStream {

    private static final int DEFAULT_SIZE = 8;

    private InputStream in;
    private int bitsRead = 0;
    private int bitByte;
    private int pos = -1;
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
    }

    public int read() throws IOException {
        checkIfBitsRead();
        if (pos >= buffer.length) {
            flush();
        }

        try {
            buffer[++pos] = (byte) in.read();
        } catch (ArrayIndexOutOfBoundsException e) {
            flush();
            buffer[++pos] = (byte) in.read();
        }

        return buffer[pos];
    }

    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    private int read(byte[] b, int off, int len) throws IOException {
        checkIfBitsRead();
        int count = 0;
        for (int i = 0; i < len - off; i++) {
            int val = in.read();

            if (val <= 0) {
                return count == 0 ? val : count;
            }

            if (count >= len) {
                return count;
            }

            if (pos >= buffer.length) {
                flush();
            }

            count++;
            buffer[++pos] = b[i] = (byte) val;
        }

        return count;
    }

    public int readBit() throws IOException {
        switch (bitsRead % 8) {
            case 0:
                bitByte = in.read();
                bitsRead++;
                return bitByte >> 7 & 0b00000001;
            case 1:
                bitsRead++;
                return bitByte >> 6 & 0b00000001;
            case 2:
                bitsRead++;
                return bitByte >> 5 & 0b00000001;
            case 3:
                bitsRead++;
                return bitByte >> 4 & 0b00000001;
            case 4:
                bitsRead++;
                return bitByte >> 3 & 0b00000001;
            case 5:
                bitsRead++;
                return bitByte >> 2 & 0b00000001;
            case 6:
                bitsRead++;
                return bitByte >> 1 & 0b00000001;
            default:
                bitsRead++;
                return bitByte & 0b00000001;
        }
    }

    private void checkIfBitsRead() {
        if (bitsRead % 8 != 0) {
            throw new IllegalStateException();
        }
    }

    private void flush() {
        buffer = new byte[buffer.length];
        pos = -1;
    }
}
