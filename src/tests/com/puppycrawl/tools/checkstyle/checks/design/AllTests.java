package com.puppycrawl.tools.checkstyle.checks.design;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(AllTests.suite());
    }

    public static Test suite() {
        TestSuite suite =
            new TestSuite("Test for com.puppycrawl.tools.checkstyle.checks.design");

        suite.addTest(new TestSuite(DesignForExtensionCheckTest.class));
        suite.addTest(new TestSuite(FinalClassCheckTest.class));
        suite.addTest(new TestSuite(HideUtilityClassConstructorCheckTest.class));
        suite.addTest(new TestSuite(InterfaceIsTypeCheckTest.class));
        suite.addTest(new TestSuite(MutableExceptionCheckTest.class));
        suite.addTest(new TestSuite(ThrowsCountCheckTest.class));
        suite.addTest(new TestSuite(VisibilityModifierCheckTest.class));

        return suite;
    }
}
