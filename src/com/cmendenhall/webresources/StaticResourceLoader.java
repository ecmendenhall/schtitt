package com.cmendenhall.webresources;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StaticResourceLoader {

    public String loadResourceFromStream(String resource) {
        ClassLoader loader = getClass().getClassLoader();
        InputStream resourceContentStream = loader.getResourceAsStream(resource);
        InputStreamReader reader = new InputStreamReader(resourceContentStream);
        StringBuilder resourceContent = new StringBuilder();
        int currentChar;
        try {
            while ((currentChar = reader.read()) != -1) {
                resourceContent.append((char) currentChar);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return resourceContent.toString();
    }

}
