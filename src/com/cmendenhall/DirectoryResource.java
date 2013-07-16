package com.cmendenhall;


import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.cmendenhall.Utils.join;

public class DirectoryResource implements WebResource {
    private String mimeType;
    private String path;
    private File directory;
    private HashMap<String, String> customHeaders = new HashMap<String, String>();

    public DirectoryResource(String filePath) {
        directory = FileManager.getFile(filePath);
        mimeType = "text/html; charset=UTF-8";
        path = directory.getPath();
    }

    public String mimeType() {
        return mimeType;
    }

    public String url() {
        return path;
    }

    public String contentLength() {
        return "" + stringData().length();
    }

    public String checkSum() {
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(binaryData());
            return Base64.encode(digest);
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }

    private String makePage() {
        String template = makeTemplate();
        HashMap<String, String> pageParameters = new HashMap<String, String>();
        pageParameters.put("stylesheet", StaticResourceCache.loadResource("style.css"));
        pageParameters.put("directorypath", directory.getPath());
        return renderPage(template, pageParameters);
    }

    private String makeTemplate() {

        String top    = join("\n", "<html>",
                                   "  <head>",
                                   "    <title>Directory Listing</title>",
                                   "    <style>",
                                   "      {{% stylesheet %}}",
                                   "    </style>",
                                   "  </head>",
                                   "  <body>",
                                   "  <h1 class=\"directoryheader\">Directory listing</h1>",
                                   "  <h2 class=\"directoryheader\">{{% directorypath %}}</h2>",
                                   "    <ul>");

        String middle = makeIndexEntries();

        String bottom = join("\n",

                                   "    </ul>",
                                   "  </body>",
                                   "</html>");

        return join("\n", top, middle, bottom);
    }

    private String makeIndexEntries() {
        File[] files = directory.listFiles();
        List<String> listEntries = new ArrayList<String>();

        if (path != System.getProperty("user.dir")) listEntries.add(makeEntry("../", "[ .. ]", ""));
        for(File file : files) {
            String fileName = (file.isDirectory()) ? file.getName() + "/" : file.getName();
            String filePath = (file.isDirectory()) ? file.getName() + "/" : file.getName();
            String className = (file.isDirectory()) ? "folder" : "file";
            listEntries.add(makeEntry(filePath, fileName, className));
        }

        return join("\n", listEntries);
    }

    private String makeEntry(String filePath, String fileName, String className) {
        MessageFormat entryElement = new MessageFormat("      <li class=\"{0}\"><a href=\"{1}\">{2}</a></li>");
        String[] name = { className, filePath, fileName };
        return entryElement.format(name);
    }

    public String renderPage(String page, HashMap<String, String> pageParameters) {

        for (String param : pageParameters.keySet()) {
            String replacement = pageParameters.get(param);
            page = page.replace("{{% " + param + " %}}", replacement);
        }

        return page;
    }

    public String stringData() {
        return makePage();
    }

    public byte[] binaryData() {
        try {
            return makePage().getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    @Override
    public HashMap<String, String> customHeaders() {
        return customHeaders;
    }
}
