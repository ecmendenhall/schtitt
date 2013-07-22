package com.cmendenhall.tests;

import com.cmendenhall.handlers.FormHandler;
import com.cmendenhall.KeyValueStore;
import com.cmendenhall.webresources.WebResource;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class FormHandlerTest {
    FormHandler getHandler;
    FormHandler postHandler;
    HashMap<String, String> params;

    private String readFile(String path) throws FileNotFoundException {
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder fileString = new StringBuilder();
        String currentLine;
        try {
            while ((currentLine = reader.readLine()) != null) {
                fileString.append(currentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return fileString.toString();
    }

    @Before
    public void setUp() throws FileNotFoundException {
        getHandler = new FormHandler();
        postHandler = new FormHandler("POST");
        params = new HashMap<String, String>();
        String styleTag = readFile("resources/style.css");
        params.put("stylesheet", styleTag);
        params.put("hovercraft", "eels");
    }

    @Test
    public void formHandlerShouldRenderFormPage() {
        WebResource formPage = getHandler.render(params);
        assertTrue(formPage.stringData().contains("Listerously.io"));
    }

    @Test
    public void formHandlerReturnsRedirectOnPOST() {
        WebResource redirect = postHandler.render(params);
        assertEquals("/list", redirect.url());
    }

    @Test
    public void formHandlerStoresPOSTData() {
        WebResource redirect = postHandler.render(params);
        assertEquals("eels", KeyValueStore.get("hovercraft"));
    }

}
