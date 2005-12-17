package com.puppycrawl.tools.checkstyle.checks.imports;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(AllTests.suite());
    }

    public static Test suite() {
        TestSuite suite =
            new TestSuite("Test for com.puppycrawl.tools.checkstyle.checks.imports");

        suite.addTest(new TestSuite(AvoidStarImportTest.class));
        suite.addTest(new TestSuite(ImportOrderCheckTest.class));
        suite.addTest(new TestSuite(IllegalImportCheckTest.class));
        suite.addTest(new TestSuite(RedundantImportCheckTest.class));
        suite.addTest(new TestSuite(UnusedImportsCheckTest.class));

        return suite;
    }
}
