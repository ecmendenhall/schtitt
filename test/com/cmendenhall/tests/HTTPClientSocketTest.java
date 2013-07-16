package com.cmendenhall.tests;

import org.junit.Test;

public class HTTPClientSocketTest {

    @Test
    public void readsOneLineRequest() throws Exception {         /*
        String testString = "GET / HTTP/1.0\n\n";
        InputStream testStream = new ByteArrayInputStream(testString.getBytes("UTF-8"));

        serverSocket = new HTTPServerSocket();
        requestListener = new RequestListener(serverSocket);
        BufferedReader reader = new BufferedReader(new InputStreamReader(testStream));
        serverSocket.setInputReader(reader);
        assertEquals(testString, requestListener.readRawRequest());    */
    }


    @Test
    public void readsTwoLineRequestWithHeader() throws Exception {     /*
        String testString = "GET / HTTP/1.0\nHost: localhost:5000\n\n";
        InputStream testStream = new ByteArrayInputStream(testString.getBytes("UTF-8"));

        serverSocket = new HTTPServerSocket();
        requestListener = new RequestListener(serverSocket);
        BufferedReader reader = new BufferedReader(new InputStreamReader(testStream));
        serverSocket.setInputReader(reader);
        assertEquals(testString, requestListener.readRawRequest());        */
    }


    @Test
    public void readsMultiLineRequest() throws Exception { /*
        String testString = "POST / HTTP/1.0\nHost: localhost:5000\n\n?q=query\n\n";
        InputStream testStream = new ByteArrayInputStream(testString.getBytes("UTF-8"));

        serverSocket = new HTTPServerSocket();
        requestListener = new RequestListener(serverSocket);
        BufferedReader reader = new BufferedReader(new InputStreamReader(testStream));
        serverSocket.setInputReader(reader);
        assertEquals(testString, requestListener.readRawRequest());     */
    }
}
