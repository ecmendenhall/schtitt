package com.cmendenhall;

import org.junit.Before;
import org.junit.Test;

import static com.cmendenhall.Utils.join;
import static junit.framework.Assert.assertEquals;

public class DirectoryResourceTest {
    DirectoryResource directory;
    String indexPage;

    @Before
    public void setUp() {
        directory = new DirectoryResource("test/sampledirectory");
        indexPage = join("\n", "<html>",
                               "  <head>",
                               "  </head>",
                               "  <body>",
                               "    <ul>",
                               "      <li><a href='characters.pdf'>characters.pdf</a></li>",
                               "      <li><a href='complicated.html'>complicated.html</a></li>",
                               "      <li><a href='eschaton.jpg'>eschaton.jpg</a></li>",
                               "      <li><a href='index.html'>index.html</a></li>",
                               "      <li><a href='nofiletype'>nofiletype</a></li>",
                               "      <li><a href='slip.gif'>slip.gif</a></li>",
                               "      <li><a href='tennis.png'>tennis.png</a></li>",
                               "      <li><a href='text.txt'>text.txt</a></li>",
                               "    </ul>",
                               "  </body>",
                               "</html>");
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

}
