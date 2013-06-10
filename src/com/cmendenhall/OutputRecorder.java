package com.cmendenhall;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Deque;
import java.util.LinkedList;

public class OutputRecorder extends PrintStream {
    private Deque<String> outputStack;

    public OutputRecorder() throws UnsupportedEncodingException {
        super(new ByteArrayOutputStream(), true, "UTF-8");
        outputStack = new LinkedList<String>();
    }

    private void catchOutput(String output) {
        outputStack.addFirst(output);
    }

    public String popLastOutput() {
        return outputStack.removeFirst();
    }

    public String popFirstOutput() {
        return outputStack.removeLast();
    }


    public String peekLastOutput() {
        return outputStack.peekFirst();
    }

    public String peekFirstOutput() {
        return outputStack.peekLast();
    }

    public void discardLastNStrings(int n) {
        for (int i=0; i < n; i++) {
            popLastOutput();
        }
    }

    public void discardFirstNStrings(int n) {
        for (int i=0; i < n; i++) {
            popFirstOutput();
        }
    }

    public void start() {
        System.setOut(this);
        System.setErr(this);
    }

    @Override
    public void println(String output) {
        catchOutput(output);
    }

    @Override
    public void print(String output) {
        catchOutput(output);
    }

}