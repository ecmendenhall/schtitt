package com.cmendenhall;

import java.util.HashMap;

public class Sanitizer {
    private HashMap<String, String> escapeCharacters;

    public Sanitizer() {
        escapeCharacters = new HashMap<String, String>();
        escapeCharacters.put("<", "&lt;");
        escapeCharacters.put(">", "&gt;");
        escapeCharacters.put("`", "&#96;");
        escapeCharacters.put("!", "&#33;");
        escapeCharacters.put("@", "&#64;");
        escapeCharacters.put("%", "&#37;");
        escapeCharacters.put("=", "&#61;");
        escapeCharacters.put("'", "&apos;");
        escapeCharacters.put("\"", "&quot;");
        escapeCharacters.put("\\(", "&#40;");
        escapeCharacters.put("\\)", "&#41;");
        escapeCharacters.put("\\+", "&#43;");
        escapeCharacters.put("\\{", "&#123;");
        escapeCharacters.put("\\}", "&#125;");
        escapeCharacters.put("\\[", "&#91;");
        escapeCharacters.put("\\]", "&#93;");
        escapeCharacters.put("\\$", "&#36;");
    }

    public String escape(String toEscape) {

        for (String character : escapeCharacters.keySet()) {
            toEscape = toEscape.replaceAll(character, escapeCharacters.get(character));
        }

        return toEscape;
    }
}
