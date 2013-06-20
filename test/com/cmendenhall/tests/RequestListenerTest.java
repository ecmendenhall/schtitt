package com.cmendenhall.tests;

import com.cmendenhall.HTTPServerSocket;
import com.cmendenhall.OutputRecorder;
import com.cmendenhall.RequestListener;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

import static junit.framework.Assert.assertEquals;

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

    @Test
    public void readsOneLineRequest() throws Exception {
        String testString = "GET / HTTP/1.0\n\n";
        InputStream testStream = new ByteArrayInputStream(testString.getBytes("UTF-8"));

        serverSocket = new HTTPServerSocket();
        requestListener = new RequestListener(serverSocket);
        BufferedReader reader = new BufferedReader(new InputStreamReader(testStream));
        serverSocket.setInputReader(reader);
        assertEquals(testString, requestListener.readRawRequest());
    }


    @Test
    public void readsTwoLineRequestWithHeader() throws Exception {
        String testString = "GET / HTTP/1.0\nHost: localhost:5000\n\n";
        InputStream testStream = new ByteArrayInputStream(testString.getBytes("UTF-8"));

        serverSocket = new HTTPServerSocket();
        requestListener = new RequestListener(serverSocket);
        BufferedReader reader = new BufferedReader(new InputStreamReader(testStream));
        serverSocket.setInputReader(reader);
        assertEquals(testString, requestListener.readRawRequest());
    }


    @Test
    public void readsMultiLineRequest() throws Exception {
        String testString = "POST / HTTP/1.0\nHost: localhost:5000\n\n?q=query\n\n";
        InputStream testStream = new ByteArrayInputStream(testString.getBytes("UTF-8"));

        serverSocket = new HTTPServerSocket();
        requestListener = new RequestListener(serverSocket);
        BufferedReader reader = new BufferedReader(new InputStreamReader(testStream));
        serverSocket.setInputReader(reader);
        assertEquals(testString, requestListener.readRawRequest());
    }

}
