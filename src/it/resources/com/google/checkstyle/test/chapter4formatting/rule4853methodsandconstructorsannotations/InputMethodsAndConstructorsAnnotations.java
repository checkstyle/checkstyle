package com.google.checkstyle.test.chapter4formatting.rule4853methodsandconstructorsannotations;

public class InputMethodsAndConstructorsAnnotations {
    public @interface SomeAnnotation1 { }

    public @interface SomeAnnotation2 { }

    // ********testing methods.***********

    /**
     * testing.
     */
    @SomeAnnotation1
    @SomeAnnotation2
    void test1() {}

    /**
     * testing.
     */
    @SomeAnnotation1 void test2() {}

    /**
     * testing.
     */
    @SomeAnnotation1 @SomeAnnotation2
    // violation above 'Annotation 'SomeAnnotation2' should be alone on line.'
    void test3() {}

    @SomeAnnotation1
    @SomeAnnotation2
    /** // violation 'Javadoc comment is placed in the wrong location.'
     * testing.
     */
    void test4() {}

    @SomeAnnotation1 @SomeAnnotation2
    // violation above 'Annotation 'SomeAnnotation2' should be alone on line.'
    /** // violation 'Javadoc comment is placed in the wrong location.'
     * testing.
     */
    void test5() {}

    @SomeAnnotation1 @SomeAnnotation2 void test6() {}
    // violation above 'Annotation 'SomeAnnotation2' should be alone on line.'

    @SuppressWarnings("all") void test7() {}
    // violation above 'Annotation 'SuppressWarnings' should be alone on line.'

    // ********testing constructors.*********

    /**
     * testing.
     */
    @SomeAnnotation1
    @SomeAnnotation2
    InputMethodsAndConstructorsAnnotations() {}

    /**
     * testing.
     */
    @SomeAnnotation1 InputMethodsAndConstructorsAnnotations(float f) {}

    /**
     * testing.
     */
    @SomeAnnotation1 @SomeAnnotation2
    // violation above 'Annotation 'SomeAnnotation2' should be alone on line.'
    InputMethodsAndConstructorsAnnotations(int x) {}

    @SomeAnnotation1
    @SomeAnnotation2
    /** // violation 'Javadoc comment is placed in the wrong location.'
     * testing.
     */
    InputMethodsAndConstructorsAnnotations(String str) {}

    @SomeAnnotation1 @SomeAnnotation2
    // violation above 'Annotation 'SomeAnnotation2' should be alone on line.'
    /** // violation 'Javadoc comment is placed in the wrong location.'
     * testing.
     */
    InputMethodsAndConstructorsAnnotations(double d) {}

    @SomeAnnotation1 @SomeAnnotation2  InputMethodsAndConstructorsAnnotations(String a,
                                                                              String b) {}
    // violation 2 lines above 'Annotation 'SomeAnnotation2' should be alone on line.'

    @SuppressWarnings("all") InputMethodsAndConstructorsAnnotations(int x, int y) {}
    // violation above 'Annotation 'SuppressWarnings' should be alone on line.'
}
