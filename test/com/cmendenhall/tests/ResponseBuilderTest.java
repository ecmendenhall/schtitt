package com.cmendenhall.tests;

import com.cmendenhall.Response;
import com.cmendenhall.ResponseBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

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
        assertEquals("HTTP/1.0", response.getHttpVersion());
        assertEquals("Schtitt/0.9a", response.getHeader("Server"));
        assertEquals("no-cache", response.getHeader("Pragma"));
    }

    @Test
    public void responseBuilderAddsBodyDependentHeaders() {
        assertEquals("text/html; charset=UTF-8", response.getHeader("Content-Type"));
        assertEquals("3847", response.getHeader("Content-Length"));
        assertEquals("xciI8muXKqfH2eG+5FMJhw==", response.getHeader("Content-MD5"));
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
        assertEquals("3847", response.getHeader("Content-Length"));
        assertEquals("xciI8muXKqfH2eG+5FMJhw==", response.getHeader("Content-MD5"));
        assertEquals("", response.bodyString());
    }

    @Test
    public void responseBuilderAdds200HeaderIfPOSTPathRegistered() {
        // Although it really should add a 302 header!
        response = responseBuilder.constructResponse("POST", "/form");
        assertEquals("200", "" + response.getStatusCode());
    }

    @Test
    public void responseBuilderConstructsPartialContentResponses() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("Range", "bytes=0-4");
        response = responseBuilder.constructResponse("GET", "/", params);
        assertEquals("4", response.getHeader("Content-Length"));
    }

    @Test
    public void responseBuilderReturns301OnRedirect() {
        response = responseBuilder.constructResponse("GET", "/redirect");
        assertEquals("301", "" + response.getStatusCode());
    }
}
