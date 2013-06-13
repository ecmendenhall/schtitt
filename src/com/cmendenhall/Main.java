package com.cmendenhall;

import java.util.HashMap;

public class Main {
    private static MessageLogger logger = new MessageLogger();
    private static String rootDirectory = null;
    private static Integer port = 0;

    public static void main(String[] args) {
        loadArgs(args);
        printStartupMessage();
        setRootDirectory();

        WebServerSocket socket = getSocket();
        RequestListener requestListener = new RequestListener(socket);
        requestListener.listen();
    }

    public static void printStartupMessage() {
        System.out.println("Schtitt 0.9a");
        logger.log("Press c-C to exit.");
    }

    public static void setRootDirectory() {
        if (rootDirectory != null) {
            System.setProperty("user.dir", rootDirectory);
            logger.log("Serving files from " + System.getProperty("user.dir"));
        } else {
            logger.log("Serving files from this folder.");
        }
    }

    public static void loadArgs(String[] args) {
        if (args.length > 0) {
            HashMap<String, String> commandLineArgs = parseArgs(args);
            try {
                port = Integer.parseInt(commandLineArgs.get("-p"));
            } catch (NumberFormatException e) {}
            rootDirectory = commandLineArgs.get("-d");
        }
    }

    public static HashMap<String, String> parseArgs(String[] args) {
        HashMap<String, String> commandLineArgs = new HashMap<String, String>();
        if (args.length == 4) {
            commandLineArgs.put(args[0], args[1]);
            commandLineArgs.put(args[2], args[3]);
        } else if (args.length == 2) {
            commandLineArgs.put(args[0], args[1]);
        } else {
            System.err.println("Usage: java -jar <your jar file> -p <port> -d <directory to serve>");
        }
        return commandLineArgs;
    }

    public static WebServerSocket getSocket() {
        return (port > 0) ? new WebServerSocket(port) : new WebServerSocket();
    }

    public static Integer getPort() {
        return port;
    }

    public static String getRootDirectory() {
        return rootDirectory;
    }

}
