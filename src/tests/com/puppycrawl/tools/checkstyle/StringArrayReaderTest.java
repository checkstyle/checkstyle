package com.puppycrawl.tools.checkstyle;

import junit.framework.TestCase;

import java.io.IOException;

public class StringArrayReaderTest
    extends TestCase
{
    public StringArrayReaderTest(String name)
    {
        super(name);
    }

    public void testMisc()
    {
        final StringArrayReader o = new StringArrayReader(new String[] {""});
        assertNotNull(o);
        o.close();
        try {
            o.read();
            fail();
        }
        catch (IOException e) {
        }
    }
}
