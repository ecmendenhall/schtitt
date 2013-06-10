package com.cmendenhall;

import com.cmendenhall.Request;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.cmendenhall.Utils.split;

public class RequestParser {
    private HashMap<String, String> requestParameters;
    private HashMap<String, List<String>> headers;
    private String body;

    public RequestParser() {
        requestParameters = new HashMap<String, String>();
        headers = new HashMap<String, List<String>>();
    }

    public Request parse(String request) {
        List<String> messageParts = split(request, "\r\n");
        try {
            parseFirstLine(messageParts.get(0));
            parseHeaders(messageParts.get(1));
        } catch (ArrayIndexOutOfBoundsException e) {}
        return new Request(requestParameters, headers);
    }

    private void parseFirstLine(String firstLine) {
        List<String> requestParameterList = split(firstLine, "\\s+");
        requestParameters.put("method", requestParameterList.get(0));
        requestParameters.put("path", requestParameterList.get(1));
        requestParameters.put("httpVersion", requestParameterList.get(2));
    }

    private void parseHeaders(String headerString) {
        List<String> headerLines = split(headerString, "\n");
        for (String headerLine : headerLines) {
            List<String> headerParameters = split(headerLine, "\\s*:\\s*");

            String headerName = headerParameters.get(0);
            List<String> headerValues = split(headerParameters.get(1), "\\s*,\\s*");

            headers.put(headerName, headerValues);
        }
    }

}
