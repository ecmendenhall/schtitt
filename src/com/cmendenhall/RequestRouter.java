package com.cmendenhall;

import java.util.HashMap;


public class RequestRouter {
    private HashMap<String, HashMap<String, PageHandler>> routes;
    private FileManager fileManager;
    private PageHandler rootDirectory, helloPage, timePage, formGet, formPost,
                        notFound, directoryPage, filePage, list, parameters,
                        redirect, simpleFormGet, simpleFormPost;

    public RequestRouter() {
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
        rootDirectory  = new RootDirectoryHandler();
        helloPage      = new HelloPageHandler();
        timePage       = new TimePageHandler();
        formGet        = new FormHandler("GET");
        formPost       = new FormHandler("POST");
        notFound       = new NotFoundHandler();
        directoryPage  = new DirectoryHandler();
        filePage       = new FileHandler();
        list           = new ListHandler();
        parameters     = new ParametersPageHandler();
        redirect       = new RedirectHandler("http://localhost:5000/");
        simpleFormGet  = new SimpleFormHandler("GET");
        simpleFormPost = new SimpleFormHandler("POST");
    }

    private void registerRoutes() {
        createRoute("GET",  "/",            rootDirectory);
        createRoute("GET",  "/hello",       helloPage);
        createRoute("GET",  "/time",        timePage);
        createRoute("GET",  "/list",        list);
        createRoute("GET",  "/parameters",  parameters);

        createRoute("GET",  "/form",        simpleFormGet);
        createRoute("POST", "/form",        simpleFormPost);
        createRoute("PUT",  "/form",        simpleFormPost);

        createRoute("GET",  "/listform",    formGet);
        createRoute("POST", "/listform",    formPost);
        createRoute("PUT",  "/listform",    formPost);

        createRoute("GET",  "/redirect",    redirect);
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
        params.put("stylesheet", StaticResourceCache.loadResource("style.css"));
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
