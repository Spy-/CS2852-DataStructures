package bretzj;

import java.io.IOException;
import java.io.OutputStream;

public class BufferedOutputStream {
    private OutputStream out;
    private byte[] buffer;
    private static final int DEFAULT_SIZE = 8;
    private int count = 0;

    public BufferedOutputStream(OutputStream out) {
        this(out, DEFAULT_SIZE);
    }

    public BufferedOutputStream(OutputStream out, int size) {
        this.out = out;
        buffer = new byte[size];
    }

    public void write(int b) throws IOException {
        if (count >= buffer.length) {
            flushBuffer();
        }
        buffer[count++] = (byte) b;
    }

    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

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

    private void flushBuffer() throws IOException {
        if (count > 0) {
            out.write(buffer);
            count = 0;
        }
    }

    public void flush() throws IOException {
        flushBuffer();
        out.flush();
    }
}
