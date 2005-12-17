package com.puppycrawl.tools.checkstyle.api;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(AllTests.suite());
    }

    public static Test suite() {
        TestSuite suite =
            new TestSuite("Test for com.puppycrawl.tools.checkstyle.api");

        suite.addTest(new TestSuite(AbstractViolationReporterTest.class));
        suite.addTest(new TestSuite(DetailASTTest.class));
        suite.addTest(new TestSuite(TokenTypesTest.class));

        return suite;
    }
}
