package com.puppycrawl.tools.checkstyle.checks.modifier;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(AllTests.class);
    }

    public static Test suite() {
        TestSuite suite =
            new TestSuite("Test for com.puppycrawl.tools.checkstyle.checks.modifier");

        suite.addTest(new TestSuite(ModifierOrderCheckTest.class));
        suite.addTest(new TestSuite(RedundantModifierTest.class));

        return suite;
    }
}
