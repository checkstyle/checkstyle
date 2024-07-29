/*
ParameterPlacement
option = SEPARATE_LINE_ALLOW_SINGLE_LINE
tokens = (default)METHOD_DEF, CTOR_DEF, RECORD_DEF, METHOD_CALL, CTOR_CALL, \
         SUPER_CTOR_CALL, LITERAL_NEW, ANNOTATION


*/

package com.puppycrawl.tools.checkstyle.checks.coding.parameterplacement;

import java.util.function.Supplier;

class InputParameterPlacementSeparateLineAllowSingleLine
{
    InputParameterPlacementSeparateLineAllowSingleLine() {
    }

    InputParameterPlacementSeparateLineAllowSingleLine(int a) {
    }

    InputParameterPlacementSeparateLineAllowSingleLine(
            char a) {
    }

    InputParameterPlacementSeparateLineAllowSingleLine(int a, int b) {
    }

    InputParameterPlacementSeparateLineAllowSingleLine(char a,
            int b) {
    }

    InputParameterPlacementSeparateLineAllowSingleLine(
            byte a,
            // violation below 'Parameters must be placed on separate lines.'
            int b, int c,
            int d) {
    }

    InputParameterPlacementSeparateLineAllowSingleLine(
            float a,
            int b,
            int c) {
    }

    @MyAnnotation(param1 = "abc", param2 = 1, param3 = 2L)
    void noParamMethod() {
    }

    @MyAnnotation(param1 = "abc",
            // violation below 'Parameters must be placed on separate lines.'
            param2 = 1, param3 = 2L)
    void singleParamMethodInline(int a) {
    }

    @MyAnnotation(param1 = "abc",
            param2 = 1,
            param3 = 2L)
    void singleParamMethodSeparateLine(
            @MyAnnotation(param1 = "abc",
            // violation below 'Parameters must be placed on separate lines.'
            param2 = 1, param3 = 2L) int a) {
    }

    void myMethod(int a, int b, int c, int d, int e, int f, int g, int h) {
    }

    // violation below 'Parameters must be placed on separate lines.'
    void myMethod2(int a, int b, int c, int d, int e, int f, int g,
            int h) {
    }

    @MyAnnotation(param1 = "abc", param2 = 1, param3 = 2L)
    void myMethod2(byte a,
            // violation below 'Parameters must be placed on separate lines.'
            int b, int c, int d, int e, int f, int g,
            int h) {
        myMethod(1,1,1,1,1,1,1,1);
        myMethod(1,
                // violation below 'Parameters must be placed on separate lines.'
                1,1,1,1,1,1,1);
        myMethod(1,
                1,
                1,
                1,
                1,
                // violation below 'Parameters must be placed on separate lines.'
                1, 1,
                1);
    }

    void myMethod2(
            float a,
            int b,
            int c,
            int d,
            int e,
            int f,
            int g,
            int h) {
        Supplier<Integer> s = new Supplier<>() {
            @Override
            public Integer get() {
                return 42;
            }
        };
    }

    @interface MyAnnotation {
        String param1();
        int param2();
        long param3();
    }

}
