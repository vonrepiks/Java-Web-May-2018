package org.softuni.toblerone;

import org.softuni.javache.api.RequestHandler;
import org.softuni.javache.http.HttpResponse;
import org.softuni.javache.http.HttpResponseImpl;
import org.softuni.javache.http.HttpStatus;
import org.softuni.javache.io.Writer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TobleroneRequestHandler implements RequestHandler {
    private boolean hasIntercepted = false;

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream) {
        try {

            HttpResponse httpResponse = new HttpResponseImpl();

            httpResponse.setStatusCode(HttpStatus.OK);
            httpResponse.addHeader("Content-Type", "text/html");
            httpResponse.setContent("<h1>Hi dude! I'm Toblerone!</h1>".getBytes());

            this.hasIntercepted = true;

            Writer.writeBytes(httpResponse.getBytes(), outputStream);
        } catch (IOException ioe) {
            hasIntercepted = false;
            ioe.printStackTrace();
        }
    }

    @Override
    public boolean hasIntercepted() {
        return this.hasIntercepted;
    }
}
