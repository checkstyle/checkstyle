/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

// violation below '.* incorrect .* level 2, expected is 0, .* same .* as line 14.'
  /**
   *
   */
public class InputCommentsIndentationJavadoc {

// violation below '.* incorrect .* level 0, expected is 4, .* same .* as line 18.'
/** some comment */
    int i;

        // violation below '.* incorrect .* level 8, expected is 4, .* same .* as line 24.'
        /**
         *
         */
    void foo() {}

    enum Bar {
        // violation below '.* incorrect .* level 10, expected is 8, .* same .* as line 29.'
          /** some comment */
        A;
    }

}
