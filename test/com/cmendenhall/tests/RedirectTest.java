package com.cmendenhall.tests;

import com.cmendenhall.webresources.Redirect;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

public class RedirectTest {
    private Redirect redirect;

    @Before
    public void setUp() {
        redirect = new Redirect("/list");
    }

    @Test
    public void redirectStoresCorrectUrlInformation() {
        assertEquals("", redirect.stringData());
        assertEquals("0", redirect.contentLength());
        assertEquals("text/html", redirect.mimeType());
        assertEquals("", redirect.checkSum());
        assertArrayEquals(new byte[0], redirect.binaryData());
        assertEquals("/list", redirect.url());
    }

    @Test
    public void redirectStoresCustomLocationHeader() {
        assertEquals("/list", redirect.customHeaders().get("Location"));
    }

    @Test
    public void resourceStoresCustomHeaders() {
        redirect.addCustomHeader("Header", "content");
        assertTrue(redirect.customHeaders().containsKey("Header"));
        assertTrue(redirect.customHeaders().containsValue("content"));
    }
}
