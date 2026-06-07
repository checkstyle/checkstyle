package com.openjdk.checkstyle.test.chapterformatting.ruleindentation;

public class InputThrowsIndentInvalid {

    void method()
        throws Exception {
    // violation above '.* incorrect indentation level 8, expected .* 12.'
    }
}
