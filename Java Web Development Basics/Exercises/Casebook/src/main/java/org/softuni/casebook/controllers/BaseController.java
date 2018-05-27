package org.softuni.casebook.controllers;

import org.softuni.casebook.constants.CasebookConstants;
import org.softuni.javache.http.*;

public abstract class BaseController {
    private HttpSessionStorage sessionStorage;

    protected BaseController(HttpSessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
    }

    protected boolean isLoggedIn(HttpRequest httpRequest) {
        if (!httpRequest.getCookies().containsKey(CasebookConstants.SERVER_SESSION_TOKEN)) {
            return false;
        }

        String sessionId = httpRequest.getCookies().get(CasebookConstants.SERVER_SESSION_TOKEN).getValue();

        if (!this.sessionStorage.getAllSessions().containsKey(sessionId)) {
            return false;
        }

        return true;
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

    protected HttpSessionStorage getSessionStorage() {
        return this.sessionStorage;
    }
}
