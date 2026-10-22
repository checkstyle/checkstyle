/*
JavadocUtilizingTrailingSpace
ignorePattern = (default)href\\s*=\\s*"[^"]*"|http://|https://|ftp://
ignoreTooShortPattern = (default)[.:,]$
lineLimit = (default)80
validateOnlyJoinableLines = (default)true
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for lines that have whitespace after the punctuation.
 */
public class InputJavadocUtilizingTrailingSpaceIgnoreTooShortPatternSpaces {

    /**
     * Reads the file. 
     * Returns its lines when the file exists.
     */
    public void periodThenSpace() { }

    /**
     * If the file is missing,  
     * an empty list is returned.
     */
    public void commaThenSpaces() { }

    // violation 2 lines below 'Line under-utilized (26/80). Words from below could be moved up'
    /**
     * Reads the file and 
     * returns its lines.
     */
    public void noPunctuationThenSpace() { }
}
