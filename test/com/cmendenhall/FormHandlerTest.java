package com.cmendenhall;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;

import static junit.framework.Assert.assertEquals;

public class FormHandlerTest {
    FormHandler handler;
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
        handler = new FormHandler();
        params = new HashMap<String, String>();
        String styleTag = readFile("resources/style.css");
        params.put("stylesheet", styleTag);
    }

    @Test
    public void formHandlerShouldRenderFormPage() {
        WebResource formPage = handler.render(params);
        assertEquals("yKtNK9X0Y0yRWsZL5XJa2A==", formPage.checkSum());
    }

}
