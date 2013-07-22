package com.cmendenhall;

public class MessagePrinter implements Runnable {
    private MessageQueue messageQueue = MessageQueue.getInstance();
    private boolean log = false;

    public void printMessage() {
        String message = messageQueue.nextMessage();
        if (message != "") System.out.println(message);
    }

    public void stop() {
        log = false;
    }

    public void run() {
        log = true;
        while (log == true) {
            printMessage();
        }

    }
}
