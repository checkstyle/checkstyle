package com.puppycrawl.tools.checkstyle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import org.junit.Test;

public class StringArrayReaderTest
{
    @Test
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

    @Test
    public void testLineBreakSingleChar()
    {
        final StringArrayReader o =
            new StringArrayReader(new String[] {"a", "bc"});
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

    @Test
    public void testLineBreakCharArray()
    {
        final StringArrayReader o =
            new StringArrayReader(new String[] {"a", "bc"});
        try {
            char[] a = new char[1];
            int count = o.read(a, 0, 1);
            assertEquals(1, count);
            assertEquals('a', a[0]);
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

    @Test
    public void testNoLineBreakCharArray()
    {
        final StringArrayReader o =
            new StringArrayReader(new String[] {"a", "bc"});
        try {
            char[] a = new char[1];
            o.read(a, 0, 1);
            int nl1 = o.read();
            assertEquals('\n', nl1);
            char[] b = new char[1];
            int count = o.read(b, 0, 1);
            assertEquals(1, count);
            assertEquals('b', b[0]);
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
