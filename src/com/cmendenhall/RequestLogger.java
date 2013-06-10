package com.cmendenhall;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestLogger {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void log(Request request) {
        Date date = new Date();
        System.out.println("[" + dateFormat.format(date) + "] " + request);
    }

}
