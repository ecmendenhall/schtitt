package com.cmendenhall;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class FileManager {
    private String rootDirectory = System.getProperty("user.dir");

    public List<String> listDirectory(String path) {
        File filePath = new File(path);
        return Arrays.asList(filePath.list());
    }

    public boolean isDirectory(String path) {
        File endpoint = new File(rootDirectory + path);
        return endpoint.isDirectory();
    }

    public boolean resourceExists(String path) {
        if (path.contentEquals("/")) return true;
        File file = new File(rootDirectory + path);
        return file.exists();
    }

}
