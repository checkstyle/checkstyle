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
    @SomeAnnotation1 @SomeAnnotation2 // warn
    void test3() {}

    @SomeAnnotation1
    @SomeAnnotation2
    /** // warn
     * testing.
     */
    void test4() {}

    @SomeAnnotation1 @SomeAnnotation2 // warn
    /** // warn
     * testing.
     */
    void test5() {}

    @SomeAnnotation1 @SomeAnnotation2 void test6() {} // warn

    @SuppressWarnings("all") void test7() {} // warn

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
    @SomeAnnotation1 @SomeAnnotation2 // warn
    InputMethodsAndConstructorsAnnotations(int x) {}

    @SomeAnnotation1
    @SomeAnnotation2
    /** // warn
     * testing.
     */
    InputMethodsAndConstructorsAnnotations(String str) {}

    @SomeAnnotation1 @SomeAnnotation2 // warn
    /** // warn
     * testing.
     */
    InputMethodsAndConstructorsAnnotations(double d) {}

    @SomeAnnotation1 @SomeAnnotation2  InputMethodsAndConstructorsAnnotations(String a, // warn
                                                                              String b) {}

    @SuppressWarnings("all") InputMethodsAndConstructorsAnnotations(int x, int y) {} // warn
}
