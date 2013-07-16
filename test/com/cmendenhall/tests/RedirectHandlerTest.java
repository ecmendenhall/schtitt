package com.cmendenhall.tests;

import com.cmendenhall.PageHandler;
import com.cmendenhall.RedirectHandler;
import com.cmendenhall.WebResource;
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
