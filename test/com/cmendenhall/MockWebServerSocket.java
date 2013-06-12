package com.cmendenhall;

public class MockWebServerSocket extends WebServerSocket {
    public String writtenString;
    public byte[] writtenBytes;

    @Override
    public void write(String string) {
        writtenString = string;
    }

    @Override
    public void write(byte[] bytes) {
        writtenBytes = bytes;
    }

}
