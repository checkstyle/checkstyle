/*
BlockCommentEndPosition
strategy = (default)alone_or_singleline


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.blockcommentendposition;

/** Testing class. */
public class InputBlockCommentEndPositionCodeAfterEnd {

    /**
     * Code follows block comment end on same line.
     */ int a = 10;
    // violation above 'Block comment end must be on new line.'

    /**
     * Code follows block comment end on same line.
     */ void method() {}
    // violation above 'Block comment end must be on new line.'

    /**
     * Trailing line comment after end is fine.
     */ // ok, trailing line comment after end delimiter is allowed
    int b = 20;

    /**
     * Multi-line Javadoc with proper end position.
     */
    int c = 30;

    /** Singleline Javadoc is allowed. */
    int d = 40;

    /**
     * Trailing block comment after end delimiter is fine.
     */     /* ok, trailing block comment after end delimiter is allowed */
    int e = 50;

    /**
     * Code follows block comment end after trailing block comment.
     */     /* comment */ int f = 60;
    // violation above 'Block comment end must be on new line.'

    /**
     * Multiple trailing comments after end delimiter are fine.
     */ /* comment 1 */ /* comment 2 */ // comment 3
    int g = 70;

}
