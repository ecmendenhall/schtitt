package com.cmendenhall;

import java.io.IOException;
import java.net.Socket;

public class MockClientSocket extends HTTPClientSocket {
    public byte[] writtenBytes;

    public MockClientSocket(Socket unused) throws IOException {
        super(unused);
    }

    @Override
    public void write(byte[] bytes) {
        writtenBytes = bytes;
    }

    @Override
    public void close() {

    }

}
