/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

  /**
   * // violation
   */
public class InputCommentsIndentationJavadoc {

/** violation */
    int i;

        /**
         * // violation
         */
    void foo() {}

    enum Bar {
          /** violation */
        A;
    }

}
