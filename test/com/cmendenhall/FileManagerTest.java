package com.cmendenhall;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class FileManagerTest {
    private FileManager fileManager;
    private String samplePage;

    @Before
    public void setUp() {
        fileManager = new FileManager();
        samplePage = "<html>" +
                     "  <head>" +
                     "  </head>" +
                     "  <body>" +
                     "    <p>" +
                     "      Junior athletics is but one facet of the real gem:" +
                     "      life's endless war against the self you cannot live without." +
                     "    </p>" +
                     "  </body>" +
                     "</html>";
    }

    @Test
    public void fileManagerShouldListFilesInDirectory() {
        List<String> files = fileManager.listDirectory("test/sampledirectory");
        HashSet<String> fileSet = new HashSet<String>(files);
        assertTrue(fileSet.contains("index.html"));
    }

    @Test
    public void fileManagerShouldReadFiles() {
        String index = fileManager.read("test/sampledirectory/index.html");
        assertEquals(samplePage, index);
    }

}
