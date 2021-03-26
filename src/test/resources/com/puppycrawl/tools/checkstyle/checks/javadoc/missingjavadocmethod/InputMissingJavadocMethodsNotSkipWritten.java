package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

/* Config:
 * scope = "private"
 * allowedAnnotations = "MyAnnotation"
 */
public class InputMissingJavadocMethodsNotSkipWritten {
    /**
     * Description.
     *
     * @param BAD
     *            This param doesn't exist.
     */
    @MyAnnotation
    public void InputMissingJavadocMethodsNotSkipWritten() {
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

    /** Description. */
    @MyAnnotation
    public String test3(int a) throws Exception {
        return "";
    }
}
