/*
MultilineCommentLeadingAsteriskPresence

*/
// violation 3 lines above 'Multiline comment should start with leading asterisk'
// violation 3 lines above 'Multiline comment should start with leading asterisk'

package com.puppycrawl.tools.checkstyle.checks.multilinecommentleadingasteriskpresence;

public class InputMultilineCommentLeadingAsteriskPresenceCorrect {

    /*
     * This method does nothing.
     */
    void method(int... a) {
        return;
    }

    /* Line 1
     * Line 2
     * Line 3
     * Line 4 */
    void foo() {}

    /* This method is empty. */
    void foo1() {}

    /* Line 1
     *
     * Line 3
     *
     * Line 4
     */
    void bar() {}
}
