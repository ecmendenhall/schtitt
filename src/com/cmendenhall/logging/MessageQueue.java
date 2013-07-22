package com.cmendenhall.logging;

import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue {

    private static MessageQueue instance;
    private LinkedBlockingQueue<String> messages = new LinkedBlockingQueue<String>();

    private MessageQueue() {

    }

    public static MessageQueue getInstance() {
        if (instance == null) {
            instance = new MessageQueue();
        }
        return instance;
    }

    public void add(String message) {
        messages.add(message);
    }

    public String nextMessage() {
        try {
            return messages.remove();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    public void clear() {
        messages.clear();
    }
}
