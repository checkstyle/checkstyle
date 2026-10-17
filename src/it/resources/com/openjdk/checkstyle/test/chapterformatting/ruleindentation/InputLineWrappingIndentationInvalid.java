package com.openjdk.checkstyle.test.chapterformatting.ruleindentation;

// violation first line 'Header is missing'

public class InputLineWrappingIndentationInvalid {

    // violation 2 lines below """Parameter should be aligned vertically or
    //  indented 8 spaces relative to the method declaration."""
    void method(int a,
        int b) {
    // violation above '.* incorrect indentation level 8, expected .* 12.'
        int sum = a + b;
    }
}
