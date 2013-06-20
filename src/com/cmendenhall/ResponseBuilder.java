package com.cmendenhall;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ResponseBuilder {
    private DateFormat dateFormat;
    private RequestRouter requestRouter;

    public ResponseBuilder() {
        dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
        requestRouter = new RequestRouter();
    }

    private String getCurrentDate() {
        Date now = new Date();
        return dateFormat.format(now);
    }

    private void addDefaultHeaders(Response response) {
        response.httpVersion("1.1");
        response.setHeader("Server", "Schtitt/0.9a");
        response.setHeader("Date", getCurrentDate());
        response.setHeader("Pragma", "no-cache");
    }

    private void addFileHeaders(Response response, WebResource file) {
        response.setHeader("Content-Type", file.mimeType());
        response.setHeader("Content-Length", file.contentLength());
        response.setHeader("Content-MD5", file.checkSum());
    }

    public Response constructResponse(String method, String path) {
        return constructResponse(method, path, new HashMap<String, String>());
    }

    public Response constructResponse(String method, String path, HashMap<String, String> queryParameters) {

        Response response = new Response();

        if (method.equals("HEAD")) {
            response = constructResponse("GET", path, queryParameters);
            response.body(new byte[0]);
            return response;
        }

        addDefaultHeaders(response);

        if (method.equals("POST")) {
            if(requestRouter.routeRegistered(method, path)) {
                response.statusCode("302");
                WebResource requestedResource = requestRouter.getResource(method, path, queryParameters);
                response.setHeader("Location", requestedResource.url());
                response.setHeader("Content-Length", "0");
            } else {
                response.statusCode("405");
            }
            response.body(new byte[0]);
        }

        if (method.equals("GET")) {
            if (requestRouter.pathExists(method, path) ||
                requestRouter.routeRegistered(method, path)) {
                response.statusCode("200");
            } else {
                response.statusCode("404");
            }
            WebResource requestedResource = requestRouter.getResource(method, path, queryParameters);
            response.body(requestedResource.binaryData());
            addFileHeaders(response, requestedResource);
        }

        return response;
    }

}
