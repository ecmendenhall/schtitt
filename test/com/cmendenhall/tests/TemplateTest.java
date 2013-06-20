package com.cmendenhall.tests;

import com.cmendenhall.Page;
import com.cmendenhall.Template;
import org.junit.Test;

import java.util.HashMap;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class TemplateTest {

    @Test
    public void templateReplacesParametersOnRender() {
    HashMap<String, String> pageParams = new HashMap<String, String>();
    pageParams.put("stylesheet", "");
    pageParams.put("path", "/list");
    pageParams.put("field1", "Gruyère");
    pageParams.put("field2", "Roquefort");
    pageParams.put("field3", "The flyest cave-aged cheese for sheez");

    Page rendered = Template.render("list.html", pageParams);
    String renderedText = rendered.stringData();

    assertTrue(renderedText.contains("Gruyère"));
    assertTrue(renderedText.contains("Roquefort"));
    assertFalse(renderedText.contains("{{% itemThree %}}"));
    }
}
