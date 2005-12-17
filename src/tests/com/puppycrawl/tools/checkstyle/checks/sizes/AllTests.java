package com.puppycrawl.tools.checkstyle.checks.sizes;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(AllTests.suite());
    }

    public static Test suite() {
        TestSuite suite =
            new TestSuite("Test for com.puppycrawl.tools.checkstyle.checks.sizes");

        suite.addTest(new TestSuite(AnonInnerLengthCheckTest.class));
        suite.addTest(new TestSuite(ExecutableStatementCountCheckTest.class));
        suite.addTest(new TestSuite(FileLengthCheckTest.class));
        suite.addTest(new TestSuite(LineLengthCheckTest.class));
        suite.addTest(new TestSuite(MethodLengthCheckTest.class));
        suite.addTest(new TestSuite(ParameterNumberCheckTest.class));

        return suite;
    }
}
