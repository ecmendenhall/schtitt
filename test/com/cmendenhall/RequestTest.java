package com.cmendenhall;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class RequestTest {
    Request request;

    @Before
    public void setUp() {
        HashMap<String, String> requestParameters = new HashMap<String, String>();
        requestParameters.put("method", "GET");
        requestParameters.put("path", "/");
        requestParameters.put("httpVersion", "HTTP/1.0");

        HashMap<String, List<String>> headerParameters = new HashMap<String, List<String>>();
        headerParameters.put("Accept", Arrays.asList("text/html", "application/xhtml+xml"));

        request = new Request(requestParameters, headerParameters, "");
    }

    @Test
    public void requestReturnsStoredMethod() {
        assertEquals("GET", request.method());
    }

    @Test
    public void requestReturnsStoredPath() {
        assertEquals("/", request.path());
    }

    @Test
    public void requestReturnsStoredHttpVersion() {
        assertEquals("HTTP/1.0", request.httpVersion());
    }

    @Test
    public void requestReturnsStoredHeaders() {
        List<String> expected = Arrays.asList("text/html", "application/xhtml+xml");
        assertEquals(expected, request.getHeader("Accept"));
    }

    @Test
    public void requestShouldPrintReadably() {
        assertEquals("GET /", request.toString());
    }

}
