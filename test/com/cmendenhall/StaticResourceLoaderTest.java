package com.cmendenhall;

import org.junit.Before;
import org.junit.Test;

import static com.cmendenhall.Utils.join;
import static junit.framework.Assert.assertEquals;

public class StaticResourceLoaderTest {
    private StaticResourceLoader staticResourceLoader;
    private String pageContent;

    @Before
    public void setUp() {
        staticResourceLoader = new StaticResourceLoader();
        pageContent = join("\n", "<!DOCTYPE html>",
                                 "<html>",
                                 "    <head>",
                                 "        <title>Schtitt 0.9a</title>",
                                 "        <style>",
                                 "            {{% stylesheet %}}",
                                 "        </style>",
                                 "    </head>",
                                 "    <body>",
                                 "        <h1>Schtitt 0.9a</h1>",
                                 "        <h3>A simple HTTP server.</h3>",
                                 "        <p>Visit any directory URL to browse its contents, or try one of these special paths:</p>",
                                 "        <ul>",
                                 "            <li><a href=\"/time\">/time</a></li>",
                                 "            <li><a href=\"/form\">/form</a></li>",
                                 "        </ul>",
                                 "    </body>",
                                 "</html>");
    }

    @Test
    public void resourceLoaderShouldReadPagesIntoStrings() {
        assertEquals(pageContent, staticResourceLoader.loadResource("hello.html"));
    }

}
