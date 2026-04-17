package com.openjdk.checkstyle.test.chapter3formatting.rule37indentation;

public class InputLineWrappingIndentationInvalid {

    void method(int a,
        int b) {
    // violation above '.* has incorrect indentation level 8, expected level should be 12'
        int sum = a + b;
    }
}
