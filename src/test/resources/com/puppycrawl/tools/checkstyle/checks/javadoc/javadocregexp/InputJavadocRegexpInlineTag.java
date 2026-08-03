/*
JavadocRegexp
format = temporary|beta
ignoreCase = (default)false
ignoreMarkup = true
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocregexp;

public class InputJavadocRegexpInlineTag {

    // violation below 'Javadoc content matches'
    /**
     * Returns {@code temporary} value.
     */
    void invalidCodeTag() {
    }

    // violation below 'Javadoc content matches'
    /**
     * Returns {@literal beta} value.
     */
    void invalidLiteralTag() {
    }

    /**
     * Returns stable value.
     */
    void validInlineTag() {
    }
}
