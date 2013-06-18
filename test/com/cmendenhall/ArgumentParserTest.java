package com.cmendenhall;

import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import static junit.framework.Assert.assertEquals;

public class ArgumentParserTest {
    private ArgumentParser argumentParser;
    private OutputRecorder recorder;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        argumentParser = new ArgumentParser();
        recorder = new OutputRecorder();
    }

    @Test
    public void mainShouldParseFourCommandLineArgs() throws Exception {
        HashMap<String, String> args = argumentParser.parseArgs(new String[] {"-p", "64000", "-d", "~/Desktop"});
        assertEquals("64000", args.get("-p"));
        assertEquals("~/Desktop", args.get("-d"));
    }

    @Test
    public void mainShouldParseTwoCommandLineArgs() throws Exception {
        HashMap<String, String> args = argumentParser.parseArgs(new String[] {"-p", "64000"});
        assertEquals("64000", args.get("-p"));

        args = argumentParser.parseArgs(new String[] {"-d", "~/Desktop"});
        assertEquals("~/Desktop", args.get("-d"));
    }

    @Test
    public void parseArgsReturnsUsageDataIfArgsAreInvalid() throws Exception {
        String[] args = new String[] {"-lol", "wat", "-r", "oflcopter", "12345"};
        try {
            argumentParser.parseArgs(args);
        } catch (Exception e) {
            String errorMessage = recorder.popLastOutput();
            assertEquals("Usage: java -jar <your jar file> -p <port> -d <directory to serve>", errorMessage);
        }
    }

}
