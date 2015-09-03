package com.puppycrawl.tools.checkstyle.checks.javadoc;

/**
 * comment.
 */
public class Input_1379666 {
    /**
     * @throws BadStringFormat some text
     */
    public void ok() throws BadStringFormat {
    }

    /**
     * @throws Input_1379666.BadStringFormat some text
     */
    public void error1()
        throws com.puppycrawl.tools.checkstyle.checks.javadoc.Input_1379666.BadStringFormat {
    }

    /**
     * Some comment.
     * @throws com.puppycrawl.tools.checkstyle.checks.javadoc.Input_1379666.BadStringFormat some text
     */
    public void error2() throws Input_1379666.BadStringFormat {
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
