package com.cmendenhall;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.cmendenhall.Utils.split;

public class ParameterDecoder {
    private HashMap<String, String> replacementCharacters;

    public static String decode(String url) {

        url = url.replaceAll("\\+", " ");
        Pattern hexCodePattern = Pattern.compile("%([\\dA-Fa-f]{2})");
        Matcher matcher = hexCodePattern.matcher(url);

        while (matcher.find()) {
            String match = matcher.group(0);
            String hexCode = matcher.group(1);
            url = url.replace(match, getCharacter(hexCode));
        }

        return url;
    }

    private static String getCharacter(String hexCode) {
        return "" + (char) Integer.parseInt(hexCode, 16);
    }

    public static HashMap<String, String> getParameters(String queryString) {
        queryString = decode(queryString);
        HashMap<String, String> fieldValues = new HashMap<String, String>();
        List<String> parameters = split(queryString, "&");

        for (String parameter : parameters) {
            List<String> values = split(parameter, "=");
            fieldValues.put(values.get(0), values.get(1));
        }

        return fieldValues;
    }
}
