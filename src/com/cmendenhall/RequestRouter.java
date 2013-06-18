package com.cmendenhall;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RequestRouter {
    private String rootDirectory = System.getProperty("user.dir");
    private HashMap<String, String> specialPaths;
    private StaticResourceLoader loader;
    private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss z");

    public RequestRouter() {
        loader = new StaticResourceLoader();
        specialPaths = new HashMap<String, String>();
        registerSpecialPaths();
    }

    private void registerSpecialPaths() {
        specialPaths.put("/hello", "hello.html");
        specialPaths.put("/time", "time.html");
        specialPaths.put("/form", "form.html");
        specialPaths.put("/list", "list.html");
    }

    public List<String> listDirectory(String path) {
        File filePath = new File(path);
        return Arrays.asList(filePath.list());
    }

    private boolean isDirectory(String path) {
        File endpoint = new File(path);
        return endpoint.isDirectory();
    }

    public boolean resourceExists(String path) {
        if (specialPaths.containsKey(path) || path.contentEquals("/")) return true;
        File file = new File(rootDirectory + path);
        return file.exists();
    }

    public WebResource getWebResource(String path) {
        if (specialPaths.containsKey(path)) {
            String resource = specialPaths.get(path);
            HashMap<String, String> params = new HashMap<String, String>();
            Date now = new Date();
            params.put("time", dateFormat.format(now));
            params.put("stylesheet", loader.loadResource("style.css"));
            return new SpecialPage(resource, path, params);
        }
        if (path.contentEquals("/")) {
            return new DirectoryResource((rootDirectory.isEmpty()) ? "." : rootDirectory);
        }
        if (!resourceExists(path)) {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("stylesheet", loader.loadResource("style.css"));
            return new SpecialPage("404.html", path, params);
        }
        if (isDirectory(rootDirectory + path)) {
            return new DirectoryResource(rootDirectory + path);
        } else {
            return new FileResource(rootDirectory + path);
        }
    }
}
