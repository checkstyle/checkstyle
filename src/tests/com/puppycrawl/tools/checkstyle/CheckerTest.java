package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import junit.framework.TestCase;

/**
 * Test class for checker
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 */
public class CheckerTest
    extends TestCase
{
    public void testPackageNames() throws CheckstyleException
    {
        final Checker c = new Checker();
        final String[] pkgs = new String[0];
        c.setPackageNames(pkgs);
        assertEquals(pkgs, c.getPackageNames());
    }
}
