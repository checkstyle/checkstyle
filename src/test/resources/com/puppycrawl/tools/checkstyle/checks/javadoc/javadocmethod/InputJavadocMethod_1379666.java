package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

/**
 * comment.
 */
public class InputJavadocMethod_1379666 {
    /**
     * @throws Exception some text
     */
    public void ok() throws Exception {
    }


    /**
     * Some comment.
     * @throws Exception some text
     */
    public void error1()
        throws Exception {
    }

    /**
     * @throws Exception some text
     */
    public void error2() throws Exception {
    }
}
