package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

/**
 * Config: default
 */
public class InputJavadocMethod_1379666 {
    /**
     * @throws BadStringFormat some text
     */
    public void ok() throws BadStringFormat { // ok
    }


    /**
     * Some comment.
     * @throws java.lang.Exception some text
     */
    public void error1() // ok
        throws java.lang.Exception {
    }

    /**
     * @throws InputJavadocMethod_1379666.BadStringFormat some text
     */
    public void error2() throws InputJavadocMethod_1379666.BadStringFormat { // ok
    }

    /**
     * Some exception class.
     */
    public static class BadStringFormat extends Exception { // ok
        /**
         * Some comment.
         * @param s string.
         */
        BadStringFormat(String s) { // ok
            super(s);
        }
    }
}
