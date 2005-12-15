package test.javadoc.method;

/**
 * comment.
 */
public class Test_1379666 {
    /**
     * @throws BadStringFormat some text
     */
    public void ok() throws BadStringFormat {
    }

    /**
     * @throws Test_1379666.BadStringFormat some text
     */
    public void error1()
        throws test.javadoc.method.Test_1379666.BadStringFormat
    {
    }

    /**
     * Some comment.
     * @throws test.javadoc.method.Test_1379666.BadStringFormat some text
     */
    public void error2() throws Test_1379666.BadStringFormat {
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
