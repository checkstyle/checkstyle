package com.puppycrawl.tools.checkstyle.checks.metrics;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(AllTests.suite());
    }

    public static Test suite() {
        TestSuite suite =
            new TestSuite("Test for com.puppycrawl.tools.checkstyle.checks.metrics");
        suite.addTest(new TestSuite(BooleanExpressionComplexityCheckTest.class));
        suite.addTest(new TestSuite(ClassFanOutComplexityCheckTest.class));
        suite.addTest(new TestSuite(ClassDataAbstractionCouplingCheckTest.class));
        suite.addTest(new TestSuite(CyclomaticComplexityCheckTest.class));
        suite.addTest(new TestSuite(NPathComplexityCheckTest.class));
        suite.addTest(new TestSuite(JavaNCSSCheckTest.class));

        return suite;
    }
}
