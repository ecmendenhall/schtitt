package com.cmendenhall;

import java.util.HashMap;

public class HelloPageHandler implements PageHandler {

    public WebResource render(HashMap<String, String> params) {
        return Template.render("hello.html", params);
    }

}
