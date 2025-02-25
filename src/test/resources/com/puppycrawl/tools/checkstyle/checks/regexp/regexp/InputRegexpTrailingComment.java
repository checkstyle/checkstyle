/*
Regexp
format = don't use trailing comments
message = (default)null
illegalPattern = true
duplicateLimit = (default)0
errorLimit = (default)100
ignoreComments = true


*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexp;

public class InputRegexpTrailingComment {
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
}
