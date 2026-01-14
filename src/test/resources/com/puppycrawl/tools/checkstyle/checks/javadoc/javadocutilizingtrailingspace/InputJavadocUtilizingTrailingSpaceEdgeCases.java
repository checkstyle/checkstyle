/*
JavadocUtilizingTrailingSpace
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for edge cases and corner scenarios.
 */
public class InputJavadocUtilizingTrailingSpaceEdgeCases {

    // violation 2 below 'Line is smaller than'
    /**
     * a
     * b
     */
    public void singleCharLines() { }

    /** x */
    public void minimalSingleLine() { }

    /**
     * Word.
     */
    public void oneWordLine() { }

    /**
     *
     */
    public void emptyContent() { }

    /**
     */
    public void minimalJavadoc() { }

    /**
     * Line ending with special chars: !@#$%^&*()
     */
    public void specialChars() { }

    /**
     * Unicode: \u00e9\u00e8\u00ea \u4e2d\u6587 \u65e5\u672c\u8a9e
     */
    public void unicodeChars() { }

    /**
     * Multiple  spaces   between    words.
     */
    public void multipleSpaces() { }

    /**
     * Tabs	between	words.
     */
    public void tabCharacters() { }

    /**
     *        Leading         spaces         everywhere.
     */
    public void excessiveWhitespace() { }

    /**
     * Numbers: 1234567890 and more 9876543210.
     */
    public void numbersOnly() { }

    /**
     * @param value123 numeric param name
     */
    public void numericParamName(int value123) { }

    /**
     * Line ends at exactly column 80 x x x x x x x x x x x x x x x x x x x x x
     */
    public void exactlyAtLimit() { }

    // violation 2 below 'Line is longer than'
    /**
     * Line ends off column 81 x x x x x x x x x x x x x x x x x x x x x x x x x
     */
    public void justOverLimit() { }

    /**
     * {@literal <not-a-tag>} literal content.
     */
    public void literalTag() { }

    /**
     * {@value #CONSTANT} reference to constant.
     */
    public void valueTag() { }

    /** Constant for value tag test. */
    public static final int CONSTANT = 10;
}
