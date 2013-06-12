package com.cmendenhall;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {
        Integer port = null;
        WebServerSocket socket;
        while (true) {
        if (port == null) {
            socket = new WebServerSocket(64000);
        } else {
            socket = new WebServerSocket(port);
        }
        port = socket.getPort();
        socket.listen();
        //System.out.println("Client connected");
        socket.getIOStreams();

        StringBuilder rawRequest = new StringBuilder();
        String currentLine;
        while((currentLine = socket.readLine()) != null) {
            rawRequest.append(currentLine);
            rawRequest.append("\n");
            if (currentLine.length() == 0) break;
        }
        //System.out.println(rawRequest.toString());

        RequestHandler handler = new RequestHandler(rawRequest.toString(), socket);
        new Thread(handler).run();
        socket.close();
        }
    }

}
