package com.cmendenhall;

import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static junit.framework.Assert.assertTrue;

public class MessageLoggerTest {
    MessageLogger MessageLogger;
    RequestParser requestParser;
    Request request;
    Response response;
    OutputRecorder recorder;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        MessageLogger = new MessageLogger();
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
        MessageLogger.log(request);
        String expected = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}  \\[REQUEST\\]   GET /";
        String output = recorder.popLastOutput();
        assertTrue(output.matches(expected));
    }

    @Test
    public void loggerShouldPrintCorrectLogStringsForResponses() {
        recorder.start();
        MessageLogger.log(response);
        String expected = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}  \\[RESPONSE\\]  200 OK";
        String output = recorder.popLastOutput();
        assertTrue(output.matches(expected));
    }

    @Test
    public void loggerShouldPrintCorrectLogStringsForInfoMessage() {
        recorder.start();
        MessageLogger.log("Keyboard not found. Press F1 to continue.");
        String expected = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}  \\[INFO\\]      Keyboard not found. Press F1 to continue.";
        String output = recorder.popLastOutput();
        assertTrue(output.matches(expected));
    }
}
