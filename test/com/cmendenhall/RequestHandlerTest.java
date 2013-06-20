package com.cmendenhall;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.cmendenhall.Utils.join;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

public class RequestHandlerTest {
    private MockClientSocket socket;
    private OutputRecorder recorder;
    private RequestHandler requestHandler;
    private String rawRequest;

    private String makeRawRequest() {
        return join("\r\n", "GET /test/sampledirectory/index.html HTTP/1.0",
                            "Accept: text/html",
                            "\r\n");
    }

    @Before
    public void setUp() throws IOException {
        recorder = new OutputRecorder();
        recorder.start();
        socket = new MockClientSocket(new MockSocket());
        rawRequest = makeRawRequest();
        requestHandler = new RequestHandler(rawRequest, socket);
    }

    @Test
    public void handlerCanWriteResponseToSocket() throws UnsupportedEncodingException {
        requestHandler.run();
        Response response = requestHandler.getResponse();
        assertArrayEquals(response.toBytes(), socket.writtenBytes);
    }

    @Test
    public void handlerShouldLogRequestHeaders() {
        requestHandler.run();
        recorder.discardFirstNStrings(1);
        String logged = recorder.popFirstOutput();
        assertTrue(logged.contains(rawRequest));
    }

    @Test
    public void handlerShouldLogResponseHeaders() {
        requestHandler.run();
        Response response = requestHandler.getResponse();
        String logged = recorder.popLastOutput();
        assertTrue(logged.contains(response.headersString()));
    }

}
