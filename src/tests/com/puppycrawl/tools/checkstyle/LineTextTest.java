package com.puppycrawl.tools.checkstyle;

import junit.framework.TestCase;

public class LineTextTest
    extends TestCase
{
    public LineTextTest(String name)
    {
        super(name);
    }

    public void testMisc()
    {
        final LineText o = new LineText(666, 667, "claira");
        assertNotNull(o);
        assertEquals("{Text = 'claira', Line = 666, Column = 667}", o.toString());
    }
}
