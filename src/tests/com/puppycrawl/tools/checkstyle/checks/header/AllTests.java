package com.puppycrawl.tools.checkstyle.checks.header;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(AllTests.suite());
    }

    public static Test suite() {
        TestSuite suite =
            new TestSuite("Test for com.puppycrawl.tools.checkstyle.checks.header");

        // tests from this package
        suite.addTest(new TestSuite(HeaderCheckTest.class));
        suite.addTest(new TestSuite(CrossLanguageRegexpHeaderCheckTest.class));

        return suite;
    }
}
