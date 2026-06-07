package com.openjdk.checkstyle.test.chapterformatting.ruleindentation;

public class InputLineWrappingIndentationInvalid {

    void method(int a,
        int b) {
    // violation above '.* incorrect indentation level 8, expected .* 12.'
        int sum = a + b;
    }
}
