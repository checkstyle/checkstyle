package com.openjdk.checkstyle.test.chapter3formatting.rule37indentation;

public class InputArrayInitIndentInvalid {

    int[] values = {
    1, 2, 3
    // violation above '.* child has incorrect indentation level 4, expected level should be one of the following: 8, 12'
    };
}
