package com.cmendenhall.handlers;

import com.cmendenhall.webresources.WebResource;

import java.util.HashMap;

public interface PageHandler {

    public WebResource render(HashMap<String, String> params);

}
