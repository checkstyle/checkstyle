/*
PreferLiteralJavadocInlineTag
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.preferliteraljavadocinlinetag;

/**
 * Correct usage with Javadoc inline tags.
 * Returns {@code true} if valid.
 * See {@link #validate()} for details.
 * Use {@literal <T>} for generics.
 * Test {@code a & b > c}.
 * Test {@code String str = "name"}.
 */
public class InputPreferLiteralJavadocInlineTagCorrect {

    /**
     * Method with proper inline tags.
     *
     * @return {@code true} always
     */
    public boolean validate() {
        return true;
    }
}
