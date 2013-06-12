package com.cmendenhall;

public interface WebResource {

    public String mimeType();
    public String url();
    public String contentLength();
    public String checkSum();
    public String stringData();
    public byte[] binaryData();

}
