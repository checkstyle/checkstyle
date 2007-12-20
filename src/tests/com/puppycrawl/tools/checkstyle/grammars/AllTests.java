package com.puppycrawl.tools.checkstyle.grammars;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Describe class AllTests.
 * @author Rick Giles
 * @version Jun 21, 2004
 */
public class AllTests {

    public static Test suite() {
        TestSuite suite =
            new TestSuite("Test for com.puppycrawl.tools.checkstyle.grammars");
        //$JUnit-BEGIN$
        suite.addTest(new TestSuite(GeneratedJava14LexerTest.class));
        suite.addTest(new TestSuite(HexFloatsTest.class));
        suite.addTest(new TestSuite(EmbeddedNullCharTest.class));
        suite.addTest(new TestSuite(VarargTest.class));
        //$JUnit-END$
        return suite;
    }
}
