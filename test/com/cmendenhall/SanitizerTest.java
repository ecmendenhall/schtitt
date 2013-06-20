package com.cmendenhall;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class SanitizerTest {
    private Sanitizer sanitizer;

    @Before
    public void setUp() {
        sanitizer = new Sanitizer();
    }

    @Test
    public void sanitizerShouldEscapeHTMLElements() {
        String spamTag = "<a href='http://weirdtricks.biz'>check out these weird old tricks to do stuff</a>";
        String javaScript = "<script>alert('Sanitize your input!');</script>";

        assertEquals("&lt;a href&#61;&apos;http://weirdtricks.biz&apos;&gt;check out these weird old tricks to do stuff&lt;/a&gt;",
                     sanitizer.escape(spamTag));

        assertEquals("&lt;script&gt;alert&#40;&apos;Sanitize your input&#33;&apos;&#41;;&lt;/script&gt;",
                     sanitizer.escape(javaScript));
    }
}
