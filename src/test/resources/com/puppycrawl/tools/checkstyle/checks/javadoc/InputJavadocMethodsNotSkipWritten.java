package com.puppycrawl.tools.checkstyle.checks.javadoc;

public class InputJavadocMethodsNotSkipWritten {
    /**
     * Description.
     * 
     * @param BAD
     *            This param doesn't exist.
     */
    @MyAnnotation
    public void InputJavadocMethodsNotSkipWritten() {
    }

    /**
     * Description.
     * 
     * @param BAD
     *            This param doesn't exist.
     */
    @MyAnnotation
    public void test() {
    }

    /** Description. */
    @MyAnnotation
    public void test2() {
    }
}
