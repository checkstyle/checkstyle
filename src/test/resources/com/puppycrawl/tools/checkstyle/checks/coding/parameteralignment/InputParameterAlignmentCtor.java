/*
ParameterAlignment
tokens = CTOR_DEF, CTOR_CALL, SUPER_CTOR_CALL, LITERAL_NEW

*/

package com.puppycrawl.tools.checkstyle.checks.coding.parameteralignment;

import java.util.function.Supplier;

class InputParameterAlignmentCtor
{
    InputParameterAlignmentCtor() {
    }

    InputParameterAlignmentCtor(int a, long b) {
    }

    InputParameterAlignmentCtor(
            int a, double b) {
    }

    InputParameterAlignmentCtor(float a,
                            int b, // violation 'Parameters must be aligned.'
                            int c) { // violation 'Parameters must be aligned.'
    }

    InputParameterAlignmentCtor(
            int a,
            int b, int c,
            int d, int e, int f, int g, int h) {
    }

    InputParameterAlignmentCtor(char a,
        // violation below 'Parameters must be aligned.'
        float b) {
    }


    InputParameterAlignmentCtor(char a, int b, int c, int d, int e, int f, int g,
        // violation below 'Parameters must be aligned.'
        int h) {

    }

    InputParameterAlignmentCtor(byte a,
        // violation below 'Parameters must be aligned.'
        int b, int c, int d, int e, int f, int g,
            // violation below 'Parameters must be aligned.'
            int h) {

    }

    void method1() {
        String[] strings = new String[3];
    }

    void method2(int a, long b) {
    }

    void method3(
        int a, double b) {
    }

    void myMethod4(float a,
                   int b,
                   int c) {
        Supplier<Integer> s = new Supplier<>() {
            @Override
            public Integer get() {
                return 42;
            }
        };
    }

    void myMethod5(
            float a,
            int b,
            int c) {
        method3(1, 1.0d);
        method3(1,
            1.0d);
    }

    void myMethod6(char a,
        float b) {
        InputParameterAlignmentCtor ipa = new InputParameterAlignmentCtor('1',
                                                                          2.0d);

        InputParameterAlignmentCtor ipa2 = new InputParameterAlignmentCtor('1',
            // violation below 'Parameters must be aligned.'
            2.0d);
    }

    void myMethod7(byte a, int b, int c, int d, int e, int f, int g,
        int h) {
    }

    void myMethod8(byte a,
        int b, int c, int d, int e, int f, int g,
            int h) {
    }
}

