package com.puppycrawl.tools.checkstyle.checks.javadoc;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(AllTests.suite());
    }

    public static Test suite() {
        TestSuite suite =
            new TestSuite("Test for com.puppycrawl.tools.checkstyle.checks.javadoc");

        suite.addTest(new TestSuite(JavadocMethodCheckTest.class));
        suite.addTest(new TestSuite(JavadocStyleCheckTest.class));
        suite.addTest(new TestSuite(JavadocTypeCheckTest.class));
        suite.addTest(new TestSuite(JavadocVariableCheckTest.class));
        suite.addTest(new TestSuite(PackageHtmlCheckTest.class));

        return suite;
    }
}
