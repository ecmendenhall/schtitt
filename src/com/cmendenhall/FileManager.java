package com.cmendenhall;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

public class FileManager {
    private String rootDirectory = System.getProperty("user.dir");
    private static ConcurrentHashMap<String, File> fileCache = new ConcurrentHashMap<String, File>();

    public List<String> listDirectory(String path) {
        File filePath = getFile(path);
        return Arrays.asList(filePath.list());
    }

    public boolean isDirectory(String path) {
        File endpoint = getFile(rootDirectory + path);
        return endpoint.isDirectory();
    }

    public boolean resourceExists(String path) {
        if (path.contentEquals("/")) return true;
        File file = getFile(rootDirectory + path);
        return file.exists();
    }

    public static File getFile(String path) {
        if (fileCache.containsKey(path)) {
            return fileCache.get(path);
        } else {
            File file = new File(path);
            fileCache.put(path, file);
            return file;
        }

    }

}
