package com.cmendenhall;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static junit.framework.Assert.assertEquals;

public class FileHandlerTest {
    FileHandler handler;
    HashMap<String, String> params;

    @Before
    public void setUp() {
        handler = new FileHandler();
        params = new HashMap<String, String>();
        params.put("rootdirectory", "");
        params.put("filepath", "test/sampledirectory/slip.gif");
    }

    @Test
    public void fileHandlerReturnsDirectoryResource() {
        WebResource filePage = handler.render(params);
        //assertEquals("7Ngh9u7eT4A0rb8yVl2TnA==", filePage.checkSum());
    }

}
