package com.cmendenhall.handlers;

import com.cmendenhall.webresources.Template;
import com.cmendenhall.webresources.WebResource;

import java.util.HashMap;

public class NotFoundHandler implements PageHandler {

    public WebResource render(HashMap<String, String> params) {
        return Template.render("404.html", params);
    }

}

