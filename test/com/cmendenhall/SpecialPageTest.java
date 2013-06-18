package com.cmendenhall;

import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static junit.framework.Assert.assertEquals;

public class SpecialPageTest {
    private SpecialPage hello;
    private String helloContent;
    private StaticResourceLoader loader;
    private OutputRecorder recorder;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        recorder = new OutputRecorder();
        recorder.start();
        loader = new StaticResourceLoader();
        hello = new SpecialPage("hello.html", "/hello");
        helloContent = loader.loadResource("hello.html");
    }

    @Test
    public void specialPageLoadsResourceContent() {
        System.out.println(hello.stringData());
        assertEquals(helloContent, hello.stringData());
    }

    @Test
    public void mimeTypeIsHTML() {
        assertEquals("text/html; charset=UTF-8", hello.mimeType());
    }

    @Test
    public void specialPageStoresURL() {
        assertEquals("/hello", hello.url());
    }

}
