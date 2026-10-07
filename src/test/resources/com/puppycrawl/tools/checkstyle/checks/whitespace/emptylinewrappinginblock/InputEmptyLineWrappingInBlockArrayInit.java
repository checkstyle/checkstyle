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

    private static final int[] MULTILINE = {
        1, // violation above''{' must have exactly one empty line after.'
        2
    }; // violation ''}' must have exactly one empty line before'

    @Retention(RetentionPolicy.RUNTIME)
    @interface WithArray {
        String[] value();
    }

    @WithArray({ // violation ''{' must have exactly one empty line after.'
        "a",
        "b"
    }) // violation ''}' must have exactly one empty line before'
    void multilineAnnotation() { }
}
