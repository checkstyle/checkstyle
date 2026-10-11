/*
SingleSpaceSeparator
validateComments = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.singlespaceseparator;

public class InputSingleSpaceSeparatorComments2 {
    /* always correct */ int i = 0;
    int   /* wrong if X is enabled */     j = 0;
    int k;   // Multiple whitespaces before comment

    /**
     * Always correct
     */
    void foo() {
        /* Always correct */
        // violation below 'Use a single space to separate non-whitespace characters.'
        int  a = 0;
    }  // Wrong if X is enabled
}
