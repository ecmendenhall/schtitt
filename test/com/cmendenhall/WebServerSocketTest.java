package com.cmendenhall;

import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class WebServerSocketTest {
    WebServerSocket webServerSocket;
    OutputRecorder recorder;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        recorder = new OutputRecorder();
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
        recorder.start();
        webServerSocket = new WebServerSocket(80);
        String output = recorder.popLastOutput();
        String expected = "Can't get I/O for port 80";
        assertEquals(expected, output);
    }

}
