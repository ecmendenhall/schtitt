package com.cmendenhall;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class HTTPClientSocket {
    private Socket socket;
    private OutputStream output;
    private PrintWriter stringWriter;

    public HTTPClientSocket(Socket clientSocket) throws IOException {
        socket = clientSocket;
        output = socket.getOutputStream();
        stringWriter = new PrintWriter(output, true);
    }

    public void write(byte[] bytes) throws IOException {
        output.write(bytes);
    }

    public void close() throws IOException {
       socket.close();
    }
}
