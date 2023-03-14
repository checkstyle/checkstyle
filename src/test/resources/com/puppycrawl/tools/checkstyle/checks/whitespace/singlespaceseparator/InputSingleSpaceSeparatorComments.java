/*
SingleSpaceSeparator
validateComments = true


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.singlespaceseparator;

public class InputSingleSpaceSeparatorComments {
    /* always correct */ int i = 0;
    int   /* wrong if X is enabled */     j = 0; // 2 violations
    int k;   // violation

    /**
     * Always correct
     */
    void foo() {
        /* Always correct */
        int  a = 0;	// <- a tab // 2 violations
    }  // Wrong if X is enabled // violation
}
