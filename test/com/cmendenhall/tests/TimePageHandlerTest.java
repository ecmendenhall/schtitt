package com.cmendenhall.tests;

import com.cmendenhall.TimePageHandler;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static junit.framework.Assert.assertTrue;

public class TimePageHandlerTest {
    TimePageHandler handler;
    HashMap<String, String> params;
    Pattern timePattern;

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
        handler = new TimePageHandler();
        params = new HashMap<String, String>();
        String stylesheet = readFile("resources/style.css");
        params.put("stylesheet", stylesheet);
        timePattern = Pattern.compile("\\d{2}:\\d{2}:\\d{2} [A-Z]{3}");
    }

    @Test
    public void timePageHandlerRendersPageWithDynamicTime() throws InterruptedException {
    String now = handler.render(params).stringData();
    Matcher timeMatcher = timePattern.matcher(now);
    assertTrue(timeMatcher.find());
    }

}
