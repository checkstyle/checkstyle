package com.openjdk.checkstyle.test.chapterformatting.ruleindentation;

// violation first line 'Header is missing'

public class InputThrowsIndentInvalid {

    void method()
        throws Exception {
    // violation above '.* incorrect indentation level 8, expected .* 12.'
    }
}
