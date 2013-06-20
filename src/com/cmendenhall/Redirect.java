package com.cmendenhall;

public class Redirect implements WebResource {
    private String url;

    public Redirect(String redirectUrl) {
        url = redirectUrl;
    }

    public String mimeType() {
        return null;
    }

    public String url() {
        return url;
    }

    public String contentLength() {
        return null;
    }

    public String checkSum() {
        return null;
    }

    public String stringData() {
        return "";
    }

    public byte[] binaryData() {
        return new byte[0];
    }
}
