package com.cmendenhall.tests;

import com.cmendenhall.FileManager;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class FileManagerTest {
    private FileManager fileManager;

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
    public void fileManagerDeterminesIfFileIsDirectory() {
        boolean file = fileManager.isDirectory("/test/sampledirectory/index.html");
        boolean directory = fileManager.isDirectory("/test/sampledirectory");
        assertEquals(false, file);
        assertEquals(true, directory);
    }

    @Test
    public void fileManagerDeterminesIfResourceExists() {
        boolean exists = fileManager.resourceExists("/test/sampledirectory/");
        boolean doesNotExist = fileManager.resourceExists("/hovercraft/fullof.eels");
        assertEquals(true, exists);
        assertEquals(false, doesNotExist);
    }

}
