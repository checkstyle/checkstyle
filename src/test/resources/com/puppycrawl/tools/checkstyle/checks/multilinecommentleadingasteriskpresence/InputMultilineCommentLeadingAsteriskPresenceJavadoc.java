/*
MultilineCommentLeadingAsteriskPresence

*/
// violation 4 lines above 'Multiline comment lines 2, 3 should start with leading asterisk'

package com.puppycrawl.tools.checkstyle.checks.multilinecommentleadingasteriskpresence;

public class InputMultilineCommentLeadingAsteriskPresenceJavadoc {

    /**
       This method has javadoc

     * @param a array of integers
     */
    void method(int... a) {
        return;
    }

    /**
       Line 1
       Line 2
       Line 3
     */
    void foo() {}

    /* This method is empty. */
    void foo1() {}

    /** Line 1

        Line 3

        Line 4
      */
    void bar() {}
}
