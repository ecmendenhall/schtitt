package com.cmendenhall;

import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static junit.framework.Assert.assertEquals;

public class ResponseTest {
    private Response response;
    private String samplePage;

    @Before
    public void setUp() {
        response = new Response();
        samplePage = "<html>" +
                       "<head>" +
                       "</head>" +
                       "<body>" +
                         "<p>Junior athletics is but one facet of the real gem:" +
                            "life's endless war against the self you cannot live without." +
                         "</p>" +
                       "</body>" +
                     "</html>";
    }

    @Test
    public void responseHttpVersionShouldBeConfigurable() {
        response.httpVersion("1.0");
        String version = response.getHttpVersion();
        assertEquals("HTTP/1.0", version);
    }

    @Test
    public void responseStatusCodeShouldBeConfigurable() {
        response.statusCode("200");
        Integer statusCode = response.getStatusCode();
        assertEquals((Integer)200, statusCode);
    }

    @Test
    public void settingStatusCodeShouldSetReasonPhrase() {
        response.statusCode("200");
        String reasonPhrase = response.getReasonPhrase();
        assertEquals("OK", reasonPhrase);
    }

    @Test
    public void headersShouldBeConfigurable() {
        response.setHeader("Server", "Schtitt/0.9b");
        String server = response.getHeader("Server");
        assertEquals("Schtitt/0.9b", server);
    }

    @Test
    public void bodyContentShouldBeConfigurable() throws UnsupportedEncodingException {
        response.body(samplePage);
        String body = response.bodyString();
        assertEquals(samplePage, body);
    }

    @Test
    public void bodyContentShouldAcceptByteArray() throws UnsupportedEncodingException {
        response.body(samplePage.getBytes("UTF-8"));
        String reconstructedBody = response.bodyString();
        assertEquals(samplePage, reconstructedBody);
    }

    @Test
    public void responseReturnsCorrectByteArray() throws UnsupportedEncodingException {
        response.httpVersion("1.0");
        response.statusCode("200");
        response.setHeader("Server", "Schtitt/0.9b");
        response.body(samplePage);
        String expected = "HTTP/1.0 200 OK" + "\r\n" + "Server: Schtitt/0.9b" + "\r\n\r\n" + samplePage + "\r\n\r\n";
        byte[] responseBytes = response.toBytes();
        String reconstructedResponse = new String(responseBytes, "UTF-8");
        assertEquals(expected, reconstructedResponse);
    }

}
