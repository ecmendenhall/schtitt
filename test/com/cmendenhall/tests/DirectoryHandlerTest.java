package com.cmendenhall.tests;

import com.cmendenhall.DirectoryHandler;
import com.cmendenhall.WebResource;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class DirectoryHandlerTest {
    DirectoryHandler handler;
    HashMap<String, String> params;

    @Before
    public void setUp() {
        handler = new DirectoryHandler();
        params = new HashMap<String, String>();
        params.put("rootdirectory", "");
        params.put("filepath", "test/sampledirectory");
    }

    @Test
    public void directoryHandlerReturnsDirectoryResource() {
        WebResource directoryPage = handler.render(params);
        String directoryPageContent  = directoryPage.stringData();
        assertTrue(directoryPageContent.contains("slip.gif"));
    }

}
