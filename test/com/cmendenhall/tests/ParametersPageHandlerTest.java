package com.cmendenhall.tests;

import com.cmendenhall.PageHandler;
import com.cmendenhall.ParametersPageHandler;
import com.cmendenhall.WebResource;
import org.junit.Test;

import java.util.HashMap;

import static junit.framework.Assert.assertTrue;

public class ParametersPageHandlerTest {

    @Test
    public void parametersPageHandlerRendersParametersTemplate() {
        PageHandler parametersPageHandler = new ParametersPageHandler();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("variable_1", "hovercraft");
        params.put("variable_2", "eels");
        WebResource rendered = parametersPageHandler.render(params);

        assertTrue(rendered.stringData().contains("hovercraft"));
        assertTrue(rendered.stringData().contains("eels"));
    }

}
