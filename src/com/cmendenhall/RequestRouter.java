package com.cmendenhall;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class RequestRouter {
    private HashMap<String, HashMap<String, PageHandler>> routes;
    private StaticResourceLoader loader;
    private FileManager fileManager;
    private PageHandler rootDirectory, helloPage, timePage, formGet, formPost, notFound, directoryPage, filePage, list;

    public RequestRouter() {
        loader = new StaticResourceLoader();
        fileManager = new FileManager();
        createRouteMap();
        loadHandlers();
        registerRoutes();
    }

    private void createRouteMap() {
        routes = new HashMap<String, HashMap<String, PageHandler>>();
        routes.put("GET", new HashMap<String, PageHandler>());
        routes.put("POST", new HashMap<String, PageHandler>());
        routes.put("PUT", new HashMap<String, PageHandler>());
    }

    public void createRoute(String method, String route, PageHandler handler) {
        HashMap<String, PageHandler> methodRoutes = routes.get(method);
        methodRoutes.put(route, handler);
    }

    private void loadHandlers() {
        rootDirectory = new RootDirectoryHandler();
        helloPage     = new HelloPageHandler();
        timePage      = new TimePageHandler();
        formGet       = new FormHandler("GET");
        formPost      = new FormHandler("POST");
        notFound      = new NotFoundHandler();
        directoryPage = new DirectoryHandler();
        filePage      = new FileHandler();
        list          = new ListHandler();
    }

    private void registerRoutes() {
        createRoute("GET",  "/",            rootDirectory);
        createRoute("GET",  "/hello",       helloPage);
        createRoute("GET",  "/time",        timePage);
        createRoute("GET",  "/form",        formGet);
        createRoute("GET",  "/list",        list);
        createRoute("POST", "/form",        formPost);
        createRoute("PUT",  "/form",        formPost);
    }

    public boolean routeRegistered(String method, String path) {
        return routes.get(method).containsKey(path);
    }

    public boolean pathExists(String method, String path) {
        return fileManager.resourceExists(path);
    }

    public WebResource getResource(String method, String path) {
        return getResource(method, path, new HashMap<String, String>());
    }

    public WebResource getResource(String method, String path, HashMap<String, String> queryParameters) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("stylesheet", loader.loadResource("style.css"));
        params.put("rootdirectory", System.getProperty("user.dir"));
        params.put("filepath", path);

        for (String queryParameter : queryParameters.keySet()) {
            params.put(queryParameter,
                       queryParameters.get(queryParameter));
        }

        if (routeRegistered(method, path)) {
            return routes.get(method).get(path).render(params);
        }

        if (!fileManager.resourceExists(path)) {
            return notFound.render(params);
        }

        if (fileManager.isDirectory(path)) {
            return directoryPage.render(params);
        } else {
            return filePage.render(params);
        }

    }

}
