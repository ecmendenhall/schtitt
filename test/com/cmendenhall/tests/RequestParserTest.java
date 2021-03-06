package com.cmendenhall.tests;

import com.cmendenhall.Request;
import com.cmendenhall.RequestParser;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class RequestParserTest {
    private RequestParser requestParser;
    private Request request;

    @Before
    public void setUp() {
        requestParser = new RequestParser();
    }

    @Test
    public void requestParserShouldParseRequestMethod() {
        request = requestParser.makeRequest("GET / HTTP/1.0\r\n\r\n");
        assertEquals("GET", request.method());
    }

    @Test
    public void requestParserShouldParseRequestPath() {
        request = requestParser.makeRequest("GET / HTTP/1.0" +
                                      "\r\n\r\n");
        assertEquals("/", request.path());
    }

    @Test
    public void requestParserShouldParseRequestHTTPVersion() {
        request = requestParser.makeRequest("GET / HTTP/1.0" +
                                      "\r\n\r\n");
        assertEquals("HTTP/1.0", request.httpVersion());
    }

    @Test
    public void requestParserShouldIgnoreSpacesinFirstLine() {
        request = requestParser.makeRequest("GET     /        HTTP/1.0" +
                                      "\r\n\r\n");
        assertEquals("GET", request.method());
        assertEquals("/", request.path());
        assertEquals("HTTP/1.0", request.httpVersion());
    }

    @Test
    public void requestParserShouldParseHeaders() {
        request = requestParser.makeRequest("GET / HTTP/1.0" + "\r\n" +
                                      "Accept:text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\n" +
                                      "Accept-Encoding:gzip,deflate,sdch\n" +
                                      "Accept-Language:en-US,en;q=0.8,tr;q=0.6\n" +
                                      "Cache-Control:no-cache\n" +
                                      "Connection:keep-alive\n" +
                                      "DNT:1\n" +
                                      "Host:ecmendenhall.github.io\n" +
                                      "Pragma:no-cache\n" +
                                      "User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.110 Safari/537.36" +
                                      "\r\n\r\n");

        List<String> accept = Arrays.asList("text/html", "application/xhtml+xml", "application/xml;q=0.9", "*/*;q=0.8");
        List<String> acceptEncoding = Arrays.asList("gzip", "deflate", "sdch");
        List<String> acceptLanguage = Arrays.asList("en-US", "en;q=0.8", "tr;q=0.6");
        List<String> cacheControl = Arrays.asList("no-cache");
        List<String> connection = Arrays.asList("keep-alive");
        List<String> dnt = Arrays.asList("1");
        List<String> host = Arrays.asList("ecmendenhall.github.io");
        List<String> pragma = Arrays.asList("no-cache");

        assertEquals(accept,         request.getHeader("Accept"));
        assertEquals(acceptEncoding, request.getHeader("Accept-Encoding"));
        assertEquals(acceptLanguage, request.getHeader("Accept-Language"));
        assertEquals(cacheControl,   request.getHeader("Cache-Control"));
        assertEquals(connection,     request.getHeader("Connection"));
        assertEquals(dnt,            request.getHeader("DNT"));
        assertEquals(host,           request.getHeader("Host"));
        assertEquals(pragma,         request.getHeader("Pragma"));
    }

    @Test
    public void requestParserShouldIgnoreSpacesInHeaders() {
        request = requestParser.makeRequest("GET / HTTP/1.0" +"\r\n" +
                                      "Accept:   text/html,    application/xhtml+xml     ,       application/xml;q=0.9\n" +
                                      "\r\n\r\n");

        List<String> accept = Arrays.asList("text/html", "application/xhtml+xml", "application/xml;q=0.9");
        assertEquals(accept, request.getHeader("Accept"));
    }
    
    @Test
    public void requestParserShouldReadBody() {
        String body = "one=1&two=2&three=3";
        request = requestParser.makeRequest("POST /numbers HTTP/1.0\nAccept: text/html\r\n\r\n" + body + "\r\n\r\n");
        assertEquals(body, request.body());
    }

    @Test
    public void requestParserShouldStorePOSTParameters() {
        String body = "one=1&two=2&three=3";
        request = requestParser.makeRequest("POST /numbers HTTP/1.0\nAccept: text/html\r\n\r\n" + body + "\r\n\r\n");
        assertEquals("1", request.getParameter("one"));
        assertEquals("2", request.getParameter("two"));
        assertEquals("3", request.getParameter("three"));
    }

    @Test
    public void requestParserShouldParseQueryStrings() {
        request = requestParser.makeRequest("GET /?query=hovercraft+eels HTTP/1.0\n\n\n");
        assertEquals("/", request.path());
        assertEquals("hovercraft eels", request.getParameter("query"));
    }
}
