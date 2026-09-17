/*
TrailingComment
format = NOT MATCH
legalComment = violation (above|below|[0-9]+ lines)


*/
// violation 7 lines above 'Don't use trailing comments.'

package com.puppycrawl.tools.checkstyle.checks.trailingcomment;

public class InputTrailingComment3 {
    // violation below 'Don't use trailing comments.'
    int i; // don't use trailing comments :)
    // violation below 'Don't use trailing comments.'
    // it fine to have comment w/o any statement
    // violation below 'Don't use trailing comments.'
    /* good c-style comment. */
    // violation below 'Don't use trailing comments.'
    int j; /* bad c-style comment. */
    // violation below 'Don't use trailing comments.'
    void method1() { /* some c-style multi-line
                        comment*/
        Runnable r = (new Runnable() {
                public void run() {
                }
            // violation below 'Don't use trailing comments.'
            }); /* we should allow this */
    // violation below 'Don't use trailing comments.'
    } // we should allow this
    // violation below 'Don't use trailing comments.'
    /*
      Let's check multi-line comments.
    */
    // violation below 'Don't use trailing comments.'
    /* c-style */ // cpp-style
    // violation below 'Don't use trailing comments.'
    /* c-style 1 */ /*c-style 2 */

    /* package */ void method2(long ms /* we should ignore this */) {
        /* comment before text */int z;
        /* int y */int y/**/;
    }

    // violation 2 lines below 'Don't use trailing comments.'
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
        // violation below 'Don't use trailing comments.'
        /* bla on this block */
        // violation below 'Don't use trailing comments.'
        // ok, format NOT FOUND
    }

    private static class TimerEntry {
        /* ok */ final String operation = null;
        /* ok */ final long start = 0L;
    }

    // violation below 'Don't use trailing comments.'
    /**
     * should be above this line.
     **/
    /* package */ void addError() {
    }
}
