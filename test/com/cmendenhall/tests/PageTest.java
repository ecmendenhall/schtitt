package com.cmendenhall.tests;

import com.cmendenhall.webresources.Page;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class PageTest {
    private Page testPage;

    @Before
    public void setUp() {
        testPage = new Page("Fake content", "/fakepath");
    }

    @Test
    public void pageMimeTypeIsHTML() {
        assertEquals("text/html; charset=UTF-8", testPage.mimeType());
    }

    @Test
    public void pageURLisSetWhenConstructed() {
        assertEquals("/fakepath", testPage.url());
    }

    @Test
    public void pageReturnsCorrectContentLength() {
        assertEquals("12", testPage.contentLength());
    }

    @Test
    public void pageReturnsCorrectStringData() {
        assertEquals("Fake content", testPage.stringData());
    }

    @Test
    public void pageReturnsCorrectBinaryData() throws UnsupportedEncodingException {
        String reconstructed = new String(testPage.binaryData(), "UTF-8");
        assertEquals("Fake content", reconstructed);
    }

    @Test
    public void resourceStoresCustomHeaders() {
        testPage.addCustomHeader("Header", "content");
        assertTrue(testPage.customHeaders().containsKey("Header"));
        assertTrue(testPage.customHeaders().containsValue("content"));
    }
}
