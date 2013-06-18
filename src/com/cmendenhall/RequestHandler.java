package com.cmendenhall;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestHandler implements Runnable {
    private RequestParser requestParser;
    private RequestRouter requestRouter;
    private MessageLogger logger;
    private String rawRequest;
    private Request request;
    private Response response;
    private DateFormat dateFormat;
    private HTTPClientSocket socket;

    public RequestHandler(String rawRequestString, HTTPClientSocket HTTPClientSocket) {
        rawRequest = rawRequestString;
        socket = HTTPClientSocket;
        dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
        logger = new MessageLogger();
        requestRouter = new RequestRouter();
        requestParser = new RequestParser();
        request = requestParser.makeRequest(rawRequest);
        response = new Response();
    }

    public void run() {
        logger.log(request);
        logger.log("Request headers:\n\n" + rawRequest);
        serveResponse();
    }

    private String getCurrentDate() {
        Date now = new Date();
        return dateFormat.format(now);
    }

    private WebResource getRequestedFile() {
        return requestRouter.getWebResource(request.path());
    }

    private void addDefaultHeaders(Response response) {
        response.setHeader("Server", "Schtitt/0.9a");
        response.setHeader("Date", getCurrentDate());
        response.setHeader("Pragma", "no-cache");
    }

    private void addFileHeaders(Response response) {
        WebResource file = getRequestedFile();
        response.setHeader("Content-Type", file.mimeType());
        response.setHeader("Content-Length", file.contentLength());
        response.setHeader("Content-MD5", file.checkSum());
    }

    private void addFileData(Response response) {
        WebResource file = getRequestedFile();
        response.body(file.binaryData());
    }

    public Response constructResponse() {
        if (request.method().equals("GET")) {
            if (requestRouter.resourceExists(request.path())) {
                return constructOKResponse();
            } else {
                return constructNotFoundResponse();
            }
        } else if (request.method().equals("POST")) {
            return constructPostResponse();
        } else if (request.method().equals("HEAD")) {
            return constructHeadResponse();
        } else {
            return constructBadRequestResponse();
        }
    }

    public Response constructOKResponse() {
        response.httpVersion("1.0");
        response.statusCode("200");
        addDefaultHeaders(response);
        addFileHeaders(response);
        addFileData(response);
        return response;
    }

    public Response constructMethodNotAllowedResponse() {
        response.httpVersion("1.0");
        response.statusCode("405");
        addDefaultHeaders(response);
        return response;
    }

    public Response constructNotFoundResponse() {
        response.httpVersion("1.0");
        response.statusCode("404");
        addDefaultHeaders(response);
        addFileData(response);
        return response;
    }

    public Response constructHeadResponse() {
        response.httpVersion("1.0");
        response.statusCode("200");
        response.body(new byte[0]);
        addDefaultHeaders(response);
        addFileHeaders(response);
        return response;
    }

    public Response constructBadRequestResponse() {
        response.httpVersion("1.0");
        response.statusCode("400");
        addDefaultHeaders(response);
        return response;
    }

    public Response constructPostResponse() {
        response.httpVersion("1.0");
        return response;
    }

    private void serveResponse() {
        constructResponse();
        try {
            socket.write(response.toBytes());
            logger.log(response);
            logger.log("Response headers:\n\n" +
                       response.statusLineString() + "\n" +
                       response.headersString());
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
