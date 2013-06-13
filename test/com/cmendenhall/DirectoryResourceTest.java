package com.cmendenhall;

import org.junit.Before;
import org.junit.Test;

import static com.cmendenhall.Utils.join;
import static junit.framework.Assert.assertEquals;

public class DirectoryResourceTest {
    private ResourceLoader loader = new ResourceLoader();
    private DirectoryResource directory;
    private String indexPage;

    @Before
    public void setUp() {
        directory = new DirectoryResource("test/sampledirectory");
        String template = join("\n", "<html>",
                               "  <head>",
                               "    <title>Directory Listing</title>",
                               "    <style>",
                               "      {{% stylesheet %}}",
                               "    </style>",
                               "  </head>",
                               "  <body>",
                               "  <h1 class=\"directoryheader\">Directory listing</h1>",
                               "  <h2 class=\"directoryheader\">test/sampledirectory</h2>",
                               "    <ul>",
                               "      <li class=''><a href='/../'>[ .. ]</a></li>",
                               "      <li class='file'><a href='characters.pdf'>characters.pdf</a></li>",
                               "      <li class='file'><a href='complicated.html'>complicated.html</a></li>",
                               "      <li class='file'><a href='eschaton.jpg'>eschaton.jpg</a></li>",
                               "      <li class='file'><a href='index.html'>index.html</a></li>",
                               "      <li class='file'><a href='nofiletype'>nofiletype</a></li>",
                               "      <li class='file'><a href='slip.gif'>slip.gif</a></li>",
                               "      <li class='file'><a href='tennis.png'>tennis.png</a></li>",
                               "      <li class='file'><a href='text.txt'>text.txt</a></li>",
                               "    </ul>",
                               "  </body>",
                               "</html>");
        String stylesheet = loader.loadResource("style.css");
        indexPage = template.replace("{{% stylesheet %}}", stylesheet);
    }

    @Test
    public void resourcesStoreURL() {
        String url = directory.url();
        assertEquals("test/sampledirectory", url);
    }

    @Test
    public void mimeTypeIsHTML() {
        assertEquals("text/html; charset=UTF-8", directory.mimeType());
    }

    @Test
    public void resourceConstructsDirectoryIndexPage() {
        assertEquals(indexPage, directory.stringData());
    }

    @Test
    public void resourceReturnsIndexPageAsByteArray() {
        String output = new String(directory.binaryData());
        assertEquals(indexPage, output);
    }

    @Test
    public void resourceReturnsCorrectCheckSum() {
        String expectedCheckSum = "syAFkiAF+el/9NlAf/iSfg==";
        assertEquals(expectedCheckSum, directory.checkSum());
    }

    @Test
    public void resourceReturnsCorrectContentLength() {
        assertEquals("4197", directory.contentLength());
    }

}
