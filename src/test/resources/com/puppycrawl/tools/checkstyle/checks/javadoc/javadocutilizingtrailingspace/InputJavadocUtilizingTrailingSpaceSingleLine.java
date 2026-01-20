/*
JavadocUtilizingTrailingSpace
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for single-line Javadoc comments.
 */
public class InputJavadocUtilizingTrailingSpaceSingleLine {

    /** Simple single-line Javadoc. */
    public void simple() { }

    /** Returns the value. */
    public int getValue() {
        return 0;
    }

    /** @return the count */
    public int getCount() {
        return 0;
    }

    /** @param value the input */
    public void setValue(int value) { }

    /** Does nothing. */
    public void noop() { }

    /** Gets the name. */
    public String getName() {
        return "";
    }

    /** {@link Object} reference. */
    public void withLink() { }

    /** {@code true} value. */
    public boolean isTrue() {
        return true;
    }

    /** http://example.com */
    public void withUrl() { }

    // violation below 'Line is longer than'
    /** This single-line Javadoc is quite long but should not exceed the eighty character limit. */
    public void longerSingleLine() { }

    // violation below 'Line is longer than'
    /** This single-line Javadoc is way too long and definitely exceeds the eighty character limit that has been set. */
    public void tooLongSingleLine() { }

    /** <b>Bold</b> text. */
    public void withHtml() { }

    /** @deprecated Use other method. */
    @Deprecated
    public void deprecated() { }
}
