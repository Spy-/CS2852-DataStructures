/*
 * Course: CS2852
 * Spring 2019
 * Lab 4 - Auto Complete
 * Name: John Bretz
 * Created: 3/16/2019
 */
package bretzj;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Tests for BufferedOutputStream
 */
class BufferedOutputStreamTest {

    private BufferedOutputStream out;
    private ByteArrayOutputStream bout;
    private static final byte[] BYTES = new byte[] {1, 2, 4, 8, 16, 32, 64};

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

        assertArrayEquals(new byte[] {14, 7, 3, 2, 1, 0, 0}, bout.toByteArray());
    }

    /**
     * send an empty buffer to the underlying stream
     *
     * @throws IOException some exception
     */
    @Test
    void flushWithoutWriting() throws IOException {
        out.flush();
        assertArrayEquals(new byte[] {}, bout.toByteArray());
    }
}