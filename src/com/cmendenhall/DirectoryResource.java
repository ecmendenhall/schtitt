package com.cmendenhall;


import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static com.cmendenhall.Utils.join;

public class DirectoryResource extends File implements WebResource {
    private String mimeType;
    private String path;

    public DirectoryResource(String filePath) {
        super(filePath);

        mimeType = "text/html; charset=UTF-8";
        path = getPath();
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

        String top    = join("\n", "<html>",
                                   "  <head>",
                                   "  </head>",
                                   "  <body>",
                                   "    <ul>");

        String middle = makeIndexEntries();

        String bottom = join("\n",

                                   "    </ul>",
                                   "  </body>",
                                   "</html>");

        return join("\n", top, middle, bottom);
    }

    private String makeIndexEntries() {
        File[] files = listFiles();
        List<String> listEntries = new ArrayList<String>();

        if (getParent() != null) listEntries.add(makeEntry("/" + getParent() + "/", "[..]"));
        for(File file : files) {
            String fileName = (file.isDirectory()) ? file.getName() + "/" : file.getName();
            String filePath = (file.isDirectory()) ? "/" + file.getPath() + "/" : file.getName();
            listEntries.add(makeEntry(filePath, fileName));
        }

        return join("\n", listEntries);
    }

    private String makeEntry(String filePath, String fileName) {
        MessageFormat entryElement = new MessageFormat("      <li><a href=''{0}''>{1}</a></li>");
        String[] name = { filePath, fileName };
        return entryElement.format(name);
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
}
