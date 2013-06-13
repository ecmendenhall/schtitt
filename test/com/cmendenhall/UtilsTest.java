package com.cmendenhall;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import static com.cmendenhall.Utils.join;
import static com.cmendenhall.Utils.split;
import static junit.framework.Assert.assertEquals;

public class UtilsTest {
    private String fullOfEels = "My hovercraft is full of eels";

    @Test
    public void utilsExist() {
        new Utils();
    }

    @Test
    public void splitShouldSplitStringIntoList() {
        List<String> splitList = split(fullOfEels, " ");
        List<String> expected = Arrays.asList("My", "hovercraft", "is", "full", "of", "eels");
        assertEquals(expected, splitList);
    }

    @Test
    public void joinShouldJoinStringsWithDelimiter() {
        String joined = join(" ", "My", "hovercraft", "is", "full", "of", "eels");
        assertEquals(fullOfEels, joined);

        joined = join("~*~*~", "Mein", "luftkissenfahrzeug", "ist", "voller", "aale");
        assertEquals("Mein~*~*~luftkissenfahrzeug~*~*~ist~*~*~voller~*~*~aale",
                     joined);
    }

    @Test
    public void joinShouldCorrectlyConcatenateByteArrays() throws UnsupportedEncodingException {
        byte[] joined = join("Mon ".getBytes("UTF-8"),
                             "aéroglisseur ".getBytes("UTF-8"),
                             "est ".getBytes("UTF-8"),
                             "plein ".getBytes("UTF-8"),
                             "d'anguilles".getBytes("UTF-8"));
        String reconstructed = new String(joined, "UTF-8");
        assertEquals("Mon aéroglisseur est plein d'anguilles", reconstructed);
    }

}
