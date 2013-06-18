package com.cmendenhall;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;

import static junit.framework.Assert.*;

public class RequestRouterTest {
    private RequestRouter requestRouter;
    private String samplePage;

    @Before
    public void setUp() {
        requestRouter = new RequestRouter();
    }

    @Test
    public void fileManagerShouldListFilesInDirectory() {
        List<String> files = requestRouter.listDirectory("test/sampledirectory");
        HashSet<String> fileSet = new HashSet<String>(files);
        assertTrue(fileSet.contains("index.html"));
    }

    @Test
    public void fileManagerReturnsFileResourcesWhenFileIsRequested() {
        FileResource file = (FileResource) requestRouter.getWebResource("/test/sampledirectory/index.html");
        assertFalse(file.isDirectory());
    }

    @Test
    public void fileManagerReturnsDirectoryResourcesWhenDirectoryIsRequested() {
        DirectoryResource directory = (DirectoryResource) requestRouter.getWebResource("/test/sampledirectory/");
        assertTrue(directory.isDirectory());
    }

    @Test
    public void fileManagerReturnsSpecialPageWhenSpecialPathIsRequested() {
        SpecialPage specialPage = (SpecialPage) requestRouter.getWebResource("/hello");
        assertEquals("/hello", specialPage.url());
    }

    @Test
    public void fileManagerReturnsCurrentDirectoryWhenRootPathIsRequested() {
        DirectoryResource rootDirectoryPage = (DirectoryResource) requestRouter.getWebResource("/");
        assertTrue(rootDirectoryPage.url().contains("/IdeaProjects/HTTPServer"));
    }

}
