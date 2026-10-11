/*
JavadocEndCommentDelimiter


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocendcommentdelimiter;

public class InputJavadocEndCommentDelimiterCorrect {

    /*
     * Normal block comments are ignored, even with an unusual end.
     **/
    int normalBlockComment;

    /** Valid single-line Javadoc. */
    void singleLine() {}

    /** Multiplication uses a * b. */
    void contentWithAsterisk() {}

    /** Emphasis ** in the middle is fine. */
    void middleAsterisks() {}

    /** Asterisk before delimiter is separated by space * */
    void separatedAsterisk() {}

    /**
     * Valid Javadoc.
     */
    void multiLine() {}

    /**
     * The content line can end with an asterisk. *
     */
    void contentLineEndsWithAsterisk() {}

    /**
     * Closing delimiter can be at column zero.
*/
    void closingDelimiterAtColumnZero() {}

    void ignoredInvalidPosition() {
        /** This is not a valid Javadoc position. **/
        int value = 0;
    }
}
