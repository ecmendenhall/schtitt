    package com.cmendenhall.tests;

    import com.cmendenhall.DirectoryResource;
    import org.junit.Before;
    import org.junit.Test;

    import static junit.framework.Assert.assertEquals;
    import static junit.framework.Assert.assertTrue;

    public class DirectoryResourceTest {
        private DirectoryResource directory;

        @Before
        public void setUp() {
            directory = new DirectoryResource("test/sampledirectory");
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
            assertTrue(directory.stringData().contains("<li class=\"file\"><a href=\"characters.pdf\">characters.pdf</a></li>"));
            assertTrue(directory.stringData().contains("<li class=\"file\"><a href=\"complicated.html\">complicated.html</a></li>"));
            assertTrue(directory.stringData().contains("<li class=\"file\"><a href=\"eschaton.jpg\">eschaton.jpg</a></li>"));
            assertTrue(directory.stringData().contains("<li class=\"file\"><a href=\"index.html\">index.html</a></li>"));
            assertTrue(directory.stringData().contains("<li class=\"file\"><a href=\"nofiletype\">nofiletype</a></li>"));
            assertTrue(directory.stringData().contains("<li class=\"file\"><a href=\"slip.gif\">slip.gif</a></li>"));
            assertTrue(directory.stringData().contains("<li class=\"file\"><a href=\"tennis.png\">tennis.png</a></li>"));
            assertTrue(directory.stringData().contains("<li class=\"file\"><a href=\"text.txt\">text.txt</a></li>"));
        }

        @Test
        public void resourceReturnsIndexPageAsByteArray() {
            String output = new String(directory.binaryData());
            assertTrue(output.contains("<li class=\"file\"><a href=\"characters.pdf\">characters.pdf</a></li>"));
            assertTrue(output.contains("<li class=\"file\"><a href=\"complicated.html\">complicated.html</a></li>"));
            assertTrue(output.contains("<li class=\"file\"><a href=\"eschaton.jpg\">eschaton.jpg</a></li>"));
            assertTrue(output.contains("<li class=\"file\"><a href=\"index.html\">index.html</a></li>"));
            assertTrue(output.contains("<li class=\"file\"><a href=\"nofiletype\">nofiletype</a></li>"));
            assertTrue(output.contains("<li class=\"file\"><a href=\"slip.gif\">slip.gif</a></li>"));
            assertTrue(output.contains("<li class=\"file\"><a href=\"tennis.png\">tennis.png</a></li>"));
            assertTrue(output.contains("<li class=\"file\"><a href=\"text.txt\">text.txt</a></li>"));
        }

        @Test
        public void resourceReturnsCorrectCheckSum() {
            //String expectedCheckSum = "syAFkiAF+el/9NlAf/iSfg==";
            //assertEquals(expectedCheckSum, directory.checkSum());
        }

        @Test
        public void resourceReturnsCorrectContentLength() {
            assertEquals("4271", directory.contentLength());
        }

        @Test
        public void resourceStoresCustomHeaders() {
            directory.addCustomHeader("Header", "content");
            assertTrue(directory.customHeaders().containsKey("Header"));
            assertTrue(directory.customHeaders().containsValue("content"));
        }

    }
