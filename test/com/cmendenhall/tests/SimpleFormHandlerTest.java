package com.cmendenhall.tests;

import com.cmendenhall.handlers.PageHandler;
import com.cmendenhall.handlers.SimpleFormHandler;
import com.cmendenhall.webresources.WebResource;
import org.junit.Test;

import java.util.HashMap;

import static junit.framework.Assert.assertEquals;

public class SimpleFormHandlerTest {
    PageHandler getHandler = new SimpleFormHandler("GET");
    PageHandler postHandler = new SimpleFormHandler("POST");

    @Test
    public void getHandlerReturnsForm() {
        WebResource form = getHandler.render(new HashMap<String, String>());
        assertEquals("data = ", form.stringData());
    }

    @Test
    public void postHandlerSavesParameterDataAndReturnsForm() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("data", "cosby");
        WebResource form = postHandler.render(params);
        assertEquals("data = cosby", form.stringData());
    }

}
