package org.softuni.toyote;

import org.softuni.javache.api.RequestHandler;
import org.softuni.javache.http.*;
import org.softuni.javache.io.Reader;
import org.softuni.javache.io.Writer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ResourceHandler implements RequestHandler {
    private static final String APPLICATION_RESOURCES_FOLDER_NAME = "resources";
    private static final String RESOURCE_NOT_FOUND_MESSAGE = "<h1 style=\"text-align: center;\">The resource - \"%s\" you are looking for cannot be found! ! !</h1>";

    private final String serverRootFolderPath;
    private boolean hasIntercepted;

    public ResourceHandler(String serverRootFolderPath) {
        this.serverRootFolderPath = serverRootFolderPath;
        this.hasIntercepted = false;
    }

    private String getApplicationName(String requestUrl) {
        requestUrl = requestUrl.substring(1);

        if (requestUrl.contains("/")) {
            return requestUrl.substring(0, requestUrl.indexOf("/"));
        }

        return "webapps/ROOT/classes";
    }

    private String getResourceName(String requestUrl) {
        return requestUrl.substring(requestUrl.lastIndexOf("/") + 1);
    }

    private void notFound(String resourceName, HttpResponse httpResponse) {
        httpResponse.setStatusCode(HttpStatus.NOT_FOUND);
        httpResponse.addHeader("Content-Type", "text/html");
        httpResponse.setContent(String.format(RESOURCE_NOT_FOUND_MESSAGE, resourceName).getBytes());
    }

    private void handleResourceRequest(String resourcesFolder, String resourceName, HttpResponse httpResponse) {
        try {
            Path resourcePath = Paths.get(new File(resourcesFolder + File.separator + resourceName).getCanonicalPath());

            byte[] resourceContent = Files.readAllBytes(resourcePath);

            String mimeType = Files.probeContentType(resourcePath);

            httpResponse.setStatusCode(HttpStatus.OK);
            httpResponse.addHeader("Content-Type", mimeType);
            httpResponse.addHeader("Content-Length", String.valueOf(resourceContent.length));
            httpResponse.addHeader("Content-Disposition", "inline");
            httpResponse.setContent(resourceContent);
        } catch (IOException e) {
            this.notFound(resourceName, httpResponse);
        }
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream) {
        try {
            HttpRequest httpRequest = new HttpRequestImpl(Reader.readAllLines(inputStream));
            HttpResponse httpResponse = new HttpResponseImpl();

            String resourcesFolder =
                    this.serverRootFolderPath +
                            this.getApplicationName(httpRequest.getRequestUrl()) +
                            File.separator +
                            APPLICATION_RESOURCES_FOLDER_NAME;

            String resourceName = this.getResourceName(httpRequest.getRequestUrl());

            this.handleResourceRequest(resourcesFolder, resourceName, httpResponse);

            Writer.writeBytes(httpResponse.getBytes(), outputStream);

            this.hasIntercepted = true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            this.hasIntercepted = false;
        }
    }

    @Override
    public boolean hasIntercepted() {
        return this.hasIntercepted;
    }
}
