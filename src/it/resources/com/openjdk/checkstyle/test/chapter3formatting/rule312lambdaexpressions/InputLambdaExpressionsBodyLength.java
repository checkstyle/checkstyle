package com.openjdk.checkstyle.test.chapter3formatting.rule312lambdaexpressions;

/** Test case for Lambda Body length */

public class InputLambdaExpressionsBodyLength {

    /**
     * Illegal method -> lambda expression body is 11 lines (max allowed 10)
     */
    public void doIllegal() {
        Runnable r = () -> {   // line 11 (opening brace)
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
        };  // line 22 (closing brace) → length 12, violation
    }

    /**
     * Simple legal method.
     */
    public void doLegal() {
        Runnable r = () -> {   // line 1 (opening brace)
            int a = 1;
            int b = 2;
            int c = 3;
            int d = 4;
            int e = 5;
            int f = 6;
            int g = 7;
            int h = 8;
            int i = 9;
        };  // line 10 (closing brace) → length 10, allowed
    }
}
