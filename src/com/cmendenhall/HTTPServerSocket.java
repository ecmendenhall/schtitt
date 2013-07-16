package com.cmendenhall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPServerSocket {
    private ServerSocket serverSocket;
    private InputStream input;
    private BufferedReader inputReader;
    private Integer port;
    private MessageLogger logger = new MessageLogger();

    public HTTPServerSocket() {
        try {
            serverSocket = new ServerSocket(0, 128);
            port = serverSocket.getLocalPort();
            logger.log("Socket listening on port " + port);
        } catch (IOException e) {
            System.err.println("Can't get I/O for port " + port);
        }
    }

    public HTTPServerSocket(Integer listenPort) {
        try {
            port = listenPort;
            serverSocket = new ServerSocket(port, 128);
            logger.log("Socket listening on port " + port);
        } catch (IOException e) {
            System.err.println("Can't get I/O for port " + port);
        }
    }

    public HTTPClientSocket listen() throws IOException {
        Socket socket = serverSocket.accept();
        return new HTTPClientSocket(socket);
    }

    public Integer getPort() {
        return port;
    }
}
