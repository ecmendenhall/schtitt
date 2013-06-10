package com.cmendenhall;

import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static junit.framework.Assert.assertTrue;

public class RequestLoggerTest {
    RequestLogger requestLogger;
    RequestParser requestParser;
    Request request;
    OutputRecorder recorder;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        requestLogger = new RequestLogger();
        requestParser = new RequestParser();
        request = requestParser.parse("GET / HTTP/1.0\r\n\r\n");
        recorder = new OutputRecorder();
    }

    @Test
    public void loggerShouldPrintCorrectLogStrings() {
        recorder.start();
        requestLogger.log(request);
        String expected = "\\[\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\] GET /";
        String output = recorder.popLastOutput();
        assertTrue(output.matches(expected));
    }
}
