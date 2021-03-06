package com.cmendenhall.webresources;

import java.util.HashMap;

public interface WebResource {

    public String mimeType();
    public String url();
    public String contentLength();
    public String checkSum();
    public String stringData();
    public byte[] binaryData();
    public HashMap<String, String> customHeaders();
    public void addCustomHeader(String header, String content);

}
