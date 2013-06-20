package com.cmendenhall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPServerSocket {
    private ServerSocket serverSocket;
    private InputStream input;
    private BufferedReader inputReader;
    private Integer port;
    private MessageLogger logger;

    public HTTPServerSocket() {
        try {
            logger = new MessageLogger();
            serverSocket = new ServerSocket(0);
            port = serverSocket.getLocalPort();
            logger.log("Socket listening on port " + port);
        } catch (IOException e) {
            System.err.println("Can't get I/O for port " + port);
        }
    }

    public HTTPServerSocket(Integer listenPort) {
        try {
            logger = new MessageLogger();
            port = listenPort;
            serverSocket = new ServerSocket(port);
            logger.log("Socket listening on port " + port);
        } catch (IOException e) {
            System.err.println("Can't get I/O for port " + port);
        }
    }

    public void setInputReader(BufferedReader inputReader) {
        this.inputReader = inputReader;
    }

    public HTTPClientSocket listen() throws IOException {
        Socket socket = serverSocket.accept();
        getInputStreams(socket);
        return new HTTPClientSocket(socket);
    }

    public void getInputStreams(Socket socket) throws IOException {
        input = socket.getInputStream();
        inputReader = new BufferedReader(new InputStreamReader(input));
    }

    public String readLine() throws IOException {
        return inputReader.readLine();
    }

    public boolean waiting() throws IOException {
        return !inputReader.ready();
    }

    public Integer getPort() {
        return port;
    }
}
