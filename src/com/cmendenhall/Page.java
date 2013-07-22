package com.cmendenhall;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class Page implements WebResource {
    private String pageContent;
    private String url;
    private HashMap<String, String> customHeaders = new HashMap<String, String>();

    public Page(String content, String path) {
        url = path;
        pageContent = content;
    }

    public String mimeType() {
        return "text/html; charset=UTF-8";
    }

    public String url() {
        return url;
    }

    public String contentLength() {
        return "" + binaryData().length;
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
        return pageContent;
    }

    public byte[] binaryData() {
        try {
            return pageContent.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    public HashMap<String, String> customHeaders() {
        return customHeaders;
    }

    public void addCustomHeader(String header, String content) {
        customHeaders.put(header, content);
    }
}
