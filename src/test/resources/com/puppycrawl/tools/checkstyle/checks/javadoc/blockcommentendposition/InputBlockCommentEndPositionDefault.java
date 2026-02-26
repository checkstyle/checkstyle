/*
BlockCommentEndPosition
strategy = (default)alone_or_singleline


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.blockcommentendposition;

/** Testing class. */ // ok, allowed singleline Javadoc
public class InputBlockCommentEndPositionDefault {

    /** This is a singleline Javadoc on a field. */
    int a = 10;

    /**
     * This is a testing multi-line Javadoc on field.
     */
    int b = 20;

    /**
     * This is a violation of block comment end position on field. */
    int c = 30;
    // violation 2 lines above ''BLOCK_COMMENT_END' must be on the new line.'

    /** This is a singleline Javadoc on a method. */
    void singleLine() {}

    /**
     * This is testing multi-line Javadoc comment on method.
     */
    void ok() {}

    /**
     * This is a violation of block comment end position on method. */
    void violation() {}
    // violation 2 lines above ''BLOCK_COMMENT_END' must be on the new line.'

}
