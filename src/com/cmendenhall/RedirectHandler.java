package com.cmendenhall;

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
