package org.softuni.casebook.controllers;

import org.softuni.casebook.utility.MimeTypeManager;
import org.softuni.javache.WebConstants;
import org.softuni.javache.http.HttpResponse;
import org.softuni.javache.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class BaseController {
    private static final String HTML_EXTENSION_AND_SEPARATOR = ".html";

    protected BaseController() {
    }

    protected byte[] ok(byte[] content, HttpResponse httpResponse) {
        httpResponse.setStatusCode(HttpStatus.OK);
        httpResponse.setContent(content);
        return httpResponse.getBytes();
    }

    protected byte[] badRequest(byte[] content, HttpResponse httpResponse) {
        httpResponse.setStatusCode(HttpStatus.BAD_REQUEST);
        httpResponse.setContent(content);
        return httpResponse.getBytes();
    }

    protected byte[] notFound(byte[] content, HttpResponse httpResponse) {
        httpResponse.setStatusCode(HttpStatus.NOT_FOUND);
        httpResponse.setContent(content);
        return httpResponse.getBytes();
    }

    protected byte[] redirect(byte[] content, String location, HttpResponse httpResponse) {
        httpResponse.setStatusCode(HttpStatus.SEE_ORDER);
        httpResponse.addHeader("Location", location);
        httpResponse.setContent(content);
        return httpResponse.getBytes();
    }

    protected byte[] internalServerError(byte[] content, HttpResponse httpResponse) {
        httpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        httpResponse.setContent(content);
        return httpResponse.getBytes();
    }

    protected byte[] processPageRequest(String page, HttpResponse httpResponse) {
        String pagePath = WebConstants.PAGES_FOLDER_PATH +
                page
                + HTML_EXTENSION_AND_SEPARATOR;

        File file = new File(pagePath);

        if(!file.exists() || file.isDirectory()) {
            return this.notFound(("Page not found!").getBytes(), httpResponse);
        }

        byte[] result;

        try {
            result = Files.readAllBytes(Paths.get(pagePath));
        } catch (IOException e) {
            return this.internalServerError(("Something went wrong!").getBytes(), httpResponse);
        }

        httpResponse.addHeader("Content-Type", MimeTypeManager.getMimeType(file.getName()));

        return this.ok(result, httpResponse);
    }
}
