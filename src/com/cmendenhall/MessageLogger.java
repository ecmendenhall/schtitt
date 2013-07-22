package com.cmendenhall;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageLogger {
    private MessageQueue messageQueue = MessageQueue.getInstance();
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Date date;

    private String dateBlock() {
        date = new Date();
        return dateFormat.format(date) + "  ";
    }

    public void printStartupMessage() {
        messageQueue.add("Schtitt 0.9a");
        messageQueue.add("Press c-C to exit.");
    }

    public void log(Request request) {
        messageQueue.add(dateBlock() + "[REQUEST]   " + request);
    }

    public void log(Response response) {
        messageQueue.add(dateBlock() + "[RESPONSE]  " + response.getStatusCode() + " " + response.getReasonPhrase());
    }

    public void log(String string) {
        messageQueue.add(dateBlock() + "[INFO]      " + string);
    }


}
