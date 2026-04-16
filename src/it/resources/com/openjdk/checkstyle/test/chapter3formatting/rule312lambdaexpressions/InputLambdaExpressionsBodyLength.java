package com.openjdk.checkstyle.test.chapter3formatting.rule312lambdaexpressions;

/**
 * Test input for lambda body length with violations.
 */
public class InputLambdaExpressionsBodyLength {

    /**
     * Illegal method - lambda expression body exceeds 10 lines.
     */
    public void doIllegal() {
        Runnable r = () -> { // violation 'Lambda body length is 11 lines (max allowed is 10).'
            int a = 1;
            int b = 2;
            int c = 3;
            int d = 4;
            int e = 5;
            int f = 6;
            int g = 7;
            int h = 8;
            int i = 9;
            int j = 10;
        };
    }

    /**
     * Simple legal method.
     */
    public void doLegal() {
        Runnable r = () -> {
            int a = 1;
            int b = 2;
            int c = 3;
            int d = 4;
            int e = 5;
            int f = 6;
            int g = 7;
            int h = 8;
            int i = 9;
        };
    }
}
