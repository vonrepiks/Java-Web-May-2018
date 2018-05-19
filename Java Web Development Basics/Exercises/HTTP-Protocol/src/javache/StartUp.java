package javache;

import java.io.IOException;

public class StartUp {
    private static final int PORT = 5000;
    public static void main(String[] args) {
        Server server = new Server(PORT);

        try {
            server.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
