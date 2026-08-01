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
