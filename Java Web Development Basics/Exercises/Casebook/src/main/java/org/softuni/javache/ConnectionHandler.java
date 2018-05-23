package org.softuni.javache;

import org.softuni.javache.api.RequestHandler;
import org.softuni.javache.io.Reader;
import org.softuni.javache.io.Writer;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.Set;

public class ConnectionHandler extends Thread {
    private static final int ITERATION_TO_KILL_REQUEST = 5000;

    private Socket clientSocket;

    private InputStream clientSocketInputStream;

    private OutputStream clientSocketOutputStream;

    private Set<RequestHandler> requestHandlers;

    public ConnectionHandler(Socket clientSocket, Set<RequestHandler> requestHandlers) {
        this.initializeConnection(clientSocket);
        this.requestHandlers = requestHandlers;
    }

    private void initializeConnection(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            this.clientSocketInputStream = this.clientSocket.getInputStream();
            this.clientSocketOutputStream = this.clientSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String requestContent = null;
            for (int i = 0; i < ITERATION_TO_KILL_REQUEST; i++) {
                requestContent = Reader.readAllLines(this.clientSocketInputStream);
                if (requestContent != null && requestContent.length() > 0) {
                    break;
                }
            }

            byte[] responseContent = null;

            for (RequestHandler requestHandler : this.requestHandlers) {
                responseContent = requestHandler.handleRequest(requestContent);

                if (requestHandler.hasIntercepted()) {
                    break;
                }
            }

            Writer.writeBytes(responseContent, this.clientSocketOutputStream);
            this.clientSocketInputStream.close();
            this.clientSocketOutputStream.close();
            this.clientSocket.close();
        } catch (IOException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}






