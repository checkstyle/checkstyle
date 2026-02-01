/*
JavadocUtilizingTrailingSpace
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for {@code} inline tag handling.
 */
public class InputJavadocUtilizingTrailingSpaceInlineCode {

    /**
     * {@code someVeryLongMethodNameThatExceedsTheLimitWhenCombinedWithOtherText}
     */
    public void longCodeAtStart() { }

    // violation 2 lines below 'Line is longer than 80 characters (found 87).'
    /**
     * Use the {@code someVeryLongMethodNameThatExceedsLimitWhenInMiddleOfLine} method.
     */
    public void longCodeInMiddleViolation() { }

    /**
     * The value should be {@code true} or {@code false}.
     */
    public void shortCodeTags() { }

    /**
     * Returns {@code null} if not found.
     */
    public Object returnsNull() {
        return null;
    }

    /**
     * Set to {@code 0} for default behavior.
     */
    public void defaultValue() { }

    /**
     * The method accepts {@code String}, {@code int}, and {@code boolean}
     * parameters for various configurations.
     */
    public void multipleCodeTags() { }

    /** Returns {@code true}. */
    public boolean singleLineCode() {
        return true;
    }

    /**
     * @param flag set to {@code true} to enable, {@code false} to disable
     */
    public void paramWithCode(boolean flag) { }
}
