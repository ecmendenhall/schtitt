package com.cmendenhall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.cmendenhall.Utils.join;

public class Response {
    private HashMap<Integer, String> reasonPhrases;

    private String httpVersion;
    private Integer statusCode;
    private String reasonPhrase;
    private String body;

    private HashMap<String, String> headers;

    public Response() {
        reasonPhrases = new HashMap<Integer, String>();
        reasonPhrases.put(200, "OK");
        reasonPhrases.put(301, "Moved Permanently");
        reasonPhrases.put(302, "Moved Temporarily");
        reasonPhrases.put(404, "Not Found");
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
        body = bodyContent;
    }

    public String getBody() {
        return body;
    }

    public String toString() {
        String statusLine = join(" ", httpVersion, statusCode.toString(), reasonPhrase);

        List<String> headerStrings = new ArrayList<String>();
        for (String header : headers.keySet()) {
            String headerString = header + ": " + headers.get(header);
            headerStrings.add(headerString);
        }

        String allHeaders = join("\n", headerStrings);

        return join("\r\n", statusLine, allHeaders, body, "\r\n");
    }

}
