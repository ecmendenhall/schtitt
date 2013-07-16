package com.cmendenhall;

import java.util.HashMap;

public interface WebResource {

    public String mimeType();
    public String url();
    public String contentLength();
    public String checkSum();
    public String stringData();
    public byte[] binaryData();
    public HashMap<String, String> customHeaders();

}
