package com.cmendenhall.tests;


import com.cmendenhall.StaticResourceCache;
import org.junit.Test;

import java.util.HashMap;

import static junit.framework.Assert.assertTrue;

public class StaticResourceCacheTest {

    @Test
    public void staticResourceCacheSavesDataToCache() {
        StaticResourceCache.loadResource("style.css");

        HashMap<String, String> cache = StaticResourceCache.getCache();
        assertTrue(cache.containsKey("style.css"));
    }

}
