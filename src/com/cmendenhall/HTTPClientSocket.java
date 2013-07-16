package com.cmendenhall;

import java.io.*;
import java.net.Socket;

public class HTTPClientSocket {
    private Socket socket;
    private OutputStream output;
    private InputStream input;
    private BufferedReader inputReader;

    public HTTPClientSocket(Socket clientSocket) throws IOException {
        socket = clientSocket;
        output = socket.getOutputStream();
        input = socket.getInputStream();
        inputReader = new BufferedReader(new InputStreamReader(input));
    }

    public void write(byte[] bytes) throws IOException {
        output.write(bytes);
    }

    public void close() throws IOException {
       socket.close();
    }

    public boolean isClosed() {
        return socket.isClosed();
    }

    public void flush() throws IOException {
        output.flush();
    }

    public int read() throws IOException {
        return inputReader.read();
    }

    public boolean waiting() throws IOException {
        return !inputReader.ready();
    }
}
