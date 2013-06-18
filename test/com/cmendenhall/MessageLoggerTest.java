package com.cmendenhall;

import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class MessageLoggerTest {
    MessageLogger messageLogger;
    RequestParser requestParser;
    Request request;
    Response response;
    OutputRecorder recorder;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        messageLogger = new MessageLogger();
        requestParser = new RequestParser();
        request = requestParser.makeRequest("GET / HTTP/1.0\r\n\r\n");
        response = new Response();
        response.httpVersion("1.0");
        response.statusCode("200");
        recorder = new OutputRecorder();
    }

    @Test
    public void loggerShouldPrintCorrectLogStringsForRequests() {
        recorder.start();
        messageLogger.log(request);
        String expected = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}  \\[REQUEST\\]   GET /";
        String output = recorder.popLastOutput();
        assertTrue(output.matches(expected));
    }

    @Test
    public void loggerShouldPrintCorrectLogStringsForResponses() {
        recorder.start();
        messageLogger.log(response);
        String expected = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}  \\[RESPONSE\\]  200 OK";
        String output = recorder.popLastOutput();
        assertTrue(output.matches(expected));
    }

    @Test
    public void loggerShouldPrintCorrectLogStringsForInfoMessage() {
        recorder.start();
        messageLogger.log("Keyboard not found. Press F1 to continue.");
        String expected = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}  \\[INFO\\]      Keyboard not found. Press F1 to continue.";
        String output = recorder.popLastOutput();
        assertTrue(output.matches(expected));
    }

    @Test
    public void loggerShouldPrintStartupMessage() {
        recorder.start();
        messageLogger.printStartupMessage();
        String serverStart = recorder.popFirstOutput();
        String infoMessage = recorder.popFirstOutput();
        assertEquals("Schtitt 0.9a", serverStart);
        assertTrue(infoMessage.contains("Press c-C to exit."));
    }
}
