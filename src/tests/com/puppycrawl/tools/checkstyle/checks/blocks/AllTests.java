package com.puppycrawl.tools.checkstyle.checks.blocks;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(AllTests.suite());
    }

    public static Test suite() {
        TestSuite suite =
            new TestSuite("Test for com.puppycrawl.tools.checkstyle.checks.blocks");

        suite.addTest(new TestSuite(AvoidNestedBlocksCheckTest.class));
        suite.addTest(new TestSuite(EmptyBlockCheckTest.class));
        suite.addTest(new TestSuite(LeftCurlyCheckTest.class));
        suite.addTest(new TestSuite(NeedBracesCheckTest.class));
        suite.addTest(new TestSuite(RightCurlyCheckTest.class));

        return suite;
    }
}
