package com.puppycrawl.tools.checkstyle;

import junit.framework.TestCase;

public class JavadocTagTest
    extends TestCase
{
    public JavadocTagTest(String name)
    {
        super(name);
    }

    public void testMisc()
    {
        final JavadocTag o = new JavadocTag(666, "@fred");
        assertNotNull(o);
        assertEquals("{Tag = '@fred', lineNo = 666, Arg1 = 'null'}",
                     o.toString());
        assertEquals(false, o.isAuthorTag());
    }
}
