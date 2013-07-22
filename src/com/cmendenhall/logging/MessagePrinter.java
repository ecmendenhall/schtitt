package com.cmendenhall.logging;

import java.io.*;

public class MessagePrinter implements Runnable {
    private PrintWriter writer;
    private MessageQueue messageQueue = MessageQueue.getInstance();
    private boolean log = false;

    public MessagePrinter() {
        try {
            FileOutputStream fileOutput = new FileOutputStream("logs.txt");
            writer = new PrintWriter(new OutputStreamWriter(fileOutput, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printMessage() {
        String message = messageQueue.nextMessage();
        if (message != "") {
            writer.println(message);
            writer.flush();
            System.out.println(message);
        }
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
