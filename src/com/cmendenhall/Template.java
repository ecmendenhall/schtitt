package com.cmendenhall;

import java.util.HashMap;

public final class Template {
    private static StaticResourceLoader loader = new StaticResourceLoader();

    public static Page render(String templateFile, HashMap<String, String> pageParameters) {
        String template = loader.loadResource(templateFile);

        for (String param : pageParameters.keySet()) {
            String replacement = pageParameters.get(param);
            replacement = (replacement == null) ? "" : replacement;
            template = template.replace("{{% " + param + " %}}", replacement);
        }

        return new Page(template, pageParameters.get("path"));
    }

}
