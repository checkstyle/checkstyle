package com.puppycrawl.tools.checkstyle.checks.duplicates;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(AllTests.suite());
    }

    public static Test suite() {
        TestSuite suite =
            new TestSuite("Test for com.puppycrawl.tools.checkstyle.checks.duplicates");

        suite.addTest(new TestSuite(StrictDuplicateCodeCheckTest.class));

        return suite;
    }
}
