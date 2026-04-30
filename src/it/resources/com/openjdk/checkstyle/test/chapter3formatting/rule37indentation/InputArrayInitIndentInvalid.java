package com.openjdk.checkstyle.test.chapter3formatting.rule37indentation;

public class InputArrayInitIndentInvalid {

    int[] values = {
    1, 2, 3
    // violation above '.* incorrect indentation level 4, expected .* 8, 12.'
    };
}
