package com.cmendenhall;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RequestHandler implements Runnable {
    private RequestParser requestParser;
    private ResponseBuilder responseBuilder;
    private String rawRequest;
    private Request request;
    private HTTPClientSocket socket;
    private MessageLogger logger;

    public RequestHandler(HTTPClientSocket httpClientSocket) {
        socket = httpClientSocket;
        requestParser = new RequestParser();
        responseBuilder = new ResponseBuilder();
        logger = new MessageLogger();
    }

    public void run() {
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        try {
            rawRequest = readRawRequest();
            if (rawRequest.isEmpty()) {
                socket.close();
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        request = requestParser.makeRequest(rawRequest);
        logger.log(request);
        logger.log("Request headers:\n\n" + rawRequest);
        serveResponse();
    }

    public String readRawRequest() throws IOException {
        ByteArrayOutputStream rawRequest = new ByteArrayOutputStream();
        int current;

        try {
            while ((current = socket.read()) != -1) {
                rawRequest.write(current);
                if (socket.waiting() || socket.isClosed()) break;
            }
        } catch (java.net.SocketException e) {
            logger.log(e.getMessage());
        }
        return new String(rawRequest.toByteArray());
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
            socket.flush();
            socket.close();
            logger.log(response);
            logger.log("Response headers:\n\n" +
                              response.statusLineString() + "\n" +
                              response.headersString() + "\n" );
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (!socket.isClosed()) socket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
