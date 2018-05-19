package javache.handlers;

import javache.http.HttpRequest;
import javache.http.HttpRequestImpl;
import javache.http.HttpResponse;
import javache.http.HttpResponseImpl;
import javache.utility.Helpers;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler {
    private static final String PAGES_PATH = "/home/vonrepiks/Java/Java Web/Java Web Development Basics/Exercises/HTTP-Protocol/src/resources/pages/";
    private static final String ASSETS_PATH = "/home/vonrepiks/Java/Java Web/Java Web Development Basics/Exercises/HTTP-Protocol/src/resources/assets/";
    private static final String NOT_FOUND_PAGE = "not_found.html";
    private static final Map<String, String> CONTENT_TYPES = new HashMap<String, String>(){{
        put("html", "text/html; charset=utf-8");
        put("text", "text/plain; charset=utf-8");
        put("png", "image/png");
        put("jpg", "image/jpeg");
        put("jpeg", "image/jpeg");
        put("css", "text/css; charset=utf-8");
    }};
    private static final String HTML_EXTENSION = "html";
    private static final String TEXT_PLAIN_EXTENSION = "text";
    private static final int SUCCESS_RESPONSE_CODE = 200;
    private static final int NOT_FOUND_RESPONSE_CODE = 404;


    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    public RequestHandler() {
        this.httpResponse = new HttpResponseImpl();
    }

    byte[] handleRequest(String requestContent) throws IOException {
        this.httpRequest = new HttpRequestImpl(requestContent);

        String filePath;
        String contentType;
        String extension = Helpers.getExtension(this.httpRequest.getRequestUrl());

        if (HTML_EXTENSION.equals(extension)) {
            filePath = PAGES_PATH;
            contentType = CONTENT_TYPES.get(HTML_EXTENSION);
        } else {
            filePath = ASSETS_PATH;
            if (CONTENT_TYPES.containsKey(extension)) {
                contentType = CONTENT_TYPES.get(extension);
            } else {
                contentType = CONTENT_TYPES.get(TEXT_PLAIN_EXTENSION);
            }
        }

        File file = new File(String.format("%s%s", filePath, httpRequest.getRequestUrl().substring(1)));
        byte[] body = new byte[(int) file.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(body);
            for (byte element : body) {
                System.out.print((char) element);
            }
            httpResponse.setStatusCode(SUCCESS_RESPONSE_CODE);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            httpResponse.setStatusCode(NOT_FOUND_RESPONSE_CODE);
            contentType = CONTENT_TYPES.get(HTML_EXTENSION);
            file = new File(String.format("%s%s", PAGES_PATH, NOT_FOUND_PAGE));
            body = new byte[(int) file.length()];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(body);
            for (byte element : body) {
                System.out.print((char) element);
            }
        }

        httpResponse.addHeader("Content-Type", contentType);
        this.addHeaders();
        httpResponse.setContent(body);

        return httpResponse.getBytes();
    }

    private void addHeaders() {
        this.httpResponse.addHeader("Date", String.format("%s", new Date()));
        this.httpResponse.addHeader("Server", "Javache/1.1.1");
    }
}
