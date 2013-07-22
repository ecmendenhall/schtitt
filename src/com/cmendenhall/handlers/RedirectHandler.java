package com.cmendenhall.handlers;

import com.cmendenhall.webresources.Redirect;
import com.cmendenhall.webresources.WebResource;

import java.util.HashMap;

public class RedirectHandler implements PageHandler {
    private String url;

    public RedirectHandler(String redirectUrl) {
        url = redirectUrl;
    }

    public WebResource render(HashMap<String, String> params) {
        return new Redirect(url);
    }

}
