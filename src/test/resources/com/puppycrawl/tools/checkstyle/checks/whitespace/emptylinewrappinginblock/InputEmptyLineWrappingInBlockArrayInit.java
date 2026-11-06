/*
EmptyLineWrappingInBlock
tokens = CLASS_DEF, ARRAY_INIT, ANNOTATION_ARRAY_INIT, CTOR_DEF, RECORD_DEF, COMPACT_CTOR_DEF
topSeparator = (default)empty_line
bottomSeparator = (default)empty_line

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylinewrappinginblock;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

 // violation below ''{' must have exactly one empty line after.'
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

     // violation below ''{' must have exactly one empty line after.'
    InputEmptyLineWrappingInBlockArrayInit() {
        int a = 0;
    } // violation ''}' must have exactly one empty line before'

    record Point(int x, int y) { // violation ''{' must have exactly one empty line after.'
        Point { // violation ''{' must have exactly one empty line after.'
            int a = 0;
        } // violation ''}' must have exactly one empty line before'
    } // violation ''}' must have exactly one empty line before'

}
