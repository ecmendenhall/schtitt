package com.cmendenhall.tests;

import com.cmendenhall.HTTPServerSocket;
import com.cmendenhall.OutputRecorder;
import com.cmendenhall.RequestListener;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class RequestListenerTest {
    private HTTPServerSocket serverSocket;
    private RequestListener requestListener;
    private OutputRecorder recorder;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        recorder = new OutputRecorder();
        recorder.start();
        serverSocket = new HTTPServerSocket();
        requestListener = new RequestListener(serverSocket);
    }

    @Test
    public void requestListenerListensForConnections() throws IOException {

        Thread listenThread = new Thread(new Runnable() {
            public void run() {
                requestListener.listen();
            }
        });

        listenThread.start();
        Integer port = serverSocket.getPort();

        Socket clientSocket = new Socket();
        InetSocketAddress webServerAddress = new InetSocketAddress("localhost", port);
        clientSocket.connect(webServerAddress);

        OutputStream clientOutput = clientSocket.getOutputStream();
        PrintWriter clientWriter = new PrintWriter(clientOutput, true);
        clientWriter.println("GET / HTTP/1.0\r\n\r\n");

    }
}
