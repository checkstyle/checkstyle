package com.openjdk.checkstyle.test.chapter3formatting.rule37indentation;

public class InputThrowsIndentInvalid {

    void method()
        throws Exception {
    // violation above '.* incorrect indentation level 8, expected .* 12.'
    }
}
