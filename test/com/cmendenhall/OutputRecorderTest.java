package com.cmendenhall;

import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static junit.framework.Assert.assertEquals;

public class OutputRecorderTest {
    OutputRecorder recorder;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        recorder = new OutputRecorder();
        recorder.start();
        System.out.println("My");
        System.out.println("hovercraft");
        System.out.println("is");
        System.out.print("full");
        System.out.print("of");
        System.out.print("eels");
    }

    @Test
    public void outputRecorderShouldPopLastOutput() {
        String eels = recorder.popLastOutput();
        String of = recorder.popLastOutput();
        assertEquals("eels", eels);
        assertEquals("of", of);
    }

    @Test
    public void outputRecorderShouldPopFirstOutput() {
        String my = recorder.popFirstOutput();
        String hovercraft = recorder.popFirstOutput();
        assertEquals("My", my);
        assertEquals("hovercraft", hovercraft);
    }

    @Test
    public void outputRecorderShouldPeekLastOutput() {
        String eels = recorder.peekLastOutput();
        assertEquals("eels", eels);
    }

    @Test
    public void outputRecorderShouldPeekFirstOutput() {
        String my = recorder.peekFirstOutput();
        assertEquals("My", my);
    }

    @Test
    public void outputRecorderShouldDiscardStringsFromFrontOfDeque() {
        recorder.discardFirstNStrings(3);
        String full = recorder.popFirstOutput();
        assertEquals("full", full);
    }

    @Test
    public void outputRecorderShouldDiscardStringsFromEndOfDeque() {
        recorder.discardLastNStrings(4);
        String hovercraft = recorder.popLastOutput();
        assertEquals("hovercraft", hovercraft);
    }

    @Test
    public void outputRecorderShouldRedirectSystemOutOnStart() {
        PrintStream currentOut = System.out;
        assertEquals(recorder, currentOut);
    }

    @Test
    public void outputRecorderShouldRedirectSystemErrOnStart() {
        PrintStream currentErr = System.err;
        assertEquals(recorder, currentErr);
    }

}
