package javache.http;

import java.util.*;

public class HttpRequestImpl implements HttpRequest {

    private Map<String, String> headers;
    private Map<String, String> bodyParameters;
    private String method;
    private String requestUrl;

    public HttpRequestImpl(String requestContent) {
        this.headers = new HashMap<>();
        this.bodyParameters = new HashMap<>();
        this.parseRequestContent(requestContent);
    }

    @Override
    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(this.headers);
    }

    @Override
    public Map<String, String> getBodyParameters() {
        return Collections.unmodifiableMap(this.bodyParameters);
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String getRequestUrl() {
        return this.requestUrl;
    }

    @Override
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    @Override
    public void addHeader(String header, String value) {
        this.headers.put(header, value);
    }

    @Override
    public void addBodyParameter(String bodyParameter, String value) {
        this.bodyParameters.put(bodyParameter, value);
    }

    @Override
    public boolean isResource() {
        return false;
    }

    private void parseRequestContent(String requestContent) {
        String[] lines = requestContent.split("\r\n");

        this.parseRequestLine(lines[0]);
        String[] remainLines = Arrays.stream(lines).skip(1).toArray(String[]::new);

        for (int i = 0; i < remainLines.length; i++) {
            if ("".equals(remainLines[i].trim())) {
                if (i + 1 < remainLines.length) {
                    this.parseBodyParameters(remainLines[i + 1]);
                }
                break;
            }
            String[] lineTokens = remainLines[i].split(":\\s");
            this.addHeader(lineTokens[0], lineTokens[1]);
        }
    }

    private void parseRequestLine(String requestLine) {
        String[] requestLineTokens = requestLine.split("\\s+");
        String method = requestLineTokens[0];
        String uri = requestLineTokens[1];

        this.setMethod(method);
        this.setRequestUrl(uri);
    }

    private void parseBodyParameters(String remainLine) {
        String[] parameters = remainLine.split("&");
        for (String parameter : parameters) {
            String[] parameterTokens = parameter.split("=");
            this.addBodyParameter(parameterTokens[0], parameterTokens[1]);
        }
    }
}
