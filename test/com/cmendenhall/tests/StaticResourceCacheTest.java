package com.cmendenhall.tests;


import com.cmendenhall.webresources.StaticResourceCache;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;

import static junit.framework.Assert.assertTrue;

public class StaticResourceCacheTest {

    @Test
    public void staticResourceCacheSavesDataToCache() {
        StaticResourceCache.loadResource("style.css");

        ConcurrentHashMap<String, String> cache = StaticResourceCache.getCache();
        assertTrue(cache.containsKey("style.css"));
    }

}
