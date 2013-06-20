package com.cmendenhall;

import java.io.IOException;

public class RequestHandler implements Runnable {
    private RequestParser requestParser;
    private ResponseBuilder responseBuilder;
    private MessageLogger logger;
    private String rawRequest;
    private Request request;
    private HTTPClientSocket socket;

    public RequestHandler(String rawRequestString, HTTPClientSocket httpClientSocket) {
        rawRequest = rawRequestString;
        socket = httpClientSocket;
        logger = new MessageLogger();
        requestParser = new RequestParser();
        responseBuilder = new ResponseBuilder();
        request = requestParser.makeRequest(rawRequest);
    }

    public void run() {
        logger.log(request);
        logger.log("Request headers:\n\n" + rawRequest);
        serveResponse();
    }

    public Response getResponse() {
        return responseBuilder.constructResponse(request.method(),
                                                 request.path(),
                                                 request.parameters());
    }

    private void serveResponse() {
        Response response = getResponse();
        try {
            socket.write(response.toBytes());
            logger.log(response);
            logger.log("Response headers:\n\n" +
                       response.statusLineString() + "\n" +
                       response.headersString() + "\n" );
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
