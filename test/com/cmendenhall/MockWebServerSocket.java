package com.cmendenhall;

import java.io.IOException;

public class MockWebServerSocket extends WebServerSocket {
    public String writtenString;
    public byte[] writtenBytes;

    public MockWebServerSocket() {

    }

    public MockWebServerSocket(Integer port) {
        super(port);
    }

    @Override
    public void write(String string) {
        writtenString = string;
    }

    @Override
    public void write(byte[] bytes) {
        writtenBytes = bytes;
    }

    @Override
    public void close() {

    }

    @Override
    public void listen() throws IOException {
    }

}
