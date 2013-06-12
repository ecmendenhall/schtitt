package com.cmendenhall;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageLogger {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date;

    private String dateBlock() {
        date = new Date();
        return dateFormat.format(date) + "  ";
    }

    public void log(Request request) {
        System.out.println(dateBlock() + "[REQUEST]   " + request);
    }

    public void log(Response response) {
        System.out.println(dateBlock() + "[RESPONSE]  " + response.getStatusCode() + " " + response.getReasonPhrase());
    }

    public void log(String string) {
        System.out.println(dateBlock() + "[INFO]      " + string);
    }

}
