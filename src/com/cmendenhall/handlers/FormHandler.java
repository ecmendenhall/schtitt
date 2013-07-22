package com.cmendenhall.handlers;

import com.cmendenhall.*;
import com.cmendenhall.webresources.Redirect;
import com.cmendenhall.webresources.Template;
import com.cmendenhall.webresources.WebResource;

import java.util.HashMap;

public class FormHandler implements PageHandler {
    private String method;
    private Sanitizer sanitizer = new Sanitizer();

    public FormHandler() {
        method = "GET";
    }

    public FormHandler(String httpMethod) {
        method = httpMethod;
    }

    public WebResource render(HashMap<String, String> params) {
        if (method.equals("POST")) {
            for (String param : params.keySet()) {
                String userInput = params.get(param);
                String sanitized = sanitizer.escape(userInput);
                KeyValueStore.put(param, sanitized);
            }
            KeyValueStore.save();
            return new Redirect("/list");
        } else {
            return Template.render("form.html", params);
        }
    }

}
