package com.puppycrawl.tools.checkstyle;

import junit.framework.TestCase;

import java.util.Set;
import java.util.HashSet;

public class ClassResolverTest
    extends TestCase
{
    public ClassResolverTest(String name)
    {
        super(name);
    }

    public void testMisc() throws ClassNotFoundException
    {
        final Set imps = new HashSet();
        imps.add(new LineText(666, "java.io.File"));
        imps.add(new LineText(666, "nothing.will.match.*"));
        imps.add(new LineText(666, "java.applet.*"));
        ClassResolver cr =
            new ClassResolver(Thread.currentThread().getContextClassLoader(),
                              null, imps);
        assertNotNull(cr);
        try {
            cr.resolve("who.will.win.the.world.cup");
            fail("Should not resolve class");
        }
        catch (ClassNotFoundException e) {
        }
        cr.resolve("java.lang.String");
        cr.resolve("StringBuffer");
        cr.resolve("AppletContext");

        try {
            cr.resolve("ChoiceFormat");
            fail();
        }
        catch (ClassNotFoundException e) {
        }

        imps.add(new LineText(324, "java.text.ChoiceFormat"));
        cr.resolve("ChoiceFormat");

        cr = new ClassResolver(Thread.currentThread().getContextClassLoader(),
                               "java.util", imps);
        cr.resolve("List");
        try {
            cr.resolve("two.nil.england");
            fail();
        }
        catch (ClassNotFoundException e) {
        }
    }
}
