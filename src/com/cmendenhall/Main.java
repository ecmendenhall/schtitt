package com.cmendenhall;

public class Main {
    private static ArgumentParser argumentParser = new ArgumentParser();
    private static String rootDirectory = null;
    private static Integer port = 0;
    private static MessageLogger logger = new MessageLogger();

    public static void main(String[] args) {
        logger.printStartupMessage();
        loadConfig(args);

        HTTPServerSocket socket = getSocket();
        RequestListener requestListener = new RequestListener(socket);
        requestListener.listen();
    }

    public static void loadConfig(String[] args) {
        argumentParser.loadArgs(args);
        rootDirectory = argumentParser.getArg("-d");
        setRootDirectory();
        try {
            port = Integer.parseInt(argumentParser.getArg("-p"));
        } catch (NumberFormatException e) {}
    }

    public static void setRootDirectory() {
        if (rootDirectory != null) {
            System.setProperty("user.dir", rootDirectory);
            logger.log("Serving files from " + System.getProperty("user.dir"));
        } else {
            logger.log("Serving files from this folder.");
        }
    }

    public static HTTPServerSocket getSocket() {
        return (port > 0) ? new HTTPServerSocket(port) : new HTTPServerSocket();
    }

    public static Integer getPort() {
        return port;
    }

    public static String getRootDirectory() {
        return rootDirectory;
    }

}
