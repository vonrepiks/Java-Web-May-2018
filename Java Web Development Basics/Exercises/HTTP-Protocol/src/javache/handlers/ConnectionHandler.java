package javache.handlers;

import javache.io.Reader;
import javache.io.Writer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ConnectionHandler extends Thread {
    private RequestHandler requestHandler;
    private Socket clientSocket;
    private InputStream clientInputStream;
    private OutputStream clientOutputStream;

    public ConnectionHandler(Socket clientSocket, RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
        this.initializeConnection(clientSocket);
    }

    @Override
    public void run() {
        try {
            String requestContent = Reader.readAllLines(this.clientInputStream);
            byte[] responseContent = this.requestHandler.handleRequest(requestContent);
            Writer.writeBytes(responseContent, this.clientOutputStream);
            this.clientInputStream.close();
            this.clientOutputStream.close();
            this.clientSocket.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void initializeConnection(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            this.clientInputStream = this.clientSocket.getInputStream();
            this.clientOutputStream= this.clientSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
