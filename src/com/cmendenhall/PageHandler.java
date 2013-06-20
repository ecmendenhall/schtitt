package com.cmendenhall;

import java.util.HashMap;

public interface PageHandler {

    public WebResource render(HashMap<String, String> params);

}
