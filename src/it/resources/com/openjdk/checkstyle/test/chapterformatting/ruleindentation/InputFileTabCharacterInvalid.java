package com.openjdk.checkstyle.test.chapterformatting.ruleindentation;

// violation first line 'Header is missing*'

/** Invalid tab-indented examples for OpenJDK style section 3.7. */
public class InputFileTabCharacterInvalid {

    // violation below ''//' must be followed by a whitespace.'
    //	has tab indentation
    // violation above 'Line contains a tab character.'
    private int value;

    private void method() {
        value++;
    }
}
