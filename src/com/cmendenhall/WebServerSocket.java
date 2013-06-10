package com.cmendenhall;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServerSocket {
    private ServerSocket serverSocket;
    private Socket socket;
    private InputStream input;
    private OutputStream output;
    private Integer port;

    public WebServerSocket() {
        try {
            serverSocket = new ServerSocket(0);
            port = serverSocket.getLocalPort();
        } catch (IOException e) {
            System.err.println("Can't get I/O for port " + port);
        }
    }

    public WebServerSocket(Integer listenPort) {
        try {
            port = listenPort;
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Can't get I/O for port " + port);
        }
    }

    public void listen() throws IOException {
        socket = serverSocket.accept();
    }

    public void getIOStreams() throws IOException {
        input = socket.getInputStream();
        output = socket.getOutputStream();
    }

    public Integer getPort() {
        return port;
    }
}
