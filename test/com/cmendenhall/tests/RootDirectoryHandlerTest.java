package com.cmendenhall.tests;

import com.cmendenhall.RootDirectoryHandler;
import com.cmendenhall.WebResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class RootDirectoryHandlerTest {
    RootDirectoryHandler handler;
    String rootDirectory = System.getProperty("user.dir");

    @Before
    public void setUp() {
        handler = new RootDirectoryHandler();
    }

    @Test
    public void rootDirectoryHandlerReturnsCurrentDirectoryResource() {
        WebResource directoryPage = handler.render();
        String directoryPageContent = directoryPage.stringData();
        assertTrue(directoryPageContent.contains("src/"));
    }

    @Test
    public void rootDirectoryHandlerReturnsCustomDirectoryResource() {
        System.setProperty("user.dir", "test/sampledirectory");
        WebResource directoryPage = handler.render();
        String directoryPageContent = directoryPage.stringData();
        //assertTrue(directoryPageContent.contains("slip.gif"));
    }

    @After
    public void cleanUp() {
        System.setProperty("user.dir", rootDirectory);
    }

}
