/*
BlockCommentEndPosition
strategy = (default)alone_or_singleline


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.blockcommentendposition;

/** Testing class. */
public class InputBlockCommentEndPositionCodeAfterEnd {

    /**
     * Code follows block comment end on same line. */ int a = 10;
    // violation above 'Block comment end must be on new line.'

    /**
     * Code follows block comment end on same line. */ void method() {}
    // violation above 'Block comment end must be on new line.'

    /**
     * Trailing line comment after end is fine.
     */ // ok
    int b = 20;

    /**
     * Multi-line Javadoc with proper end position.
     */
    int c = 30;

    /** Singleline Javadoc is allowed. */
    int d = 40;

}
