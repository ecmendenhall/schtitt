package com.cmendenhall.tests;

import com.cmendenhall.HTTPServerSocket;
import com.cmendenhall.Main;
import com.cmendenhall.logging.MessageQueue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class MainTest {
    private MessageQueue messageQueue = MessageQueue.getInstance();
    private String rootDirectory = System.getProperty("user.dir");

    @Before
    public void setUp() {
        messageQueue.clear();
    }

    @Test
    public void loadArgsSetsPort() throws Exception {
        Main.loadConfig(new String[]{"-p", "64000"});
        Integer port = Main.getPort();
        assertEquals(port, (Integer)64000);
    }

    @Test
    public void loadArgsSetsRootDirectory() throws Exception {
        Main.loadConfig(new String[] {"-d", "~/Desktop"});
        String directory = Main.getRootDirectory();
        assertEquals(directory, "~/Desktop");
    }

    @Test
    public void setRootDirectorySetsDirectoryIfPresent() throws Exception {
        Main.loadConfig(new String[]{"-d", "~/Desktop"});
        Main.setRootDirectory();
        String directory = System.getProperty("user.dir");
        assertEquals("~/Desktop", directory);
        assertTrue(messageQueue.nextMessage().contains("Serving files from ~/Desktop"));
    }

    @Test
    public void setRootDirectoryDoesNotChangeDirectoryIfNotProvided() throws Exception {
        Main.loadConfig(new String[] {"-p", "64000"});
        String expected = System.getProperty("user.dir");
        Main.setRootDirectory();
        String directory = System.getProperty("user.dir");
        assertEquals(expected, directory);
        assertTrue(messageQueue.nextMessage().contains("Serving files from this folder."));
    }

    @Test
    public void getSocketReturnsSocketWithSpecifiedPort() throws Exception {
        Main.loadConfig(new String[] {"-p", "64000"});
        HTTPServerSocket socket = Main.getSocket();
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
        messageQueue.clear();
        Main.stopPrinter();
    }

}
