package com.puppycrawl.tools.checkstyle;

import junit.framework.TestCase;

public class MyModifierSetTest
    extends TestCase
{
    public MyModifierSetTest(String name)
    {
        super(name);
    }

    public void testMisc()
    {
        final MyModifierSet o = new MyModifierSet();
        assertNotNull(o);
        assertEquals("MyModifierSet [ ]", o.toString());
        o.addModifier(new MyCommonAST());
        assertEquals("MyModifierSet [ null ]", o.toString());
    }
}
