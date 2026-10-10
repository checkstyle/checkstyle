/*
JavadocUtilizingTrailingSpace
ignorePattern = (default)href\\s*=\\s*"[^"]*"|http://|https://|ftp://
ignoreTooShortPattern = [.:,](</\\w+>)?$
lineLimit = (default)80
validateOnlyJoinableLines = (default)true
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for a pattern that allows a closing HTML tag after punctuation.
 */
public class InputJavadocUtilizingTrailingSpaceIgnoreTooShortPatternCustom {

    /**
     * Reads the <b>file.</b>
     * Returns its lines when the file exists.
     */
    public void endsWithClosingTag() { }

    /**
     * Reads the file.
     * Returns its lines when the file exists.
     */
    public void endsWithPeriod() { }

    // violation 2 lines below 'Line under-utilized (22/80). Words from below could be moved up'
    /**
     * Reads the file;
     * returns its lines when the file exists.
     */
    public void endsWithSemicolon() { }

    // violation 2 lines below 'Line under-utilized (28/80). Words from below could be moved up'
    /**
     * Reads the <b>file</b>
     * and returns its lines.
     */
    public void endsWithOtherClosingTag() { }
}
