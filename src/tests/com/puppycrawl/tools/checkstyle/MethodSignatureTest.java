package com.puppycrawl.tools.checkstyle;

import junit.framework.TestCase;

public class MethodSignatureTest
    extends TestCase
{
    public MethodSignatureTest(String name)
    {
        super(name);
    }

    public void testMisc()
    {
        final MethodSignature o = new MethodSignature();
        assertNotNull(o);
        final MyCommonAST ret = new MyCommonAST();
        o.setReturnType(ret);
        assertEquals(ret, o.getReturnType());
    }
}
