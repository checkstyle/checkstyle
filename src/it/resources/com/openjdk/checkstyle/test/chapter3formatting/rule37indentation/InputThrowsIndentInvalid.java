package com.openjdk.checkstyle.test.chapter3formatting.rule37indentation;

public class InputThrowsIndentInvalid {

    void method()
        throws Exception {
    // violation above '.* has incorrect indentation level 8, expected level should be 12'
    }
}
