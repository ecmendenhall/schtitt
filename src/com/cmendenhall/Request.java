package com.cmendenhall;

import java.util.HashMap;
import java.util.List;

public class Request {
    private String method;
    private String path;
    private String httpVersion;
    private HashMap<String, List<String>> headers;

    public Request(HashMap<String, String> requestParameters,
                   HashMap<String, List<String>> headerParameters) {
        method = requestParameters.get("method");
        path = requestParameters.get("path");
        httpVersion = requestParameters.get("httpVersion");
        headers = headerParameters;
    }

    public String method() {
        return method;
    }

    public String path() {
        return path;
    }

    public String httpVersion() {
        return httpVersion;
    }

    public List<String> getHeader(String header) {
        return headers.get(header);
    }

    public String toString() {
        return method + " " + path;
    }

}
