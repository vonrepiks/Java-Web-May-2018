package javache.http;

import javache.constants.WebConstants;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HttpResponseImpl implements HttpResponse {

    private Map<String, String> headers;
    private byte[] content;
    private HttpStatus statusCode;

    public HttpResponseImpl() {
        this.setContent(new byte[0]);
        this.headers = new HashMap<>();
    }

    @Override
    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(this.headers);
    }

    @Override
    public HttpStatus getStatusCode() {
        return this.statusCode;
    }

    @Override
    public byte[] getContent() {
        return this.content;
    }

    @Override
    public byte[] getBytes() {
        byte[] headersBytes = this.getHeadersBytes();
        byte[] bodyBytes = this.getContent();

        byte[] fullResponse = new byte[headersBytes.length + bodyBytes.length];

        System.arraycopy(headersBytes, 0, fullResponse, 0, headersBytes.length);
        System.arraycopy(bodyBytes, 0, fullResponse, headersBytes.length, bodyBytes.length);

        return fullResponse;
    }

    @Override
    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public void addHeader(String header, String value) {
        this.headers.put(header, value);
    }

    private byte[] getHeadersBytes() {
        String responseLine = String.format("%s %s%s", WebConstants.HTTP_PROTOCOL_1, this.getStatusCode().getStatusPhrase(), System.lineSeparator());
        StringBuilder headers = new StringBuilder(responseLine);
        for (Map.Entry<String, String> entry : this.headers.entrySet()) {
            headers.append(entry.getKey()).append(": ").append(entry.getValue()).append(System.lineSeparator());
        }

        headers.append(String.format("Date: %s", new Date())).append(System.lineSeparator());
        headers.append("Server: Javache/1.1.1").append(System.lineSeparator());
        headers.append(System.lineSeparator());

        return headers.toString().getBytes();
    }
}
