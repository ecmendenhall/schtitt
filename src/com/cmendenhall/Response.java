package com.cmendenhall;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.cmendenhall.Utils.join;

public class Response {
    private HashMap<Integer, String> reasonPhrases;

    private String httpVersion;
    private Integer statusCode;
    private String reasonPhrase;
    private byte[] body;

    private HashMap<String, String> headers;

    public Response() {
        reasonPhrases = new HashMap<Integer, String>();
        reasonPhrases.put(200, "OK");
        reasonPhrases.put(201, "Created");
        reasonPhrases.put(206, "Partial Content");
        reasonPhrases.put(301, "Moved Permanently");
        reasonPhrases.put(302, "Found");
        reasonPhrases.put(303, "See Other");
        reasonPhrases.put(400, "Bad Request");
        reasonPhrases.put(401, "Unauthorized");
        reasonPhrases.put(404, "Not Found");
        reasonPhrases.put(405, "Method not Allowed");
        reasonPhrases.put(418, "I'm a Teapot");
        reasonPhrases.put(500, "Server Error");

        headers = new HashMap<String, String>();
    }

    public void httpVersion(String version) {
        httpVersion = "HTTP/" + version;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void statusCode(String code) {
        statusCode = Integer.parseInt(code);
        reasonPhrase = reasonPhrases.get(statusCode);
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public void setHeader(String header, String value) {
        headers.put(header, value);
    }

    public String getHeader(String header) {
        return headers.get(header);
    }

    public void body(String bodyContent) {
        try {
            body = bodyContent.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void body(byte[] bodyContent) {
        body = bodyContent;
    }

    public String bodyString() throws UnsupportedEncodingException {
        return new String(body, "UTF-8");
    }

    protected byte[] bodyBytes() throws UnsupportedEncodingException {
        return body;
    }

    public String statusLineString() {
        return join(" ", httpVersion, statusCode.toString(), reasonPhrase);
    }

    public String headersString() {
        List<String> headerStrings = new ArrayList<String>();
        for (String header : headers.keySet()) {
            String headerString = header + ": " + headers.get(header);
            headerStrings.add(headerString);
        }

        return join("\r\n", headerStrings);
    }

    public byte[] toBytes() throws UnsupportedEncodingException {
        byte[] carriageReturnNewline = "\r\n".getBytes("UTF-8");
        return join(statusLineString().getBytes("UTF-8"),
                    carriageReturnNewline,
                    headersString().getBytes("UTF-8"),
                    carriageReturnNewline,
                    carriageReturnNewline,
                    bodyBytes(),
                    carriageReturnNewline,
                    carriageReturnNewline);
    }

}
