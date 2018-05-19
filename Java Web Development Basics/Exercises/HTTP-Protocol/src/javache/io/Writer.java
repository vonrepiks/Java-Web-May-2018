package javache.io;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class Writer {
    private Writer() {}

    public static void writeBytes(byte[] byteData, OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        dataOutputStream.write(byteData);
    }
}
