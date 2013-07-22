package com.cmendenhall.tests;

import com.cmendenhall.*;
import com.cmendenhall.logging.MessageLogger;
import com.cmendenhall.logging.MessageQueue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class MessageLoggerTest {
    MessageQueue messageQueue;
    MessageLogger messageLogger;
    RequestParser requestParser;
    Request request;
    Response response;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        messageQueue = MessageQueue.getInstance();
        messageQueue.clear();
        messageLogger = new MessageLogger();
        requestParser = new RequestParser();
        request = requestParser.makeRequest("GET / HTTP/1.0\r\n\r\n");
        response = new Response();
        response.httpVersion("1.0");
        response.statusCode("200");
    }

    @Test
    public void loggerShouldPrintCorrectLogStringsForRequests() {
        messageLogger.log(request);
        String expected = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}  \\[REQUEST\\]   GET /";
        String output = messageQueue.nextMessage();
        assertTrue(output.matches(expected));
    }

    @Test
    public void loggerShouldPrintCorrectLogStringsForResponses() {
        messageLogger.log(response);
        String expected = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}  \\[RESPONSE\\]  200 OK";
        String output = messageQueue.nextMessage();
        assertTrue(output.matches(expected));
    }

    @Test
    public void loggerShouldPrintCorrectLogStringsForInfoMessage() {
        messageLogger.log("Keyboard not found. Press F1 to continue.");
        String expected = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}  \\[INFO\\]      Keyboard not found. Press F1 to continue.";
        String output = messageQueue.nextMessage();
        assertTrue(output.matches(expected));
    }

    @Test
    public void loggerShouldPrintStartupMessage() {
        messageLogger.printStartupMessage();
        String serverStart = messageQueue.nextMessage();
        String infoMessage = messageQueue.nextMessage();
        assertTrue(serverStart.contains("Schtitt 0.9a"));
        assertTrue(infoMessage.contains("Press c-C to exit."));
    }

    @After
    public void cleanUp() {
        messageQueue.clear();
    }

}
