package com.cmendenhall.webresources;

import java.util.HashMap;

public class Redirect implements WebResource {
    private String url;
    private HashMap<String, String> customHeaders;

    public Redirect(String redirectUrl) {
        url = redirectUrl;
        customHeaders = new HashMap<String, String>();
        customHeaders.put("Location", url);
        customHeaders.put("Refresh", "0; url=" + url);
    }

    public String mimeType() {
        return "text/html";
    }

    public String url() {
        return url;
    }

    public String contentLength() {
        return "0";
    }

    public String checkSum() {
        return "";
    }

    public String stringData() {
        return "";
    }

    public byte[] binaryData() {
        return new byte[0];
    }

    public HashMap<String, String> customHeaders() {
        return customHeaders;
    }

    public void addCustomHeader(String header, String content) {
        customHeaders.put(header, content);
    }
}
