package com.cmendenhall;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static junit.framework.Assert.assertTrue;

public class ListHandlerTest {
    private PageHandler handler;
    private String field1;
    private String field2;
    private String field3;

    @Before
    public void setUp() {
        handler = new ListHandler();
        field1 = KeyValueStore.get("field1");
        field2 = KeyValueStore.get("field2");
        field3 = KeyValueStore.get("field3");
    }

    @Test
    public void listHandlerRendersTemplateWithValuesFromKeyValueStore() {
        KeyValueStore.put("field1", "Gruyere");
        KeyValueStore.put("field2", "Roquefort");
        KeyValueStore.put("field3", "Manchego");
        KeyValueStore.save();

        WebResource listPage = handler.render(new HashMap<String, String>());
        String listPageContent = listPage.stringData();

        assertTrue(listPageContent.contains("Gruyere"));
        assertTrue(listPageContent.contains("Roquefort"));
        assertTrue(listPageContent.contains("Manchego"));

    }

    @After
    public void cleanUp() {
        KeyValueStore.put("field1", field1);
        KeyValueStore.put("field2", field2);
        KeyValueStore.put("field3", field3);
    }
}
