package com.google.checkstyle.test.chapter4formatting.rule4852classannotations;

public class InputClassAnnotations {
    public @interface SomeAnnotation1 { }

    public @interface SomeAnnotation2 { }

    /**
     * Inner class 2.
     */
    @SomeAnnotation1
    @SomeAnnotation2
    class Inner1 {}

    @SomeAnnotation1
    @SomeAnnotation2
    /** // warn
     * Inner class 2.
     */
    class Inner2 {}

    /**
     * Inner class 3.
     */
    @SomeAnnotation1 @SomeAnnotation2 // warn
    class Inner3 {}

    @SomeAnnotation1 @SomeAnnotation2 // warn
    /** // warn
     * Inner class 4.
     */
    class Inner4 {}
}
