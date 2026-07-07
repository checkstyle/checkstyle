/*
MultilineCommentLeadingAsteriskPresence

*/
// violation 3 lines above 'Multiline comment should start with leading asterisk'
// violation 3 lines above 'Multiline comment should start with leading asterisk'

package com.puppycrawl.tools.checkstyle.checks.multilinecommentleadingasteriskpresence;

public class InputMultilineCommentLeadingAsteriskPresence1 {

    // violation 2 lines below 'Multiline comment should start with leading asterisk'
    /*
    This method does nothing.
    */
    void method(int... a) {
        return;
    }

    // violation 4 lines below 'Multiline comment should start with leading asterisk'
    // violation 4 lines below 'Multiline comment should start with leading asterisk'
    // violation 4 lines below 'Multiline comment should start with leading asterisk'
    /* Line 1
    Line 2
    Line 3
    Line 4 */
    void foo() {}

    /* This method is empty. */
    void foo1() {}

    // violation 5 lines below 'Multiline comment should start with leading asterisk'
    // violation 5 lines below 'Multiline comment should start with leading asterisk'
    // violation 5 lines below 'Multiline comment should start with leading asterisk'
    // violation 5 lines below 'Multiline comment should start with leading asterisk'
    /* Line 1

       Line 3

       Line 4
     */
    void bar() {}
}
