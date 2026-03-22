/* // violation
TrailingComment
format = NOT MATCH
legalComment = (default)(null)


*/

package com.puppycrawl.tools.checkstyle.checks.trailingcomment;

public class InputTrailingComment3 {
    int i; // don't use trailing comments :) // violation
    // it fine to have comment w/o any statement // violation
    /* good c-style comment. */ // violation
    int j; /* bad c-style comment. */ // violation
    void method1() { /* some c-style multi-line // violation
                        comment*/
        Runnable r = (new Runnable() {
                public void run() {
                }
            }); /* we should allow this */ // violation
    } // we should allow this // violation
    /* // violation
      Let's check multi-line comments.
    */
    /* c-style */ // cpp-style // violation
    /* c-style 1 */ /*c-style 2 */ // violation

    /* package */ void method2(long ms /* we should ignore this */) {
        /* comment before text */int z;
        /* int y */int y/**/;
    }

    /** // violation
     * comment with trailing space.
     */
    final static public String NAME="Some Name"; // NOI18N // violation
    final static public String NAME2="Some Name"; /*NOI18N*/ // violation
    String NAME3="Some Name"; /*NOI18N // violation
*/
    /* package */ void method3() {
        /* violation on this block */ // violation
        // violation here for format NOT FOUND // violation
    }

    private static class TimerEntry {
        /* ok */ final String operation = null;
        /* ok */ final long start = 0L;
    }

    /** // violation
     * violation above this line.
     **/
    /* package */ void addError() {
    }
}
