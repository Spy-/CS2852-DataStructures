/*
 * Course: CS2852
 * Spring 2019
 * Lab 5 - Buffered IO
 * Name: John Bretz
 * Created: 4/1/2019
 */
package bretzj;

import java.io.IOException;
import java.io.OutputStream;

/**
 * An Output Stream that has a buffer to minimize the use of the
 * underlying stream as much as possible
 */
public class BufferedOutputStream {
    private OutputStream out;
    private byte[] buffer;
    private static final int DEFAULT_SIZE = 8;
    private int count = 0;
    private int bitCount = 0;
    private int bitPos;

    /**
     * Basic constructor that will use the default buffer size
     *
     * @param out an OutputStream
     */
    public BufferedOutputStream(OutputStream out) {
        this(out, DEFAULT_SIZE);
    }

    /**
     * Constructor
     *
     * @param out  an OutputStream
     * @param size the buffer size
     */
    public BufferedOutputStream(OutputStream out, int size) {
        this.out = out;
        buffer = new byte[size];
    }

    /**
     * Writes the given int to the buffer
     *
     * @param b an int
     * @throws IOException some exception
     */
    public void write(int b) throws IOException {
        checkBitCount();
        if (count >= buffer.length) {
            flushBuffer();
        }
        buffer[count++] = (byte) b;
    }

    /**
     * Writes the given bytes to the buffer
     *
     * @param b an array of bytes
     * @throws IOException some exception
     */
    public void write(byte[] b) throws IOException {
        checkBitCount();
        write(b, 0, b.length);
    }

    /**
     * Writes the given bytes to the buffer
     *
     * @param b   an array of bytes
     * @param off the index to start writing values in the array
     * @param len how many bytes to write
     * @throws IOException some exception
     */
    private void write(byte[] b, int off, int len) throws IOException {
        if (len >= buffer.length) {
            flushBuffer();
            out.write(b);
        } else if (len > buffer.length - count) {
            flushBuffer();
            System.arraycopy(b, off, buffer, count, len);
            count += len;
        } else {
            System.arraycopy(b, off, buffer, count, len);
            count += len;
        }
    }

    /**
     * Writes a bit
     *
     * @param bit the bit
     * @throws IOException some exception
     */
    public void writeBit(int bit) throws IOException {
        if (bit != 1 && bit != 0) {
            throw new IllegalArgumentException("Can only pass a zero or a one");
        }
        if (count >= buffer.length) {
            flushBuffer();
        }

        switch (bitCount % 8) {
            case 0:
                bitPos = count++;
                buffer[bitPos] = (byte) (bit << 7);
                bitCount++;
                break;
            case 1:
                buffer[bitPos] = (byte) ((bit << 6) | buffer[bitPos]);
                bitCount++;
                break;
            case 2:
                buffer[bitPos] = (byte) ((bit << 5) | buffer[bitPos]);
                bitCount++;
                break;
            case 3:
                buffer[bitPos] = (byte) ((bit << 4) | buffer[bitPos]);
                bitCount++;
                break;
            case 4:
                buffer[bitPos] = (byte) ((bit << 3) | buffer[bitPos]);
                bitCount++;
                break;
            case 5:
                buffer[bitPos] = (byte) ((bit << 2) | buffer[bitPos]);
                bitCount++;
                break;
            case 6:
                buffer[bitPos] = (byte) ((bit << 1) | buffer[bitPos]);
                bitCount++;
                break;
            case 7:
                buffer[bitPos] = (byte) (bit | buffer[bitPos]);
                bitCount++;
                break;
        }
    }

    /**
     * Sends the buffer to be written to the underlying OutputStream
     *
     * @throws IOException some exception
     */
    private void flushBuffer() throws IOException {
        if (count > 0) {
            out.write(buffer);
            count = 0;
        }
    }

    /**
     * Writes the buffer to the output
     *
     * @throws IOException some exception
     */
    public void flush() throws IOException {
        flushBuffer();
        out.flush();
    }

    /**
     * Throws an IllegalStateException if an improper number of bits has been written
     * before trying to write a byte
     */
    private void checkBitCount() {
        if (bitCount % 8 != 0) {
            throw new IllegalStateException();
        }
    }
}
