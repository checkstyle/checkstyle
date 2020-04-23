package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

import java.io.Serializable;

/**
 * Config:
 * offset = 4
 */
public class InputJavadocTagContinuationIndentationBlockTag {

    /**
     * Example from issue 5711.
     * Returns the value represented by the specified string of the specified
     * type. Returns 0 for types other than float, double, int, and long.
     * @param text the string to be parsed.
     * @param type the token type of the text. Should be a constant of
     * {@link TokenTypes}. // violation
     * @return the double value represented by the string argument.
     */
    public static double parseDouble(String text, int type) {
        return 0;
    }

    /**
     * Javadoc.
     *
     * @param x this line is normal
     * {@code this} line is wrongly indented // violation
     */
    public void newlineThenBlockTag(int x) {
        // do stuff
    }

    /**
     * Not enough indentation.
     *
     * @param x this line is normal
     *   {@code this} line is wrongly indented // violation
     */
    public void partialIndent(int x) {
        // do stuff
    }

    /**
     * There can be a newline but nothing follows it.
     *
     * @param x input
     * @return itself
     * */ // ok
    public int identity(int x) {
        return x;
    }

}
