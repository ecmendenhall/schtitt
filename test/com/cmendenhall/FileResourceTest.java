package com.cmendenhall;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static com.cmendenhall.Utils.join;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class FileResourceTest {
    private FileResource png;
    private FileResource gif;
    private FileResource jpg;
    private FileResource html;
    private FileResource pdf;
    private FileResource noExtension;
    private String samplePage;

    @Before
    public void setUp() {
        png = new FileResource("test/sampledirectory/tennis.png");
        gif = new FileResource("test/sampledirectory/slip.gif");
        jpg = new FileResource("test/sampledirectory/eschaton.jpg");
        html  = new FileResource("test/sampledirectory/index.html");
        pdf = new FileResource("test/sampledirectory/characters.pdf");
        noExtension = new FileResource("test/sampledirectory/nofiletype");
        samplePage = join("\n", "<html>",
                                "  <head>",
                                "  </head>",
                                "  <body>",
                                "    <p>",
                                "      Junior athletics is but one facet of the real gem:",
                                "      life's endless war against the self you cannot live without.",
                                "    </p>",
                                "  </body>",
                                "</html>",
                                ""
        );
    }

    @Test
    public void resourcesReturnCorrectMimeType() {
        String pngType = png.mimeType();
        assertEquals("image/png", pngType);

        String gifType = gif.mimeType();
        assertEquals("image/gif", gifType);

        String jpgType = jpg.mimeType();
        assertEquals("image/jpg", jpgType);

        String htmlType = html.mimeType();
        assertEquals("text/html; charset=UTF-8", htmlType);

        String pdfType = pdf.mimeType();
        assertEquals("application/pdf", pdfType);

        String noExtensionType = noExtension.mimeType();
        assertEquals("application/octet-stream", noExtensionType);
    }

    @Test
    public void resourcesStoreURL() {
        String imageUrl = png.url();
        assertEquals("test/sampledirectory/tennis.png", imageUrl);

        String htmlUrl = html.url();
        assertEquals("test/sampledirectory/index.html", htmlUrl);
    }

    @Test
    public void resourceReturnsBinaryData() throws UnsupportedEncodingException {
        byte[] imageBytes = png.binaryData();
        byte[] htmlBytes = html.binaryData();

        File tennisImage = new File("test/sampledirectory/tennis.png");
        FileInputStream byteStream;
        byte[] expected = new byte[(int) tennisImage.length()];
        try {
            byteStream = new FileInputStream(tennisImage);
            int currentByte;
            int byteIndex = 0;
            while((currentByte = byteStream.read()) != -1) {
                expected[byteIndex] = (byte) currentByte;
                byteIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertArrayEquals(expected, imageBytes);
        assertArrayEquals(samplePage.getBytes("UTF-8"), htmlBytes);
    }

    @Test
    public void resourceReturnsDataAsString() {
        String pageData = html.stringData();
        assertEquals(samplePage, pageData);
    }

    private void testCheckSum(FileResource file, String checksum) {
        assertEquals(checksum, file.checkSum());
    }

    @Test
    public void filesReturnCorrectMD5CheckSum() {
        testCheckSum(html, "wVh5xL+Xc3s0v+ZUozFNSA==");
        testCheckSum(png, "nplBGOxl9yFPjCe+Mu2PlA==");
        testCheckSum(gif, "7Ngh9u7eT4A0rb8yVl2TnA==");
        testCheckSum(jpg, "wLeHNnlpNs3nmSSDydtTrA==");
        testCheckSum(pdf, "qhfGNRL5BihtUrscUvhDyg==");
    }

    @Test
    public void filesReturnCorrectContentLengths() {
        assertEquals("194", "" + html.contentLength());
    }

}
