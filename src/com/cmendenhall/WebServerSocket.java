package com.cmendenhall;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServerSocket {
    private ServerSocket serverSocket;
    private Socket socket;
    private InputStream input;
    private BufferedReader inputReader;
    private PrintWriter stringWriter;
    private OutputStream output;
    private Integer port;
    private MessageLogger logger;

    public WebServerSocket() {
        try {
            logger = new MessageLogger();
            serverSocket = new ServerSocket(0);
            port = serverSocket.getLocalPort();
            logger.log("Socket listening on port " + port);
        } catch (IOException e) {
            System.err.println("Can't get I/O for port " + port);
        }
    }

    public WebServerSocket(Integer listenPort) {
        try {
            logger = new MessageLogger();
            port = listenPort;
            serverSocket = new ServerSocket(port);
            logger.log("Socket listening on port " + port);
        } catch (IOException e) {
            System.err.println("Can't get I/O for port " + port);
        }
    }

    public BufferedReader getInputReader() {
        return inputReader;
    }

    public void setInputReader(BufferedReader inputReader) {
        this.inputReader = inputReader;
    }

    public void listen() throws IOException {
        socket = serverSocket.accept();
        getIOStreams();
    }

    public void getIOStreams() throws IOException {
        input = socket.getInputStream();
        inputReader = new BufferedReader(new InputStreamReader(input));
        output = socket.getOutputStream();
        stringWriter = new PrintWriter(output, true);
    }

    public String readLine() throws IOException {
        return inputReader.readLine();
    }

    public boolean waiting() throws IOException {
        return !inputReader.ready();
    }

    public void write(byte[] bytes) throws IOException {
        output.write(bytes);
    }

    public void write(String string) throws IOException {
        stringWriter.println(string);
    }

    public Integer getPort() {
        return port;
    }

    public void close() throws IOException {
        socket.close();
    }

    public String getHostname() {
        return socket.getInetAddress().getHostName();
    }

}
