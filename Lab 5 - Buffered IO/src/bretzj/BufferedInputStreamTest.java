package bretzj;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BufferedInputStreamTest {

    private BufferedInputStream input;
    private static final byte[] BYTES = new byte[]{1, 2, 4, 8, 16, 32, 64};

    @BeforeEach
    void setUp() {
        input = new BufferedInputStream(new ByteArrayInputStream(BYTES), BYTES.length);
    }

    @Test
    void read() throws IOException {
        assertEquals(1, input.read());
        assertEquals(2, input.read());
        assertEquals(4, input.read());
        assertEquals(8, input.read());
        assertEquals(16, input.read());
        assertEquals(32, input.read());
        assertEquals(64, input.read());
        assertEquals(-1, input.read());
    }

    @Test
    void readAndCopyToSmallArray() throws IOException {
        byte[] underSized = new byte[6];
        assertEquals(6, input.read(underSized));
        assertArrayEquals(new byte[]{1, 2, 4, 8, 16, 32}, underSized);
    }

    @Test
    void readAndCopyToBigArray() throws IOException {
        byte[] overSized = new byte[12];
        assertEquals(7, input.read(overSized));
        assertArrayEquals(new byte[]{1, 2, 4, 8, 16, 32, 64, 0, 0, 0, 0, 0}, overSized);
    }

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
        assertThrows(IllegalStateException.class, input::read);

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