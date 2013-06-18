package com.cmendenhall;

import java.util.HashMap;

public class ArgumentParser {
    private HashMap<String, String> parsedArgs;

    public void loadArgs(String[] args) {
        parsedArgs = parseArgs(args);
    }

    public String getArg(String argument) {
        return parsedArgs.get(argument);
    }

    public HashMap<String, String> parseArgs(String[] args) {
        HashMap<String, String> commandLineArgs = new HashMap<String, String>();
        if (args.length == 4) {
            commandLineArgs.put(args[0], args[1]);
            commandLineArgs.put(args[2], args[3]);
        } else if (args.length == 2) {
            commandLineArgs.put(args[0], args[1]);
        } else if (args.length != 0) {
            System.err.println("Usage: java -jar <your jar file> -p <port> -d <directory to serve>");
        }
        return commandLineArgs;
    }
}
