package com.cmendenhall;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FileManager {

    public List<String> listDirectory(String path) {
        File filePath = new File(path);
        return Arrays.asList(filePath.list());
    }

    private boolean isDirectory(String path) {
        File endpoint = new File(path);
        return endpoint.isDirectory();
    }

    public boolean resourceExists(String path) {
        if (path.charAt(0) == '/') {
            path = path.substring(1);
        }
        File file = new File(path);
        return file.exists();
    }

    public WebResource getWebResource(String path) {
        if (path.charAt(0) == '/') {
            path = path.substring(1);
        }
        if (isDirectory(path)) {
            return new DirectoryResource(path);
        } else {
            return new FileResource(path);
        }
    }
}
