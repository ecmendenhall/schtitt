package com.cmendenhall.tests;

import com.cmendenhall.NotFoundHandler;
import com.cmendenhall.WebResource;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;

import static junit.framework.Assert.assertTrue;

public class NotFoundHandlerTest {
    NotFoundHandler handler;
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
        handler = new NotFoundHandler();
        params = new HashMap<String, String>();
        String styleTag = readFile("resources/style.css");
        params.put("stylesheet", styleTag);
        params.put("rootdirectory", "/hovercraft");
        params.put("filepath", "/full/of/eels/");
    }

    @Test
    public void notFoundHandlerShouldRenderFourOhFourPage() {
        WebResource notFoundPage = handler.render(params);
        String notFoundPageContent = notFoundPage.stringData();
        assertTrue(notFoundPageContent.contains("404: File not found"));
    }

}
