package com.cmendenhall.handlers;

import com.cmendenhall.handlers.PageHandler;
import com.cmendenhall.webresources.Template;
import com.cmendenhall.webresources.WebResource;

import java.util.HashMap;

public class HelloPageHandler implements PageHandler {

    public WebResource render(HashMap<String, String> params) {
        return Template.render("hello.html", params);
    }

}
