package com.cmendenhall.tests;

import com.cmendenhall.handlers.ServerLogHandler;
import com.cmendenhall.webresources.WebResource;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ServerLogHandlerTest {
    private ServerLogHandler logHandler = new ServerLogHandler();

    @Test
    public void serverLogHandlerChecksUsernameAndPassword() {
        assertFalse(logHandler.validCredentials("Basic FaKeBase64EnCodEd=="));
        assertTrue(logHandler.validCredentials("Basic YWRtaW46aHVudGVyMg=="));
    }

    @Test
    public void serverLogHandlerReturnsUnauthorizedPageIfUserUnauthorized() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("Authorization", "Basic FaKeBase64EnCodEd==");
        WebResource page = logHandler.render(params);
        page.stringData().contains("Authentication required");
    }

    @Test
    public void serverLogHandlerReturnsLogsIfUserAuthorized() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("Authorization", "Basic YWRtaW46aHVudGVyMg==");
        WebResource page = logHandler.render(params);
        page.stringData().contains("Schtitt 0.9a");
    }
}
