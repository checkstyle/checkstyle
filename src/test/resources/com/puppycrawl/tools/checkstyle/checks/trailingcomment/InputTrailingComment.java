package com.puppycrawl.tools.checkstyle.checks.trailingcomment;

public class InputTrailingComment {
    int i; // don't use trailing comments :)
    // it fine to have comment w/o any statement
    /* good c-style comment. */
    int j; /* bad c-style comment. */
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
    /* c-style */ // cpp-style
    /* c-style 1 */ /*c-style 2 */

    void method2(long ms /* we should ignore this */) {
        /* comment before text */int z;
        /* int y */int y/**/;
    }

    /**
     * comment with trailing space
     */
    final static public String NAME="Some Name"; // NOI18N
    final static public String NAME2="Some Name"; /*NOI18N*/
    String NAME3="Some Name"; /*NOI18N
*/
    void method3() {
        /* Test Block comment */
        // Test single line comment
    }

    private static class TimerEntry {
        /* package */ final String operation = null;
        /* package */ final long start = 0l;
    }

    /**
     * Print an Emacs compliant line on the error stream.
     * If the column number is non zero, then also display it.
     **/
    public void addError() {
    }
}
