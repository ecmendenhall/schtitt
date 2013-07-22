package com.cmendenhall.tests;

import com.cmendenhall.logging.MessageQueue;
import org.junit.*;

import static org.junit.Assert.assertEquals;

public class MessageQueueTest {
    private MessageQueue messageQueue;

    @Before
    public void setUp() {
        messageQueue = MessageQueue.getInstance();
        messageQueue.clear();
    }

    @Test
    public void MessageQueueStoresAndReturnsMessages() {
        messageQueue.add("My hovercraft is full of eels.");
        String message = messageQueue.nextMessage();
        assertEquals("My hovercraft is full of eels.", message);
    }

    @Test
    public void MessageQueueReturnsEmptyStringIfNoMessagesAreInQueue() {
        String message = messageQueue.nextMessage();

        assertEquals("", message);
    }

    @After
    public void cleanUp() {
        messageQueue.clear();
    }
}
