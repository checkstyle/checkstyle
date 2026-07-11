package com.openjdk.checkstyle.test.chapterformatting.ruleindentation;

// violation 2 lines above 'Header is missing*'

public class InputArrayInitIndentInvalid {

    int[] values = {
    1, 2, 3
    // violation above '.* incorrect indentation level 4, expected .* 8, 12.'
    };
}
