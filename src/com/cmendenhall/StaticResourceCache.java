package com.cmendenhall;

import java.util.HashMap;

public class StaticResourceCache {
    private static StaticResourceLoader loader = new StaticResourceLoader();
    private static HashMap<String, String> cache = new HashMap<String, String>();

    public static String loadResource(String resource) {
        if (cache.containsKey(resource)) {
            return cache.get(resource);
        } else {
            String result = loader.loadResourceFromStream(resource);
            cache.put(resource, result);
            return result;
        }
    }

    public static HashMap<String, String> getCache() {
        return cache;
    }
}
