package com.cmendenhall.tests;

import com.cmendenhall.HelloPageHandler;
import com.cmendenhall.WebResource;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;

import static junit.framework.Assert.assertTrue;

public class HelloPageHandlerTest {
    HelloPageHandler handler;
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
        handler = new HelloPageHandler();
        params = new HashMap<String, String>();
        String styleTag = readFile("resources/style.css");
        params.put("stylesheet", styleTag);
    }

    @Test
    public void helloHandlerShouldRenderHelloPage() {
        WebResource helloPage = handler.render(params);
        String helloPageContent = helloPage.stringData();
        assertTrue(helloPageContent.contains("Schtitt 0.9a"));
    }
}
