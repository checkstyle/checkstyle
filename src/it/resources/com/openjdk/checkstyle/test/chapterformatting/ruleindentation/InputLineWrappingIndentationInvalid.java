package com.openjdk.checkstyle.test.chapterformatting.ruleindentation;

// violation 2 lines above 'Header is missing*'

public class InputLineWrappingIndentationInvalid {

    void method(int a,
        int b) {
    // violation above '.* incorrect indentation level 8, expected .* 12.'
        int sum = a + b;
    }
}
