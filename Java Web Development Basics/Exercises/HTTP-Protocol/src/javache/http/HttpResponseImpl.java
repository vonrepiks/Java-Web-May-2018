package javache.http;

import javache.constants.WebConstants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpResponseImpl implements HttpResponse {
    private static final Map<Integer, String> RESPONSE_LINE = new HashMap<Integer, String>(){{
        put(200, "200 OK");
        put(404, "404 Not Found");
    }};

    private Map<String, String> headers;
    private byte[] content;
    private int statusCode;

    public HttpResponseImpl() {
        this.headers = new HashMap<>();
    }

    @Override
    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(this.headers);
    }

    @Override
    public int getStatusCode() {
        return this.statusCode;
    }

    @Override
    public byte[] getContent() {
        return this.content;
    }

    @Override
    public byte[] getBytes() {
        String responseLine = String.format("%s %s%s", WebConstants.HTTP_PROTOCOL_1, RESPONSE_LINE.get(this.statusCode), System.lineSeparator());
        StringBuilder headers = new StringBuilder();
        for (Map.Entry<String,String> entry : this.headers.entrySet()) {
            headers.append(entry.getKey()).append(": ").append(entry.getValue()).append(System.lineSeparator());
        }
        headers.append(System.lineSeparator());

        byte[] responseLineByteArray = responseLine.getBytes();
        byte[] headersByteArray = headers.toString().getBytes();
        byte[] contentByteArray = this.getContent();

        byte[] result = new byte[responseLineByteArray.length + headersByteArray.length + content.length];

        System.arraycopy(responseLineByteArray, 0, result, 0, responseLineByteArray.length);
        System.arraycopy(headersByteArray, 0, result, responseLineByteArray.length, headersByteArray.length);
        System.arraycopy(contentByteArray, 0, result, responseLineByteArray.length + headersByteArray.length, contentByteArray.length);

        return result;
    }

    @Override
    public void setStatusCode(int statusCode) {
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
}
