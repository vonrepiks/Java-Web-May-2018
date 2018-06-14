package org.softuni.broccolina.solet;

import org.softuni.javache.http.HttpStatus;

public class BaseHttpSolet implements HttpSolet {
    private static final String GET_METHOD_NAME = "GET";
    private static final String POST_METHOD_NAME = "POST";
    private static final String PUT_METHOD_NAME = "PUT";
    private static final String DELETE_METHOD_NAME = "DELETE";
    private static final String DEFAULT_NOT_FOUND_RESPONSE_MESSAGE = "[ERROR] %s %s [MESSAGE] The page or functionality are looking for is not found!";

    private void configureNotFound(HttpSoletResponse httpSoletResponse, String method, String requestUrl) {
        httpSoletResponse.setStatusCode(HttpStatus.NOT_FOUND);
        httpSoletResponse.addHeader("Content-Type", "text/html");
        httpSoletResponse.setContent(String.format(DEFAULT_NOT_FOUND_RESPONSE_MESSAGE, method, requestUrl).getBytes());
    }

    protected void doGet(HttpSoletRequest httpSoletRequest, HttpSoletResponse httpSoletResponse) {
        this.configureNotFound(httpSoletResponse, GET_METHOD_NAME, httpSoletRequest.getRequestUrl());
    }

    protected void doPost(HttpSoletRequest httpSoletRequest, HttpSoletResponse httpSoletResponse) {
        this.configureNotFound(httpSoletResponse, POST_METHOD_NAME, httpSoletRequest.getRequestUrl());
    }

    protected void doPut(HttpSoletRequest httpSoletRequest, HttpSoletResponse httpSoletResponse) {
        this.configureNotFound(httpSoletResponse, PUT_METHOD_NAME, httpSoletRequest.getRequestUrl());
    }

    protected void doDelete(HttpSoletRequest httpSoletRequest, HttpSoletResponse httpSoletResponse) {
        this.configureNotFound(httpSoletResponse, DELETE_METHOD_NAME, httpSoletRequest.getRequestUrl());
    }

    @Override
    public void init(SoletConfig soletConfig) {

    }

    @Override
    public SoletConfig getSoletConfig() {
        return null;
    }

    @Override
    public void service(HttpSoletRequest httpSoletRequest, HttpSoletResponse httpSoletResponse) {
        if (GET_METHOD_NAME.equals(httpSoletRequest.getMethod())) {
            this.doGet(httpSoletRequest, httpSoletResponse);
        } else if (POST_METHOD_NAME.equals(httpSoletRequest.getMethod())) {
            this.doPost(httpSoletRequest, httpSoletResponse);
        } else if (PUT_METHOD_NAME.equals(httpSoletRequest.getMethod())) {
            this.doPut(httpSoletRequest, httpSoletResponse);
        } else if (DELETE_METHOD_NAME.equals(httpSoletRequest.getMethod())) {
            this.doDelete(httpSoletRequest, httpSoletResponse);
        }
    }
}
