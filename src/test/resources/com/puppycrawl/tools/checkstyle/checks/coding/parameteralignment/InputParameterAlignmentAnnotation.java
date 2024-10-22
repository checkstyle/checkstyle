/*
ParameterAlignment
tokens = ANNOTATION

*/

package com.puppycrawl.tools.checkstyle.checks.coding.parameteralignment;

import java.util.function.Supplier;

class InputParameterAlignmentAnnotation
{
    @interface MyAnnotation {
        String param1();
        int param2();
        long param3();
    }

    InputParameterAlignmentAnnotation(int a, double b) {
    }

    void method1(int a, long b) {
    }

    void method2(
        int a, double b) {
    }

    @MyAnnotation(param1 = "abc", param2 = 1, param3 = 2L)
    void myMethod3(float a,
                   int b,
                   int c) {
        Supplier<Integer> s = new Supplier<>() {
            @Override
            public Integer get() {
                return 42;
            }
        };
    }

    @MyAnnotation(param1 = "abc",
                  param2 = 1,
                  param3 = 2L)
    void myMethod4(
            float a,
            int b,
            int c) {
        method2(1, 1.0d);
    }

    @MyAnnotation(
        param1 = "abc",
        param2 = 1,
        param3 = 2L)
    void myMethod5(byte a, int b, int c, int d, int e, int f, int g,
        int h) {
    }

    @MyAnnotation(param1 = "abc",
            // violation below 'Parameters must be aligned.'
            param2 = 1,
                  param3 = 2L)
    void myMethod6(byte a,
        int b, int c, int d, int e, int f, int g,
            int h) {
    }
}

