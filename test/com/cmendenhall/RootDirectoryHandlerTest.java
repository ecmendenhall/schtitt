package com.cmendenhall;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static junit.framework.Assert.assertEquals;

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
        assertEquals("nyKHph+zKPrs5+D5Ohy87Q==", directoryPage.checkSum());
    }

    @Test
    public void rootDirectoryHandlerReturnsCustomDirectoryResource() {
        System.setProperty("user.dir", "test/sampledirectory");
        WebResource directoryPage = handler.render();
        assertEquals("qUR/Dwcz7zXKALSlD9G7sg==", directoryPage.checkSum());
    }

    @After
    public void cleanUp() {
        System.setProperty("user.dir", rootDirectory);
    }

}
