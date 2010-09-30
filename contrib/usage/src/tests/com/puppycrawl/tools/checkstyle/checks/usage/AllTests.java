package com.puppycrawl.tools.checkstyle.checks.usage;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(AllTests.suite());
    }

    public static Test suite() {
        TestSuite suite =
            new TestSuite("Test for com.puppycrawl.tools.checkstyle.checks.usage");
        suite.addTest(new TestSuite(OneMethodPrivateFieldCheckTest.class));
        suite.addTest(new TestSuite(UnusedLocalVariableCheckTest.class));
        suite.addTest(new TestSuite(UnusedParameterCheckTest.class));
        suite.addTest(new TestSuite(UnusedPrivateFieldCheckTest.class));
        suite.addTest(new TestSuite(UnusedPrivateMethodCheckTest.class));

        return suite;
    }
}
