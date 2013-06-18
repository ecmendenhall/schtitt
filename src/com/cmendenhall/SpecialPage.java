package com.cmendenhall;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class SpecialPage implements WebResource {
    private String pageContent;
    private StaticResourceLoader loader;
    private String url;
    private HashMap<String, String> pageParameters;

    public SpecialPage(String pageResource, String path) {
        loader = new StaticResourceLoader();
        url = path;
        pageParameters = new HashMap<String, String>();
        pageContent = renderPage(pageResource, pageParameters);
    }


    public SpecialPage(String pageResource, String path, HashMap<String, String> params) {
        loader = new StaticResourceLoader();
        url = path;
        pageParameters = params;
        pageContent = renderPage(pageResource, pageParameters);
    }

    public String renderPage(String pageResource, HashMap<String, String> pageParameters) {
        String template = loader.loadResource(pageResource);

        for (String param : pageParameters.keySet()) {
            String replacement = pageParameters.get(param);
            template = template.replace("{{% " + param + " %}}", replacement);
        }

        return template;
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
}
