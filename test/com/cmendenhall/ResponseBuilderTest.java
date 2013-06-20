package com.cmendenhall;

import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class ResponseBuilderTest {
    private ResponseBuilder responseBuilder;
    private Response response;

    @Before
    public void setUp() {
        responseBuilder = new ResponseBuilder();
        response = responseBuilder.constructResponse("GET", "/hello");
    }

    @Test
    public void responseBuilderConstructsResponse() {
        assertEquals("Response", response.getClass().getSimpleName());
    }

    @Test
    public void responseBuilderAddsDefaultHeaders() {
        assertEquals("HTTP/1.1", response.getHttpVersion());
        assertEquals("Schtitt/0.9a", response.getHeader("Server"));
        assertEquals("no-cache", response.getHeader("Pragma"));
    }

    @Test
    public void responseBuilderAddsBodyDependentHeaders() {
        assertEquals("text/html; charset=UTF-8", response.getHeader("Content-Type"));
        assertEquals("3816", response.getHeader("Content-Length"));
        assertEquals("5dG/HJKTpVyeM1oNIsE1vg==", response.getHeader("Content-MD5"));
    }

    @Test
    public void responseBuilderAddsDateHeader() {
        String expected = "[A-Z][a-z]{2}, \\d{2} [A-Z][a-z]{2} \\d{4} \\d{2}:\\d{2}:\\d{2} [\\+\\-]\\d{4}";
        assertTrue(response.getHeader("Date").matches(expected));
    }

    @Test
    public void responseBuilderAddsTwoHundredStatusCodeIfResourceExists() {
        assertEquals("200", "" + response.getStatusCode());
    }

    @Test
    public void responseBuilderAddsFourOhFourStatusCodeIfResourceNotFound() {
        response = responseBuilder.constructResponse("GET", "/hovercraft");
        assertEquals("404", "" + response.getStatusCode());
    }

    @Test
    public void responseBuilderAddsFourOhFiveStatusCodeIfRequestTypeNotRegistered() {
        response = responseBuilder.constructResponse("POST", "/hello");
        assertEquals("405", "" + response.getStatusCode());
    }

    @Test
    public void responseContainsOnlyHeadersIfRequestTypeIsHEAD() throws UnsupportedEncodingException {
        response = responseBuilder.constructResponse("HEAD", "/hello");
        assertEquals("text/html; charset=UTF-8", response.getHeader("Content-Type"));
        assertEquals("3816", response.getHeader("Content-Length"));
        assertEquals("5dG/HJKTpVyeM1oNIsE1vg==", response.getHeader("Content-MD5"));
        assertEquals("", response.bodyString());
    }

    @Test
    public void responseBuilderAdds302HeaderIfPOSTPathRegistered() {
        response = responseBuilder.constructResponse("POST", "/form");
        assertEquals("302", "" + response.getStatusCode());
    }
}
