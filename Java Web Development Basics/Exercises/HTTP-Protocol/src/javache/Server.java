package javache;

import javache.handlers.ConnectionHandler;
import javache.handlers.RequestHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.FutureTask;

public class Server {
    private static final String LISTENING_MESSAGE = "Listening on port: ";
    private static final int SOCKET_TIMEOUT_MILLISECONDS = 5000;
    private static final String TIMEOUT_DETECTION_MESSAGE = "Timeout detected!";

    private ServerSocket serverSocket;
    private int port;
    private int timeouts;

    public Server(int port) {
        this.port = port;
        this.timeouts = 0;
    }

    public void run() throws IOException {
        this.serverSocket = new ServerSocket(this.port);
        System.out.println(LISTENING_MESSAGE + this.port);

        this.serverSocket.setSoTimeout(SOCKET_TIMEOUT_MILLISECONDS);

        while (true) {
            try (Socket clientSocket = this.serverSocket.accept()) {
                clientSocket.setSoTimeout(SOCKET_TIMEOUT_MILLISECONDS);

                ConnectionHandler connectionHandler = new ConnectionHandler(clientSocket, new RequestHandler());
                FutureTask<?> task = new FutureTask<>(connectionHandler, null);
                task.run();
            } catch (SocketTimeoutException stoe) {
                System.out.println(TIMEOUT_DETECTION_MESSAGE);
                this.timeouts++;
            }
        }
    }
}
