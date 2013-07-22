package com.cmendenhall.handlers;

import com.cmendenhall.KeyValueStore;
import com.cmendenhall.Sanitizer;
import com.cmendenhall.webresources.Template;
import com.cmendenhall.webresources.WebResource;

import java.util.HashMap;

public class SimpleFormHandler implements PageHandler {
    private String method;
    private Sanitizer sanitizer = new Sanitizer();

    public SimpleFormHandler(String httpMethod) {
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
        }
        params.put("data", KeyValueStore.get("data"));
        return Template.render("simpleform.html", params);
    }
}
