package com.cmendenhall.tests;

import com.cmendenhall.handlers.PageHandler;
import com.cmendenhall.handlers.RedirectHandler;
import com.cmendenhall.webresources.WebResource;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class RedirectHandlerTest {
    PageHandler redirectHandler = new RedirectHandler("/redirect");

    @Test
    public void redirectHandlerReturnsRedirectOnRender() {
        WebResource redirect = redirectHandler.render(new HashMap<String, String>());
        assertEquals("/redirect", redirect.url());
    }

}
