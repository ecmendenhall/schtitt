package com.cmendenhall.handlers;

import com.cmendenhall.webresources.Template;
import com.cmendenhall.webresources.WebResource;

import java.util.HashMap;

public class ParametersPageHandler implements PageHandler {

    public WebResource render(HashMap<String, String> params) {
        return Template.render("parameters.html", params);
    }

}
