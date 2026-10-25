/*
BlockCommentEndPosition
strategy = (default)alone_or_singleline


*/

package org.checkstyle.suppressionxpathfilter.javadoc.blockcommentendposition;

public class InputXpathBlockCommentEndPositionOne {

    /**
     * Comment. */ // violation ''BLOCK_COMMENT_END' must be on the new line.'
    void test() {}
}
