package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

/**
 * comment.
 */
public class InputJavadocMethod_1379666 {
    /**
     * @throws BadStringFormat some text
     */
    public void ok() throws BadStringFormat {
    }


    /**
     * Some comment.
     * @throws java.lang.Exception some text
     */
    public void error1()
        throws java.lang.Exception {
    }

    /**
     * @throws InputJavadocMethod_1379666.BadStringFormat some text
     */
    public void error2() throws InputJavadocMethod_1379666.BadStringFormat {
    }

    /**
     * Some exception class.
     */
    public static class BadStringFormat extends Exception {
        /**
         * Some comment.
         * @param s string.
         */
        BadStringFormat(String s) {
            super(s);
        }
    }
}
