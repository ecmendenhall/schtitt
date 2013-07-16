package com.cmendenhall;

import java.util.ArrayList;
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
        body = "";
    }

    public Request makeRequest(String request) {
        List<String> messageParts = split(request, "\r\n\r\n");
        try {
            parseHeaders(messageParts.get(0));
            body = messageParts.get(1);
            if (requestParameters.get("method").equals("POST") ||
                requestParameters.get("method").equals("PUT")) {
                requestParameters.put("postbody", body);
                parseParameters(body);
            }

        } catch (ArrayIndexOutOfBoundsException e) {}
        return new Request(requestParameters, headers, body);
    }

    private void parsePath(String fullPath) {
        List<String> pathParts = split(fullPath, "\\?");
        String path = pathParts.get(0);
        requestParameters.put("path", path);
        try {
            String queryString = pathParts.get(1);
            parseParameters(queryString);
        } catch (ArrayIndexOutOfBoundsException e) {}
    }

    private void parseParameters(String queryString) {
        HashMap<String, String> queryStringParameters = ParameterDecoder.getParameters(queryString);
        for (String parameter : queryStringParameters.keySet()) {
            requestParameters.put(parameter, queryStringParameters.get(parameter));
        }
    }

    private void parseFirstLine(String firstLine) {
        List<String> requestParameterList = split(firstLine, "\\s+");
        requestParameters.put("method", requestParameterList.get(0));
        parsePath(requestParameterList.get(1));
        requestParameters.put("httpVersion", requestParameterList.get(2));
    }

    private void parseHeaders(String headerString) {
        List<String> headerLines = split(headerString, "\n");
        String firstLine = headerLines.get(0);
        parseFirstLine(firstLine);
        for (String headerLine : headerLines.subList(1, headerLines.size())) {
            List<String> headerParameters = split(headerLine, "\\s*:\\s*");

            String headerName = headerParameters.get(0);
            List<String> rawHeaderValues = split(headerParameters.get(1), "\\s*,\\s*");
            List<String> headerValues = new ArrayList<String>();

            for (String value : rawHeaderValues) {
                headerValues.add(value.trim());
                requestParameters.put(headerName, value);
            }

            headers.put(headerName, headerValues);
        }
    }
}
