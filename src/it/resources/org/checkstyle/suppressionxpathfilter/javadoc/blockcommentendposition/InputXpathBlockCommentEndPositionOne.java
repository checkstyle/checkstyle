/*
BlockCommentEndPosition
strategy = (default)alone_or_singleline


*/

package org.checkstyle.suppressionxpathfilter.javadoc.blockcommentendposition;

public class InputXpathBlockCommentEndPositionOne {

    // violation 2 lines below ''BLOCK_COMMENT_END' must be on the new line.'
    /**
     * Comment. */
    void test() {}
}
