/*
JavadocUtilizingTrailingSpace
ignorePattern = (default)href\\s*=\\s*"[^"]*"|http://|https://|ftp://
ignoreTooShortPattern = (default)[.:,]$
lineLimit = (default)80
validateOnlyJoinableLines = (default)true
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

public class InputJavadocUtilizingTrailingSpaceSupplementaryCharacters {

    /**
     * The emoji 😀 counts as one character, so this line is exactly at the limit
     */
    void lineAtLimit() {
    }

    // violation 2 lines below 'Line under-utilized (25/80). Words from below could be moved up'
    /**
     * This line is short
     * and the next line with 😀 would fit after it right here
     */
    void shortLine() {
    }
}
