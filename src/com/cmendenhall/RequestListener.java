package com.cmendenhall;

import java.io.IOException;

public class RequestListener {
    private HTTPServerSocket socket;
    private ThreadManager threadManager;

    public RequestListener(HTTPServerSocket serverSocket) {
        socket = serverSocket;
        threadManager = new ThreadManager();
    }

    public void listen() {
        try {
            HTTPClientSocket httpClientSocket = socket.listen();
            while (true) {
                String rawRequest = readRawRequest();
                if (!rawRequest.isEmpty()) {
                    dispatchRequest(rawRequest, httpClientSocket);
                    httpClientSocket = socket.listen();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readRawRequest() throws IOException {
        StringBuilder rawRequest = new StringBuilder();
        String currentLine;

        while ((currentLine = socket.readLine()) != null) {
            rawRequest.append(currentLine + "\n");
            if (socket.waiting()) {
                break;
            }
        }
        return rawRequest.toString();
    }

    private void dispatchRequest(String rawRequest, HTTPClientSocket httpClientSocket) {
        RequestHandler handler = new RequestHandler(rawRequest, httpClientSocket);
        threadManager.execute(handler);
    }

}
