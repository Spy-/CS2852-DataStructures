/*
 * Course: CS2852
 * Spring 2019
 * Lab 5 - Buffered IO
 * Name: John Bretz
 * Created: 4/1/2019
 */
package bretzj;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * An Input Stream that has a buffer to minimize the use of the
 * underlying stream as much as possible
 */
public class BufferedInputStream {

    private static final int DEFAULT_SIZE = 8;

    private InputStream in;
    private int bitsRead = 0;
    private int bitsPosition;
    private int pos;
    private byte[] buffer;

    /**
     * Constructor where the internal buffer will be the default size
     *
     * @param in an InputStream
     */
    public BufferedInputStream(InputStream in) {
        this(in, DEFAULT_SIZE);
    }

    /**
     * Constructor
     *
     * @param in   an InputStream
     * @param size the size of buffer
     */
    public BufferedInputStream(InputStream in, int size) {
        this.in = in;
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be > 0");
        }
        buffer = new byte[size];
        pos = size;
    }

    /**
     * Fills the buffer with as much data as possible
     *
     * @return the first value in the buffer that hasn't already been returned
     * @throws IOException some exception
     */
    public int read() throws IOException {
        checkIfBitsLeftover();
        if (pos == buffer.length) {
            flush();
            refill();
        }

        return buffer[pos++];
    }

    /**
     * Fills the given array with data from the buffer
     *
     * @param b the array to fill
     * @return the number of bytes that were filled
     * @throws IOException some exception
     */
    public int read(byte[] b) throws IOException {
        checkIfBitsLeftover();
        return read(b, 0, b.length);
    }

    /**
     * Fills the given array with data from the buffer
     *
     * @param b   the array to fill
     * @param off the index to start inserting into the array
     * @param len the number of bytes to put into the array
     * @return the number of bytes that were filled
     * @throws IOException some exception
     */
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

    /**
     * Will read a single bit from the buffer. Must be called a multiple of 8 times
     * if one wants to call any form of read()
     *
     * @return the next bit
     * @throws IOException some exception
     */
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

    /**
     * Helper method to check if a proper number of bits has been read before reading any bytes
     */
    private void checkIfBitsLeftover() {
        System.out.println("bitsRead = " + bitsRead);
        if (bitsRead % 8 != 0) {
            throw new IllegalStateException();
        }
    }

    /**
     * Refills the buffer with new data when the all the data in the buffer has been read
     *
     * @throws IOException some ejection
     */
    private void refill() throws IOException {
        in.read(buffer);
    }

    /**
     * Resets the buffer to its default value
     */
    public void flush() {
        Arrays.fill(buffer, (byte) -1);
        pos = 0;
    }
}
