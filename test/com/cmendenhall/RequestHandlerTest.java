package com.cmendenhall;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.cmendenhall.Utils.join;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

public class RequestHandlerTest {
    private MockWebServerSocket socket = new MockWebServerSocket();
    private RequestHandler requestHandler;
    private String rawRequest;
    private String body = "";
    private Response response;
    private DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy");

    private String makeRawRequest() {
        return join("\r\n", "GET /test/sampledirectory/index.html HTTP/1.0",
                            "Accept: text/html",
                            "\r\n");
    }

    @Before
    public void setUp() throws IOException {
        rawRequest = makeRawRequest();
        requestHandler = new RequestHandler(rawRequest, socket);
        response = requestHandler.constructResponse();
    }

    @Test
    public void requestHandlerShouldParseRawRequests() {
        Request request = requestHandler.getRequest();
        assertEquals("GET", request.method());
        assertEquals("/test/sampledirectory/index.html", request.path());
        assertEquals("text/html", request.getHeader("Accept").get(0));
        assertEquals(body, request.body());
    }

    @Test
    public void responseShouldHaveHTTPVersion() {
        assertEquals("HTTP/1.0", response.getHttpVersion());
        assertEquals("200","" + response.getStatusCode());
    }

    @Test
    public void responseShouldHaveDefaultHeaders() {
        Date now = new Date();
        String today = dateFormat.format(now);

        assertEquals("Schtitt/0.9a", response.getHeader("Server"));
        assertEquals("no-cache", response.getHeader("Pragma"));
        assertTrue(response.getHeader("Date").contains(today));
    }

    @Test
    public void responseShouldHaveFileData() throws UnsupportedEncodingException {
        File requestedFile = new File("test/sampledirectory/index.html");

        FileInputStream byteStream;
        byte[] expected = new byte[(int) requestedFile.length()];
        try {
            byteStream = new FileInputStream(requestedFile);
            int currentByte;
            int byteIndex = 0;
            while((currentByte = byteStream.read()) != -1) {
                expected[byteIndex] = (byte) currentByte;
                byteIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("text/html; charset=UTF-8", response.getHeader("Content-Type"));
        assertEquals("194", response.getHeader("Content-Length"));
        assertArrayEquals(expected, response.bodyBytes());
    }

    @Test
    public void handlerCanWriteResponseToSocket() throws UnsupportedEncodingException {
        requestHandler.run();
        assertArrayEquals(response.toBytes(), socket.writtenBytes);
    }

}
