package com.openjdk.checkstyle.test.chapterformatting.ruleindentation;

/** Invalid tab-indented examples for OpenJDK style section 3.7. */
public class InputFileTabCharacterInvalid {

    //	has tab indentation
    // violation above 'Line contains a tab character.'
    private int value;

    private void method() {
        value++;
    }
}
