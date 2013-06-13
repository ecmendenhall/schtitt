package com.cmendenhall;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class MainTest {
    private OutputRecorder recorder;
    private String rootDirectory = System.getProperty("user.dir");

    @Before
    public void setUp() throws UnsupportedEncodingException {
        recorder = new OutputRecorder();
        recorder.start();
    }

    @Test
    public void mainShouldPrintStartupMessage() {
        Main.printStartupMessage();
        String serverStart = recorder.popFirstOutput();
        String infoMessage = recorder.popFirstOutput();
        assertEquals("Schtitt 0.9a", serverStart);
        assertTrue(infoMessage.contains("Press c-C to exit."));
    }

    @Test
    public void mainShouldParseFourCommandLineArgs() throws Exception {
        HashMap<String, String> args = Main.parseArgs(new String[] {"-p", "64000", "-d", "~/Desktop"});
        assertEquals("64000", args.get("-p"));
        assertEquals("~/Desktop", args.get("-d"));
    }

    @Test
    public void mainShouldParseTwoCommandLineArgs() throws Exception {
        HashMap<String, String> args = Main.parseArgs(new String[] {"-p", "64000"});
        assertEquals("64000", args.get("-p"));

        args = Main.parseArgs(new String[] {"-d", "~/Desktop"});
        assertEquals("~/Desktop", args.get("-d"));
    }

    @Test
    public void parseArgsReturnsUsageDataIfArgsAreInvalid() throws Exception {
        String[] args = new String[] {"-lol", "wat", "-r", "oflcopter", "12345"};
        try {
            Main.parseArgs(args);
        } catch (Exception e) {
            String errorMessage = recorder.popLastOutput();
            assertEquals("Usage: java -jar <your jar file> -p <port> -d <directory to serve>", errorMessage);
        }
    }

    @Test
    public void loadArgsSetsPort() throws Exception {
        Main.loadArgs(new String[] {"-p", "64000"});
        Integer port = Main.getPort();
        assertEquals(port, (Integer)64000);
    }

    @Test
    public void loadArgsSetsRootDirectory() throws Exception {
        Main.loadArgs(new String[] {"-d", "~/Desktop"});
        String directory = Main.getRootDirectory();
        assertEquals(directory, "~/Desktop");
    }

    @Test
    public void setRootDirectorySetsDirectoryIfPresent() throws Exception {
        Main.loadArgs(new String[]{"-d", "~/Desktop"});
        Main.setRootDirectory();
        String directory = System.getProperty("user.dir");
        assertEquals("~/Desktop", directory);
        assertTrue(recorder.popLastOutput().contains("Serving files from ~/Desktop"));
    }

    @Test
    public void setRootDirectoryDoesNotChangeDirectoryIfNotProvided() throws Exception {
        Main.loadArgs(new String[] {"-p", "64000"});
        String expected = System.getProperty("user.dir");
        Main.setRootDirectory();
        String directory = System.getProperty("user.dir");
        assertEquals(expected, directory);
        assertTrue(recorder.popLastOutput().contains("Serving files from this folder."));
    }

    @Test
    public void getSocketReturnsSocketWithSpecifiedPort() throws Exception {
        Main.loadArgs(new String[] {"-p", "64000"});
        WebServerSocket socket = Main.getSocket();
        assertEquals(socket.getPort(), (Integer)64000);
    }

    @Test
    public void mainMethodShouldCreateSocketAndListen() throws Exception {

        Thread listenThread = new Thread(new Runnable() {
            public void run() {
                Main.main(new String[0]);
            }
        });

        listenThread.start();
    }

    @After
    public void cleanUp() {
        System.setProperty("user.dir", rootDirectory);
    }

}
