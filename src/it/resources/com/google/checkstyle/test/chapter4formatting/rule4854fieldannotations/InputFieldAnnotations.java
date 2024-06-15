package com.google.checkstyle.test.chapter4formatting.rule4854fieldannotations;

import java.util.ArrayList;
import java.util.List;

public class InputFieldAnnotations {
    public @interface SomeAnnotation1 { }

    public @interface SomeAnnotation2 { }

    public @interface SomeAnnotation3 {
        int x();
    }

    /**
     * testing.
     */
    @SomeAnnotation1
    @SomeAnnotation2
    String name = "Zops";

    /**
     * testing.
     */
    @SomeAnnotation1 @SomeAnnotation2
    int age = 19;

    @SomeAnnotation1 @SomeAnnotation2 boolean test = false;

    @SuppressWarnings("all") @SomeAnnotation3(x = 0) float pi = 3.14f;

    @SomeAnnotation1 @SomeAnnotation3(x = 14)
    /** // warn
     * testing.
     */
    List<String> list = new ArrayList<>();

    @SuppressWarnings("all")
    @SomeAnnotation1
    /** // warn
     * testing.
     */
    InputFieldAnnotations obj = new InputFieldAnnotations();

    /**
     * testing.
     */
    @SomeAnnotation1 @SomeAnnotation2 // warn
    void test1() {}

    @SomeAnnotation1 @SomeAnnotation2 void test2() {} // warn

    @SomeAnnotation3(x = 78) void test3() {} // warn
}
