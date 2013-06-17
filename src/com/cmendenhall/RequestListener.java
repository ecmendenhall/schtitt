package com.cmendenhall;

import java.io.IOException;

public class RequestListener {
    private WebServerSocket socket;

    public RequestListener(WebServerSocket serverSocket) {
        socket = serverSocket;
    }

    public void listen() {
        try {
            socket.listen();
            while (true) {
                String rawRequest = readRawRequest();
                if (!rawRequest.isEmpty()) {
                    dispatchRequest(rawRequest);
                    socket.listen();
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
        System.out.println(rawRequest.toString());
        return rawRequest.toString();
    }

    private void dispatchRequest(String rawRequest) {
        RequestHandler handler = new RequestHandler(rawRequest, socket);
        new Thread(handler).run();
    }

}
