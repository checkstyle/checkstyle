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
        assertNotNull(c);

        // TODO: reintegrate the following test?
        //        final String[] pkgs = new String[0];
        //        c.setModuleFactory(pkgs);
        //        assertEquals(pkgs, c.getModuleFactory());
    }
}
