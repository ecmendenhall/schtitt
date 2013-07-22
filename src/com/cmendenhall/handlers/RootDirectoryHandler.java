package com.cmendenhall.handlers;

import com.cmendenhall.webresources.DirectoryResource;

import java.util.HashMap;

public class RootDirectoryHandler implements PageHandler {
    private String rootDirectory = System.getProperty("user.dir");

    public DirectoryResource render() {
        return new DirectoryResource((rootDirectory.isEmpty()) ? "." : rootDirectory);
    }

    public DirectoryResource render(HashMap<String, String> params) {
        return render();
    }

}
