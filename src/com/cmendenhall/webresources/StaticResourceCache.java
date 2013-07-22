package com.cmendenhall.webresources;

import java.util.concurrent.ConcurrentHashMap;

public class StaticResourceCache {
    private static StaticResourceLoader loader = new StaticResourceLoader();
    private static ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<String, String>();

    public static String loadResource(String resource) {
        if (cache.containsKey(resource)) {
            return cache.get(resource);
        } else {
            String result = loader.loadResourceFromStream(resource);
            cache.put(resource, result);
            return result;
        }
    }

    public static ConcurrentHashMap<String, String> getCache() {
        return cache;
    }

    public static void addResource(String name, String data) {
        cache.put(name, data);
    }

    public static boolean containsResource(String name) {
        return (cache.containsKey(name)) ? true : false;
    }
}
