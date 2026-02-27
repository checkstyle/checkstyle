/*
BlockCommentEndPosition
strategy = alone


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.blockcommentendposition;

/**
 * This class contains examples of singleline and multi-line
 * Javadoc with alone property.
 */
public class InputBlockCommentEndPositionAlone {

    /** Singleline Javadoc not allowed with alone property. */
    public int a = 10;
    // violation 2 lines above ''BLOCK_COMMENT_END' must be on the new line.'

    /**
     * This is a multi-line Javadoc allowed with alone property.
     */
    private int b = 10;

    /**
     * This is gives violation because it doesn't follow proper
     * structure multline Javadoc. */
    protected int c = 10;
    // violation 2 lines above ''BLOCK_COMMENT_END' must be on the new line.'

    /**
     * Testing multi-line Javadoc,
     * Testing multi-line Javadoc,
     * Testing multi-line Javadoc,
     * Testing multi-line Javadoc,
     * Testing multi-line Javadoc,
     * Testing multi-line Javadoc.
     */
    public void test() {}

    /**
     * Testing multi-line Javadoc,
     * Testing multi-line Javadoc,
     * Testing multi-line Javadoc,
     * Testing multi-line Javadoc,
     * Testing multi-line Javadoc. */
    public void violation() {}
    // violation 2 lines above ''BLOCK_COMMENT_END' must be on the new line.'

    /** Testing single line on enum definition. */
    public enum State {
        // violation 2 lines above ''BLOCK_COMMENT_END' must be on the new line.'

        /** Testing single-line Javadoc on enum constant. */
        NEW,
        // violation 2 lines above ''BLOCK_COMMENT_END' must be on the new line.'

        /**
         * Testing multi-line Javadoc on enum constant.
         */
        SUCCESS,

        /**
         * Testing multi-line Javadoc on enum constant with violation case.  */
        FAILURE,
        // violation 2 lines above ''BLOCK_COMMENT_END' must be on the new line.'
    }
}
