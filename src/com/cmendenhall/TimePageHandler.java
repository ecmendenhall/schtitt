package com.cmendenhall;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class TimePageHandler implements PageHandler {
    private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss z");

    public WebResource render(HashMap<String, String> params) {
        Date now = new Date();
        params.put("time", dateFormat.format(now));

        return Template.render("time.html", params);
    }
}
