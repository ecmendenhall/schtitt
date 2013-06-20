package com.cmendenhall;

import java.util.HashMap;

public class FileHandler implements PageHandler {

    public WebResource render(HashMap<String, String> params) {
        String rootDirectory = params.get("rootdirectory");
        String path = params.get("filepath");
        return new FileResource(rootDirectory + path);
    }

}
