package com.puppycrawl.tools.checkstyle;

import junit.framework.TestCase;
import antlr.Token;

public class MyCommonASTTest
    extends TestCase
{
    public MyCommonASTTest(String name)
    {
        super(name);
    }

    public void testMisc()
    {
        final MyCommonAST o = new MyCommonAST();
        assertNotNull(o);
        o.initialize(new Token());
        assertEquals("{Text = '<no text>', Line = 0, Column = -1}", o.toString());
    }
}
