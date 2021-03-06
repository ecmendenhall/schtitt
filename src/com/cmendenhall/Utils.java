package com.cmendenhall;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Utils {

    public static List<String> split(String string, String splitAt) {
        return Arrays.asList(string.split(splitAt));
    }

    public static String join(String delimiter, String... strings) {
        return join(delimiter, Arrays.asList(strings));
    }

    public static String join(String delimiter, Collection<String> strings) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : strings) {
            stringBuilder.append(string);
            stringBuilder.append(delimiter);
        }
        String extraDelimiter = stringBuilder.toString();
        Integer delimiterStart = extraDelimiter.length() - delimiter.length();
        return extraDelimiter.substring(0, delimiterStart);
    }

    public static byte[] join(byte[]... arrays) {

        Integer totalLength = 0;
        for (byte[] array : arrays) {
            totalLength += array.length;
        }

        byte[] joinedArray = new byte[totalLength];

        Integer joinedArrayIndex = 0;
        for(byte[] array : arrays) {
            for (Integer i=0; i < array.length; i++) {
                joinedArray[joinedArrayIndex] = array[i];
                joinedArrayIndex++;
            }
        }

        return joinedArray;
    }



}
