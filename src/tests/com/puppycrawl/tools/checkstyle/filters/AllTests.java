package com.puppycrawl.tools.checkstyle.filters;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Filter test suite.
 * @author Rick Giles
 */
public class AllTests
{
    public static Test suite()
    {
        TestSuite suite =
            new TestSuite("Test for com.puppycrawl.tools.checkstyle.filter");
        //$JUnit-BEGIN$
        suite.addTest(new TestSuite(IntMatchFilterTest.class));
        suite.addTest(new TestSuite(IntRangeFilterTest.class));
        suite.addTest(new TestSuite(CSVFilterTest.class));
        suite.addTest(new TestSuite(SupressElementTest.class));
        //$JUnit-END$
        return suite;
    }
}
