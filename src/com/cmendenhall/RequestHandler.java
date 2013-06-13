package com.cmendenhall;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestHandler implements Runnable {
    private RequestParser requestParser;
    private FileManager fileManager;
    private MessageLogger logger;
    private Request request;
    private Response response;
    private DateFormat dateFormat;
    private WebServerSocket socket;

    public RequestHandler(String rawRequest, WebServerSocket webServerSocket) {
        socket = webServerSocket;
        dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
        logger = new MessageLogger();
        fileManager = new FileManager();
        requestParser = new RequestParser();
        request = requestParser.makeRequest(rawRequest);
        response = new Response();
    }

    public void run() {
        logger.log(request);
        serveResponse();
    }

    public Request getRequest() {
        return request;
    }

    private String getCurrentDate() {
        Date now = new Date();
        return dateFormat.format(now);
    }

    private WebResource getRequestedFile() {
        return fileManager.getWebResource(request.path());
    }

    private void addDefaultHeaders(Response response) {
        response.setHeader("Server", "Schtitt/0.9a");
        response.setHeader("Date", getCurrentDate());
        response.setHeader("Pragma", "no-cache");
    }

    private void addFileData(Response response) {
        WebResource file = getRequestedFile();
        response.setHeader("Content-Type", file.mimeType());
        response.setHeader("Content-Length", file.contentLength());
        response.setHeader("Content-MD5", file.checkSum());
        response.body(file.binaryData());
    }

    public Response constructResponse() {
        if (fileManager.resourceExists(request.path())) {
            return constructOKResponse();
        } else {
            return constructNotFoundResponse();
        }
    }

    public Response constructOKResponse() {
        response.httpVersion("1.0");
        response.statusCode("200");
        addDefaultHeaders(response);
        addFileData(response);
        return response;
    }

    public Response constructNotFoundResponse() {
        response.httpVersion("1.0");
        response.statusCode("404");
        addDefaultHeaders(response);
        addFileData(response);
        return response;
    }

    private void serveResponse() {
        constructResponse();
        try {
            socket.write(response.toBytes());
            logger.log(response);
            socket.close();
            socket.listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
