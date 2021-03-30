package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

/**
 * Config:
 * allowedAnnotations = MyAnnotation
 */
public class InputJavadocMethodsNotSkipWritten {
    /**
     * Description.
     *
     * @param BAD
     *            This param doesn't exist.
     */
    @MyAnnotation
    public void InputJavadocMethodsNotSkipWritten() { // violation at line 11
    }

    /**
     * Description.
     *
     * @param BAD
     *            This param doesn't exist.
     */
    @MyAnnotation
    public void test() { // violation at line 21
    }

    /** Description. */
    @MyAnnotation
    public void test2() { // ok
    }

    /** Description. */
    @MyAnnotation
    public String test3(int a) throws Exception { // ok
        return "";
    }
}
@interface MyAnnotation {}
