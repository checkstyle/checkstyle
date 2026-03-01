/*
EmptyLineWrappingInBlock
tokens = ARRAY_INIT, ANNOTATION_ARRAY_INIT
topSeparator = (default)empty_line
bottomSeparator = (default)empty_line

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylinewrappinginblock;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class InputEmptyLineWrappingInBlockArrayInit {

    private static final int[] MULTILINE = { // violation 'Exactly one empty line is required after the opening brace.'
        1,
        2
    }; // violation 'Exactly one empty line is required before the closing brace.'

    @Retention(RetentionPolicy.RUNTIME)
    @interface WithArray {
        String[] value();
    }

    @WithArray({ // violation 'Exactly one empty line is required after the opening brace.'
        "a",
        "b"
    }) // violation 'Exactly one empty line is required before the closing brace.'
    void multilineAnnotation() { }
}
