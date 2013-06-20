package com.cmendenhall.tests;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MockSocket extends Socket {

    @Override
    public OutputStream getOutputStream() {
        return new ByteArrayOutputStream();
    }
}
