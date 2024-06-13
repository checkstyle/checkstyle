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
    @SomeAnnotation1 @SomeAnnotation2 // warn
    void test2() {}

    @SomeAnnotation1
    @SomeAnnotation2
    /** // warn
     * testing.
     */
    void test3() {}


    @SomeAnnotation1 @SomeAnnotation2 // warn
    /** // warn
     * testing.
     */
    void test4() {}

    /**
     * testing.
     */
    @SomeAnnotation1 public void test5() {}


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

    /**
     * testing.
     */
    @SomeAnnotation1 public int InputMethodsAndConstructorsAnnotations(float f) { return 0; }
}
