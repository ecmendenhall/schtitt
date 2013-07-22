package com.cmendenhall.tests;


import com.cmendenhall.logging.MessagePrinter;
import com.cmendenhall.logging.MessageQueue;
import com.cmendenhall.OutputRecorder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.NoSuchElementException;

import static junit.framework.Assert.assertEquals;

public class MessagePrinterTest {
    private OutputRecorder recorder;
    private MessageQueue messageQueue;
    private MessagePrinter printer = new MessagePrinter();

    @Before
    public void setUp() throws UnsupportedEncodingException {
        messageQueue = MessageQueue.getInstance();
        messageQueue.clear();
        recorder = new OutputRecorder();
        recorder.start();
    }

    @Test
    public void MessagePrinterPrintsMessagesWhenAvailable() {
        messageQueue.add("My hovercraft is full of eels!");
        MessagePrinter printer = new MessagePrinter();
        printer.printMessage();

        String printed = recorder.popLastOutput();
        assertEquals("My hovercraft is full of eels!", printed);
        printer.stop();
    }

    @Test(expected = NoSuchElementException.class)
    public void MessagePrinterDoesNotPrintMessagesWhenQueueIsEmpty() {
        recorder.start();
        MessagePrinter printer = new MessagePrinter();
        printer.printMessage();

        String printed = recorder.popLastOutput();
        printer.stop();
    }

    @After
    public void cleanUp() {
        messageQueue.clear();
    }

}
