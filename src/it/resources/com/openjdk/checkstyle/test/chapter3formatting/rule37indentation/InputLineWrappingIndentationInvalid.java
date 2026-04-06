package com.openjdk.checkstyle.test.chapter3formatting.rule37indentation;

public class InputLineWrappingIndentationInvalid {

    void method(int a,
        int b) {
    // violation above '.* incorrect indentation level.*'
        int sum = a + b;
    }
}
