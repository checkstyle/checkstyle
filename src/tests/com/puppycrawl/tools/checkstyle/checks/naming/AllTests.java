package com.puppycrawl.tools.checkstyle.checks.naming;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(AllTests.suite());
    }

    public static Test suite() {
        TestSuite suite =
            new TestSuite("Test for com.puppycrawl.tools.checkstyle.checks.naming");

        suite.addTest(new TestSuite(AbstractClassNameCheckTest.class));
        suite.addTest(new TestSuite(ConstantNameCheckTest.class));
        suite.addTest(new TestSuite(LocalFinalVariableNameCheckTest.class));
        suite.addTest(new TestSuite(LocalVariableNameCheckTest.class));
        suite.addTest(new TestSuite(MemberNameCheckTest.class));
        suite.addTest(new TestSuite(MethodNameCheckTest.class));
        suite.addTest(new TestSuite(PackageNameCheckTest.class));
        suite.addTest(new TestSuite(ParameterNameCheckTest.class));
        suite.addTest(new TestSuite(StaticVariableNameCheckTest.class));
        suite.addTest(new TestSuite(TypeNameCheckTest.class));

        return suite;
    }
}
