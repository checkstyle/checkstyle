package com.puppycrawl.tools.checkstyle.checks.j2ee;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(AllTests.suite());
    }

    public static Test suite() {
        TestSuite suite =
            new TestSuite("Test for com.puppycrawl.tools.checkstyle.checks.j2ee");
        suite.addTest(new TestSuite(EntityBeanCheckTest.class));
        suite.addTest(new TestSuite(FinalStaticCheckTest.class));
        suite.addTest(new TestSuite(LocalHomeInterfaceCheckTest.class));
        suite.addTest(new TestSuite(LocalInterfaceCheckTest.class));
        suite.addTest(new TestSuite(MessageBeanCheckTest.class));
        suite.addTest(new TestSuite(RemoteHomeInterfaceCheckTest.class));
        suite.addTest(new TestSuite(RemoteInterfaceCheckTest.class));
        suite.addTest(new TestSuite(SessionBeanCheckTest.class));
        suite.addTest(new TestSuite(ThisParameterCheckTest.class));
        suite.addTest(new TestSuite(ThisReturnCheckTest.class));

        return suite;
    }
}
