package com.cmendenhall;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

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
    public void fileManagerShouldRemovePrependedSlashesFromFilepath() {
        WebResource fileOne = fileManager.getWebResource("test/sampledirectory/index.html");
        WebResource fileTwo = fileManager.getWebResource("/test/sampledirectory/index.html");
        assertArrayEquals(fileOne.binaryData(), fileTwo.binaryData());
    }

}
