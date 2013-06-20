package com.cmendenhall.tests;

import com.cmendenhall.ParameterDecoder;
import junit.framework.Assert;
import org.junit.Test;

import java.util.HashMap;

import static junit.framework.Assert.assertEquals;

public class ParameterDecoderTest {
    private String decoded = "search=My hovercraft is full (of eels?)";

    @Test
    public void parameterDecoderShouldDecodePercentEncodedURLs() {
        String encoded = "search=My+hovercraft+is+full+%28of+eels%3F%29";
        Assert.assertEquals(ParameterDecoder.decode(encoded), decoded);
    }

    @Test
    public void hexEncodedValuesShouldNotBeCaseSensitive() {
        String encoded = "search=My+hovercraft+is+full+%28of+eels%3f%29";
        assertEquals(ParameterDecoder.decode(encoded), decoded);
    }

    @Test
    public void parameterDecoderShouldExtractMapOfValues() {
        String queryString = "my=Mein&hovercraft=luftkissenfahrzeug&fullofeels=ist+voller+aale";
        HashMap<String, String> values = ParameterDecoder.getParameters(queryString);
        assertEquals(values.get("my"), "Mein");
        assertEquals(values.get("hovercraft"), "luftkissenfahrzeug");
        assertEquals(values.get("fullofeels"), "ist voller aale");
    }

}
