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

    public void testClose()
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

    public void testLineBreakSingleChar()
    {
        final StringArrayReader o = new StringArrayReader(new String[] {"a", "bc"});
        try {
            int a = o.read();
            assertEquals('a', a);
            int nl1 = o.read();
            assertEquals('\n', nl1);
            int b = o.read();
            assertEquals('b', b);
            int c = o.read();
            assertEquals('c', c);
            int nl2 = o.read();
            assertEquals('\n', nl2);
            int eof = o.read();
            assertEquals(-1, eof);
        }
        catch (IOException ex) {
        }
    }
}
