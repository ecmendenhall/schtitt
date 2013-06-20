package com.cmendenhall;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static junit.framework.Assert.assertEquals;

public class KeyValueStoreTest {
    private KeyValueStore keyValueStore;
    private String field1;
    private String field2;
    private String field3;

    @Before
    public void setUp() {
        keyValueStore = KeyValueStore.getInstance();

        field1 = keyValueStore.get("field1");
        field2 = keyValueStore.get("field2");
        field3 = keyValueStore.get("field3");

        keyValueStore.put("field1", "eggs");
        keyValueStore.put("field2", "milk");
        keyValueStore.put("field3", "cheese");
    }

    @Test
    public void keyValueStoreSavesAndRetrievesData() {
        String eggs = keyValueStore.get("field1");
        String milk = keyValueStore.get("field2");
        String cheese = keyValueStore.get("field3");

        assertEquals("eggs", eggs);
        assertEquals("milk", milk);
        assertEquals("cheese", cheese);
    }

    @Test
    public void keyValueStorePersistsData() {
        keyValueStore.put("field3", "bananas");
        keyValueStore.save();
        keyValueStore.load();
        String bananas = keyValueStore.get("field3");
        assertEquals("bananas", bananas);
    }

    @After
    public void cleanUp() {
        keyValueStore.put("field1", field1);
        keyValueStore.put("field2", field2);
        keyValueStore.put("field3", field3);
    }

}
