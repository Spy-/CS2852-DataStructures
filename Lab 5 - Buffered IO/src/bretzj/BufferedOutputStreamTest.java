package bretzj;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class BufferedOutputStreamTest {

    private BufferedOutputStream out;
    private ByteArrayOutputStream bout;
    private static final byte[] BYTES = new byte[]{1, 2, 4, 8, 16, 32, 64};

    @BeforeEach
    void setUp() {
        bout = new ByteArrayOutputStream(BYTES.length);
        out = new BufferedOutputStream(bout, BYTES.length);
    }

    @Test
    void write() throws IOException {
        for (byte b : BYTES) {
            out.write(b);
        }
        out.flush();

        assertArrayEquals(BYTES, bout.toByteArray());
    }

    @Test
    void writeToArray() throws IOException {
        out.write(BYTES);
        out.flush();

        assertArrayEquals(BYTES, bout.toByteArray());
    }

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

    @Test
    void flushWithoutWriting() throws IOException {
        out.flush();
        assertArrayEquals(new byte[]{}, bout.toByteArray());
    }
}