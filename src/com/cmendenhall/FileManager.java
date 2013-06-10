package com.cmendenhall;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FileManager {

    public List<String> listDirectory(String path) {
        File filePath = new File(path);
        return Arrays.asList(filePath.list());
    }

    public String read(String path) {
        String fileString = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while((line = reader.readLine()) != null) {
               fileString += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileString;
    }

}
