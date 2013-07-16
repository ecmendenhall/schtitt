package com.cmendenhall.tests;

import com.cmendenhall.HTTPClientSocket;
import com.cmendenhall.HTTPServerSocket;
import com.cmendenhall.OutputRecorder;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

import static junit.framework.Assert.assertEquals;

public class HTTPServerSocketTest {
    HTTPServerSocket webServerSocket;
    OutputRecorder recorder;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        recorder = new OutputRecorder();
        recorder.start();
    }

    @Test
    public void webServerSocketShouldChooseRandomPortIfNotSet() {
        webServerSocket = new HTTPServerSocket();
        Integer port = webServerSocket.getPort();
        assertEquals(Integer.class, port.getClass());
    }

    @Test
    public void webServerSocketPortShouldBeSettableOnConstruction() {
        webServerSocket = new HTTPServerSocket(60000);
        assertEquals(60000, (int)webServerSocket.getPort());
    }

    @Test
    public void webServerSocketShouldPrintErrorMessageIfConstructedWithInvalidPort() {
        webServerSocket = new HTTPServerSocket(80);
        String output = recorder.popLastOutput();
        String expected = "Can't get I/O for port 80";
        assertEquals(expected, output);
    }

    @Test
    public void webServerSocketListensForConnections() throws IOException {
        webServerSocket = new HTTPServerSocket();

        Thread listenThread = new Thread(new Runnable() {
                public void run() {
                    try {
                        HTTPClientSocket socket = webServerSocket.listen();
                        socket.write("Connection successful.".getBytes("UTF-8"));
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        listenThread.start();

        Socket socket = new Socket();
        InetSocketAddress webServerAddress = new InetSocketAddress("localhost", webServerSocket.getPort());
        socket.connect(webServerAddress);

        InputStream clientInput = socket.getInputStream();
        BufferedReader clientReader = new BufferedReader(new InputStreamReader(clientInput));
        String connectMessage = clientReader.readLine();
        socket.close();

        assertEquals("Connection successful.", connectMessage);
    }
}
