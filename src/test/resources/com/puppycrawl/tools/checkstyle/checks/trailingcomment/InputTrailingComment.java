/*
TrailingComment
format = (default)^[\\s});]*$
legalComment = (default)(null)


*/

package com.puppycrawl.tools.checkstyle.checks.trailingcomment;

public class InputTrailingComment {
    // violation below 'Don't use trailing comments.'
    int i; // don't use trailing comments :)
    // it fine to have comment w/o any statement
    /* good c-style comment. */
    // violation below 'Don't use trailing comments.'
    int j; /* bad c-style comment. */
    // violation below 'Don't use trailing comments.'
    void method1() { /* some c-style multi-line
                        comment*/
        Runnable r = (new Runnable() {
                public void run() {
                }
            }); /* we should allow this */
    } // we should allow this
    /*
      Let's check multi-line comments.
    */
    // violation below 'Don't use trailing comments.'
    /* c-style */ // cpp-style
    /* c-style 1 */ /*c-style 2 */
     // violation above 'Don't use trailing comments.'
    /* package */ void method2(long ms /* we should ignore this */) {
        /* comment before text */int z;
        /* int y */int y/**/;
    }

    // violation 4 lines below 'Don't use trailing comments.'
    /**
     * comment with trailing space.
     */
    final static public String NAME="Some Name"; // NOI18N
     // violation below 'Don't use trailing comments.'
    final static public String NAME2="Some Name"; /*NOI18N*/
     // violation below 'Don't use trailing comments.'
    String NAME3="Some Name"; /*NOI18N
*/
    /* package */ void method3() {

        // ok, format NOT FOUND
    }

    private static class TimerEntry {
        /* ok */ final String operation = null;
        /* ok */ final long start = 0L;
    }

    /**
     * should be above this line.
     **/
    /* package */ void addError() {
    }
}
