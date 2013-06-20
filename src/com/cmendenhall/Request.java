package com.cmendenhall;

import java.util.HashMap;
import java.util.List;

public class Request {
    private String method;
    private String path;
    private String httpVersion;
    private HashMap<String, List<String>> headers;
    private HashMap<String, String> parameters;
    private String body;

    public Request(HashMap<String, String> requestParameters,
                   HashMap<String, List<String>> headerParameters,
                   String bodyContent) {
        method = requestParameters.get("method");
        path = requestParameters.get("path");
        httpVersion = requestParameters.get("httpVersion");
        headers = headerParameters;
        parameters = requestParameters;
        body = bodyContent;
    }

    public String method() {
        return method;
    }

    public String path() {
        return path;
    }

    public String body() {
        return body;
    }

    public String httpVersion() {
        return httpVersion;
    }

    public HashMap<String, String> parameters() {
        return parameters;
    }

    public List<String> getHeader(String header) {
        return headers.get(header);
    }

    public String getParameter(String parameter) {
        return parameters.get(parameter);
    }

    public String toString() {
        return method + " " + path;
    }

}
