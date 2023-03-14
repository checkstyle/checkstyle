/*
TrailingComment
format = (default)^[\s});]*$
legalComment = ^NOI18N$


*/

package com.puppycrawl.tools.checkstyle.checks.trailingcomment;

public class InputTrailingComment2 {
    // violation below
    int i; // don't use trailing comments :)
    // it fine to have comment w/o any statement
    /* good c-style comment. */
    // violation below
    int j; /* bad c-style comment. */
    // violation below
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
    // violation below
    /* c-style */ // cpp-style
    // violation below
    /* c-style 1 */ /*c-style 2 */

    /* package */ void method2(long ms /* we should ignore this */) {
        /* comment before text */int z;
        /* int y */int y/**/;
    }

    /**
     * comment with trailing space.      */
    // violation below
    final static public String NAME="Some Name"; // NOI18N
    final static public String NAME2="Some Name"; /*NOI18N*/
    // violation below
    String NAME3="Some Name"; /*NOI18N
*/
    /* package */ void method3() {
        /* violation on this block */
        // violation here for format NOT FOUND
    }

    private static class TimerEntry {
        /* ok */ final String operation = null;
        /* ok */ final long start = 0L;
    }

    /**
     * violation above this line.
     **/
    /* package */ void addError() {
    }
}
