/*
MultilineCommentLeadingAsteriskPresence

*/
// violation 4 lines above 'Multiline comment lines 2, 3 should start with leading asterisk'

package com.puppycrawl.tools.checkstyle.checks.multilinecommentleadingasteriskpresence;

public class InputMultilineCommentLeadingAsteriskPresence2 {

    // violation below 'Multiline comment lines 14 should start with leading asterisk'
    /*
     * This method does nothing.

     */
    void method(int... a) {
        return;
    }

    // violation below 'Multiline comment lines 23, 24 should start with leading asterisk'
    /* Line 1
     * Line 2
       Line 3
       Line 4 */
    void foo() {}

    /* This method is empty. */
    void foo1() {}

    // violation below 'Multiline comment lines 32, 34 should start with leading asterisk'
    /* Line 1

     * Line 3

     * Line 4
     */
    void bar() {}
}
