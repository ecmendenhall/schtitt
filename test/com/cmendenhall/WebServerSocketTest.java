package com.cmendenhall;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class WebServerSocketTest {
    WebServerSocket webServerSocket;
    OutputRecorder recorder;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        recorder = new OutputRecorder();
        recorder.start();
    }

    @Test
    public void webServerSocketShouldChooseRandomPortIfNotSet() {
        webServerSocket = new WebServerSocket();
        Integer port = webServerSocket.getPort();
        assertEquals(Integer.class, port.getClass());
    }

    @Test
    public void webServerSocketPortShouldBeSettableOnConstruction() {
        webServerSocket = new WebServerSocket(60000);
        assertEquals(60000, (int)webServerSocket.getPort());
    }

    @Test
    public void webServerSocketShouldPrintErrorMessageIfConstructedWithInvalidPort() {
        webServerSocket = new WebServerSocket(80);
        String output = recorder.popLastOutput();
        String expected = "Can't get I/O for port 80";
        assertEquals(expected, output);
    }

    @Test
    public void webServerSocketListensForConnections() throws IOException {
        webServerSocket = new WebServerSocket();

        Thread listenThread = new Thread(new Runnable() {
                public void run() {
                    try {
                        webServerSocket.listen();
                        webServerSocket.getIOStreams();
                        webServerSocket.write("Connection successful.");
                        webServerSocket.close();
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

    @Test
    public void webServerSocketReadsLinesFromClientInput() throws IOException {

        webServerSocket = new WebServerSocket();

        Thread listenThread = new Thread(new Runnable() {
            public void run() {
                try {
                    webServerSocket.listen();
                    webServerSocket.getIOStreams();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        listenThread.start();

        Socket socket = new Socket();
        InetSocketAddress webServerAddress = new InetSocketAddress("localhost", webServerSocket.getPort());
        socket.connect(webServerAddress);

        OutputStream clientOutput = socket.getOutputStream();
        PrintWriter clientWriter = new PrintWriter(clientOutput, true);
        clientWriter.println("Hello, server!");

        String serverMessage = webServerSocket.readLine();
        while (serverMessage == null) {
            serverMessage = webServerSocket.readLine();
        }

        assertEquals("Hello, server!", serverMessage);
    }


    @Test
    public void webServerSocketWritesBytesToClientOutput() throws IOException {

        webServerSocket = new WebServerSocket();

        Thread listenThread = new Thread(new Runnable() {
            public void run() {
                try {
                    webServerSocket.listen();
                    webServerSocket.getIOStreams();
                    webServerSocket.write("Hello, client!".getBytes("UTF-8"));
                    webServerSocket.close();
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
        String clientMessage = clientReader.readLine();
        socket.close();

        assertEquals("Hello, client!", clientMessage);
    }

    @Test
    public void webServerSocketReturnsHostname() throws IOException {
        webServerSocket = new WebServerSocket();

        Thread listenThread = new Thread(new Runnable() {
            public void run() {
                try {
                    webServerSocket.listen();
                    webServerSocket.getIOStreams();
                    String hostname = webServerSocket.getHostname();
                    webServerSocket.write(hostname);
                    webServerSocket.close();
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
        String hostname = clientReader.readLine();
        socket.close();

        assertEquals("localhost", hostname);
    }

}
