package com.cmendenhall;

import com.cmendenhall.handlers.RequestHandler;

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
               if (httpClientSocket != null) dispatchRequest(httpClientSocket);
               httpClientSocket = socket.listen();
            }
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

    private void dispatchRequest(HTTPClientSocket httpClientSocket) {
        RequestHandler handler = new RequestHandler(httpClientSocket);
        threadManager.execute(handler);
    }

}
