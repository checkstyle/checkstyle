/*
JavadocUtilizingTrailingSpace
ignorePattern = (default)href\\s*=\\s*"[^"]*"|http://|https://|ftp://
ignoreTooShortPattern = (default)[.:,]$
lineLimit = (default)80
validateOnlyJoinableLines = false
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

public class InputJavadocUtilizingTrailingSpaceSupplementaryFirstWord {

    // violation 2 lines below 'Line under-utilized (70/80). Words from below could be moved up'
    /**
     * Only the first word of the next line is pulled up; it fits here
     * word😀with an emoji, followed by more words that stay below
     */
    void firstWord() {
    }
}
