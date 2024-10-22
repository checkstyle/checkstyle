/*
ParameterAlignment
tokens = METHOD_DEF, METHOD_CALL

*/

package com.puppycrawl.tools.checkstyle.checks.coding.parameteralignment;

import java.util.function.Supplier;

class InputParameterAlignmentMethod
{
    InputParameterAlignmentMethod(int a, double b) {
    }

    void method1(int a, long b) {
    }

    void method2(
        int a, double b) {
    }

    void myMethod3(float a,
                   @MyAnnotation(param1 = "1", param2 = 1, param3 = 1L) int b,
                   int c) {
        Supplier<Integer> s = new Supplier<>() {
            @Override
            public Integer get() {
                return 42;
            }
        };
    }

    void myMethod4(
            float a,
            int b,
            int c) {
        method2(1, 1.0d);
        method2(1,
            // violation below 'Parameters must be aligned.'
            1.0d);
    }

    void myMethod5(byte a, int b, int c, int d, int e, int f, int g,
        // violation below 'Parameters must be aligned.'
        int h) {
    }

    void myMethod6(byte a,
        // violation below 'Parameters must be aligned.'
        int b, int c, int d, int e, int f, int g,
            // violation below 'Parameters must be aligned.'
            int h) {
    }

    @interface MyAnnotation {
        String param1();
        int param2();
        long param3();
    }

}

