/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

  /** // violation '.* incorrect .* level 2, expected is 0, .* same .* as line 13.'
   *
   */
public class InputCommentsIndentationJavadoc {

// violation below '.* incorrect .* level 0, expected is 4, .* same .* as line 17.'
/** some comment */
    int i;

        /** // violation '.* incorrect .* level 8, expected is 4, .* same .* as line 22.'
         *
         */
    void foo() {}

    enum Bar {
        // violation below '.* incorrect .* level 10, expected is 8, .* same .* as line 27.'
          /** some comment */
        A;
    }

}
