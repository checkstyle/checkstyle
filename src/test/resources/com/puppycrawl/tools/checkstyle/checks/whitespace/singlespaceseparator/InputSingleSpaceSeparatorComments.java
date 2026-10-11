/*
SingleSpaceSeparator
validateComments = true


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.singlespaceseparator;

public class InputSingleSpaceSeparatorComments {
    /* always correct */ int i = 0;
    int   /* wrong if X is enabled */     j = 0;
    // 2 violations above:
    //     'Use a single space to separate non-whitespace characters.'
    //     'Use a single space to separate non-whitespace characters.'
    int k;   // violation 'Use a single space to separate non-whitespace'

    /**
     * Always correct
     */
    void foo() {
        /* Always correct */
        int  a = 0;	// <- a tab
        // 2 violations above:
        //     'Use a single space to separate non-whitespace characters.'
        //     'Use a single space to separate non-whitespace characters.'
    // violation below 'Use a single space to separate non-whitespace'
    }  // Wrong if X is enabled
}
