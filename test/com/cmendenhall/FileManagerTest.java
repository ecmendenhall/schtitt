package com.cmendenhall;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;

import static junit.framework.Assert.*;

public class FileManagerTest {
    private FileManager fileManager;
    private String samplePage;

    @Before
    public void setUp() {
        fileManager = new FileManager();
    }

    @Test
    public void fileManagerShouldListFilesInDirectory() {
        List<String> files = fileManager.listDirectory("test/sampledirectory");
        HashSet<String> fileSet = new HashSet<String>(files);
        assertTrue(fileSet.contains("index.html"));
    }

    @Test
    public void fileManagerReturnsFileResourcesWhenFileIsRequested() {
        FileResource file = (FileResource)fileManager.getWebResource("/test/sampledirectory/index.html");
        assertFalse(file.isDirectory());
    }

    @Test
    public void fileManagerReturnsDirectoryResourcesWhenDirectoryIsRequested() {
        DirectoryResource directory = (DirectoryResource)fileManager.getWebResource("/test/sampledirectory/");
        assertTrue(directory.isDirectory());
    }

    @Test
    public void fileManagerReturnsSpecialPageWhenSpecialPathIsRequested() {
        SpecialPage specialPage = (SpecialPage)fileManager.getWebResource("/hello");
        assertEquals("/hello", specialPage.url());
    }

    @Test
    public void fileManagerReturnsCurrentDirectoryWhenRootPathIsRequested() {
        DirectoryResource rootDirectoryPage = (DirectoryResource)fileManager.getWebResource("/");
        assertTrue(rootDirectoryPage.url().contains("/IdeaProjects/HTTPServer"));
    }

}
