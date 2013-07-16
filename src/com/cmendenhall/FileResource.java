package com.cmendenhall;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class FileResource implements WebResource {
    private HashMap<String, String> mimeTypePrefixes;
    private String mimeType;
    private String path;
    private File file;
    private HashMap<String, String> customHeaders = new HashMap<String, String>();

    public FileResource(String filePath) {
        file = FileManager.getFile(filePath);
        path = file.getPath();

        String fileType = getFileType(filePath);

        loadMimeTypes();
        mimeType = makeMimeType(fileType);
    }

    private void loadMimeTypes() {
        mimeTypePrefixes = new HashMap<String, String>();

        mimeTypePrefixes.put("png", "image");
        mimeTypePrefixes.put("jpg", "image");
        mimeTypePrefixes.put("gif", "image");

        mimeTypePrefixes.put("htm", "text");
        mimeTypePrefixes.put("html", "text");
        mimeTypePrefixes.put("txt", "text");
        mimeTypePrefixes.put("css", "text");

        mimeTypePrefixes.put("pdf", "application");
    }

    private String getFileType(String filePath) {
        if (filePath.contains(".")) {
            String[] splitPath = filePath.split("\\.");
            return splitPath[1];
        } else {
            return "";
        }
    }

    private String makeMimeType(String fileExtension) {
        String prefix = mimeTypePrefixes.get(fileExtension);
        if (prefix == null) {
            return "application/octet-stream";
        } else if (prefix == "text") {
            return prefix + "/" + fileExtension + "; charset=UTF-8";
        } else {
            return prefix + "/" + fileExtension;
        }
    }

    public String mimeType() {
        return mimeType;
    }

    public String url() {
        return path;
    }

    public String contentLength() {
        return "" + this.file.length();
    }

    public String checkSum() {
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(binaryData());
            return Base64.encode(digest);
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }

    public String stringData() {
        StringBuilder fileString = new StringBuilder();
        try {
            FileReader reader = new FileReader(path);
            int currentChar;
            while((currentChar = reader.read()) != -1) {
                fileString.append((char)currentChar);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileString.toString();
    }

    public byte[] binaryData() {
        FileInputStream byteStream;
        byte[] bytes = new byte[(int) this.file.length()];
        try {
            byteStream = new FileInputStream(path);
            int currentByte;
            int byteIndex = 0;
            while((currentByte = byteStream.read()) != -1) {
                bytes[byteIndex] = (byte) currentByte;
                byteIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public HashMap<String, String> customHeaders() {
        return customHeaders;
    }
}
