package com.openjdk.checkstyle.test.chapter3formatting.rule37indentation;

public class InputLineWrappingIndentationInvalid {

    void method(int a,
        int b) {
    // violation above '.* incorrect indentation level 8, expected .* 12.'
        int sum = a + b;
    }
}
