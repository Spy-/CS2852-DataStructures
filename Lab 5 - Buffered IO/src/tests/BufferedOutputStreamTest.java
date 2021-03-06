/*
 * Course: CS2852
 * Spring 2019
 * Lab 5 - Buffered IO
 * Name: John Bretz
 * Created: 4/1/2019
 */
package tests;

import bretzj.BufferedOutputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for BufferedOutputStream
 */
class BufferedOutputStreamTest {

    private BufferedOutputStream out;
    private ByteArrayOutputStream bout;
    private static final byte[] BYTES = new byte[]{1, 2, 4, 8, 16, 32, 64};

    /**
     * Called before each test
     */
    @BeforeEach
    void setUp() {
        bout = new ByteArrayOutputStream(BYTES.length);
        out = new BufferedOutputStream(bout, BYTES.length);
    }

    /**
     * Tests writing a bunch of bytes
     *
     * @throws IOException some exception
     */
    @Test
    void write() throws IOException {
        for (byte b : BYTES) {
            out.write(b);
        }
        out.flush();

        assertArrayEquals(BYTES, bout.toByteArray());
    }

    /**
     * Writes some bytes from a given array
     *
     * @throws IOException some exception
     */
    @Test
    void writeToArray() throws IOException {
        out.write(BYTES);
        out.flush();

        assertArrayEquals(BYTES, bout.toByteArray());
    }

    /**
     * Tests the writing of bits
     *
     * @throws IOException some exception
     */
    @Test
    void writeBit() throws IOException {
        out.writeBit(0);
        out.writeBit(1);
        out.writeBit(0);
        out.writeBit(0);
        out.writeBit(0);
        out.writeBit(1);
        out.writeBit(0);
        out.writeBit(0);

        out.writeBit(0);
        assertThrows(IllegalStateException.class, () -> out.write(1));
        assertThrows(IllegalStateException.class, () -> out.write(new byte[5]));
        out.writeBit(0);
        out.writeBit(0);
        out.writeBit(0);
        out.writeBit(1);
        out.writeBit(1);
        out.writeBit(1);
        out.writeBit(1);

        out.write(5);
        out.flush();

        assertArrayEquals(new byte[]{68, 15, 5, 0, 0, 0, 0}, bout.toByteArray());
    }

    /**
     * sends the buffer to the underlying stream
     *
     * @throws IOException some exception
     */
    @Test
    void flush() throws IOException {
        out.write(14);
        out.write(7);
        out.write(3);
        out.write(2);
        out.write(1);
        out.flush();

        assertArrayEquals(new byte[]{14, 7, 3, 2, 1, 0, 0}, bout.toByteArray());
    }

    /**
     * send an empty buffer to the underlying stream
     *
     * @throws IOException some exception
     */
    @Test
    void flushWithoutWriting() throws IOException {
        out.flush();
        assertArrayEquals(new byte[]{}, bout.toByteArray());
    }
}