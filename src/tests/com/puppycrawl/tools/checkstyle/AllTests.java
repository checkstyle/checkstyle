package com.puppycrawl.tools.checkstyle;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Test suite for all tests.
 * @author Rick Giles
 * @version 22-Nov-2002
 */
public class AllTests {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(AllTests.suite());
    }

    public static Test suite() {
        TestSuite suite =
            new TestSuite("Test for com.puppycrawl.tools.checkstyle");

        // test from this package
        suite.addTest(new TestSuite(CheckerTest.class));
        suite.addTest(new TestSuite(ConfigurationLoaderTest.class));
        suite.addTest(new TestSuite(OptionTest.class));
        suite.addTest(new TestSuite(PackageNamesLoaderTest.class));
        suite.addTest(new TestSuite(PackageObjectFactoryTest.class));
        suite.addTest(new TestSuite(StringArrayReaderTest.class));
        suite.addTest(new TestSuite(UtilsTest.class));
        suite.addTest(new TestSuite(XMLLoggerTest.class));
        
        // tests from sub-packages
        suite.addTest(com.puppycrawl.tools.checkstyle.api.AllTests.suite());
        suite.addTest(com.puppycrawl.tools.checkstyle.checks.AllTests.suite());
        suite.addTest(com.puppycrawl.tools.checkstyle.filters.AllTests.suite());
        suite.addTest(
            com.puppycrawl.tools.checkstyle.grammars.AllTests.suite());
        //$JUnit-END$
        return suite;
    }
}
