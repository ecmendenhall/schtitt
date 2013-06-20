package com.cmendenhall.tests;

import com.cmendenhall.Redirect;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class RedirectTest {
    private Redirect redirect;

    @Before
    public void setUp() {
        redirect = new Redirect("/list");
    }

    @Test
    public void redirectStoresOnlyUrlInformation() {
        assertEquals("", redirect.stringData());
        assertEquals(null, redirect.contentLength());
        assertEquals(null, redirect.mimeType());
        assertEquals(null, redirect.checkSum());
        assertArrayEquals(new byte[0], redirect.binaryData());
        assertEquals("/list", redirect.url());
    }
}
