package com.cmendenhall.handlers;

import com.cmendenhall.webresources.FileResource;
import com.cmendenhall.webresources.Template;
import com.cmendenhall.webresources.WebResource;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.util.HashMap;
import java.util.List;

import static com.cmendenhall.Utils.split;

public class ServerLogHandler implements PageHandler {

    public boolean validCredentials(String auth) {
        try {
            if (auth != null) {
                String encodedAuth = split(auth, " ").get(1);
                String decodedAuth = new String(Base64.decode(encodedAuth));
                List<String> credentials = split(decodedAuth, ":");

                String username = credentials.get(0);
                String password = credentials.get(1);

                if (username.equals("admin") && password.equals("hunter2")) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public WebResource render(HashMap<String, String> params) {

        String credentials = params.get("Authorization");
        if (validCredentials(credentials)) {
            FileResource logs = new FileResource("logs.txt");
            return logs;
        } else {
            WebResource page = Template.render("authenticate.html", params);
            page.addCustomHeader("WWW-Authenticate", "Basic realm='Server logs'");
            return page;
        }
    }

}