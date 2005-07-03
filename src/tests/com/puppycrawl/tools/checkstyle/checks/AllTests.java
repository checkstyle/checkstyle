package com.puppycrawl.tools.checkstyle.checks;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(AllTests.class);
    }

    public static Test suite() {
        TestSuite suite =
            new TestSuite("Test for com.puppycrawl.tools.checkstyle.checks");

        // tests from this package
        suite.addTest(new TestSuite(ArrayTypeStyleCheckTest.class));
        suite.addTest(new TestSuite(DescendantTokenCheckTest.class));
        suite.addTest(new TestSuite(FileSetCheckLifecycleTest.class));
        suite.addTest(new TestSuite(FinalParametersCheckTest.class));
        suite.addTest(new TestSuite(GenericIllegalRegexpCheckTest.class));
        suite.addTest(new TestSuite(NewlineAtEndOfFileCheckTest.class));
        suite.addTest(new TestSuite(RequiredRegexpCheckTest.class));
        suite.addTest(new TestSuite(TodoCommentCheckTest.class));
        suite.addTest(new TestSuite(TrailingCommentCheckTest.class));
        suite.addTest(new TestSuite(TranslationCheckTest.class));
        suite.addTest(new TestSuite(UncommentedMainCheckTest.class));
        suite.addTest(new TestSuite(UpperEllCheckTest.class));

        // test from sub-packages
        suite.addTest(com.puppycrawl.tools.checkstyle.checks.blocks.AllTests.suite());
        suite.addTest(com.puppycrawl.tools.checkstyle.checks.coding.AllTests.suite());
        suite.addTest(com.puppycrawl.tools.checkstyle.checks.design.AllTests.suite());
        suite.addTest(com.puppycrawl.tools.checkstyle.checks.duplicates.AllTests.suite());
        suite.addTest(com.puppycrawl.tools.checkstyle.checks.header.AllTests.suite());
        suite.addTest(com.puppycrawl.tools.checkstyle.checks.imports.AllTests.suite());
        suite.addTest(com.puppycrawl.tools.checkstyle.checks.indentation.AllTests.suite());
        suite.addTest(com.puppycrawl.tools.checkstyle.checks.j2ee.AllTests.suite());
        suite.addTest(com.puppycrawl.tools.checkstyle.checks.javadoc.AllTests.suite());
        suite.addTest(com.puppycrawl.tools.checkstyle.checks.metrics.AllTests.suite());
        suite.addTest(com.puppycrawl.tools.checkstyle.checks.naming.AllTests.suite());
        suite.addTest(com.puppycrawl.tools.checkstyle.checks.sizes.AllTests.suite());
        suite.addTest(com.puppycrawl.tools.checkstyle.checks.whitespace.AllTests.suite());

        return suite;
    }
}
