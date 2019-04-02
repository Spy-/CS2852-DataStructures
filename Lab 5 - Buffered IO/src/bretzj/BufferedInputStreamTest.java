/*
 * Course: CS2852
 * Spring 2019
 * Lab 5 - Buffered IO
 * Name: John Bretz
 * Created: 4/1/2019
 */
package bretzj;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for BufferedInputStream's
 */
class BufferedInputStreamTest {

    private BufferedInputStream input;
    private static final byte[] BYTES = new byte[] {1, 2, 4, 8, 16, 32, 64};
    private static final int ERROR = -1;

    /**
     * Called before each test
     */
    @BeforeEach
    void setUp() {
        input = new BufferedInputStream(new ByteArrayInputStream(BYTES), BYTES.length);
    }

    /**
     * Read until the end of file
     *
     * @throws IOException some exception
     */
    @Test
    void read() throws IOException {
        for (byte b : BYTES) {
            assertEquals(b, input.read());
        }
        assertEquals(ERROR, input.read());
    }

    /**
     * Have read values stored into a array that is smaller than the buffered array
     *
     * @throws IOException some exception
     */
    @Test
    void readAndCopyToSmallArray() throws IOException {
        byte[] underSized = new byte[6];
        assertEquals(underSized.length, input.read(underSized));
        assertArrayEquals(new byte[] {1, 2, 4, 8, 16, 32}, underSized);
    }

    /**
     * Have read values stored into an array that is bigger than the buffered array
     *
     * @throws IOException some exception
     */
    @Test
    void readAndCopyToBigArray() throws IOException {
        byte[] overSized = new byte[12];
        assertEquals(7, input.read(overSized));
        assertArrayEquals(new byte[] {1, 2, 4, 8, 16, 32, 64, 0, 0, 0, 0, 0}, overSized);
    }

    /**
     * Have read values stored into an array after an arbitrary number of .read()'s
     *
     * @throws IOException some exception
     */
    @Test
    void readAndCopyAfterRead() throws IOException {
        byte[] array = new byte[8];
        input.read();
        input.read();
        assertEquals(5, input.read(array));
        assertArrayEquals(new byte[]{4, 8, 16, 32, 64, 0, 0, 0}, array);
    }

    /**
     * Tests if bits are read properly and the proper errors are thrown if not enough bits are read
     *
     * @throws IOException some exception
     */
    @Test
    void readBit() throws IOException {
        // read a whole byte
        assertEquals(0, input.readBit());
        assertEquals(0, input.readBit());
        assertEquals(0, input.readBit());
        assertEquals(0, input.readBit());
        assertEquals(0, input.readBit());
        assertEquals(0, input.readBit());
        assertEquals(0, input.readBit());
        assertEquals(1, input.readBit());

        // test calling read() after an invalid number of bits read
        input.readBit();
        assertThrows(IllegalStateException.class, input::read); // read(int)
        assertThrows(IllegalStateException.class, input::read); // read(byte[])

        // finish reading bits and try to read again
        input.readBit();
        input.readBit();
        input.readBit();
        input.readBit();
        input.readBit();
        input.readBit();
        input.readBit();
        assertEquals(4, input.read());
    }
}