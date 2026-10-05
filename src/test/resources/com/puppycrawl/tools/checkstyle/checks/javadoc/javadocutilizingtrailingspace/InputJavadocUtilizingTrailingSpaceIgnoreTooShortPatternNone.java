/*
JavadocUtilizingTrailingSpace
ignorePattern = (default)href\\s*=\\s*"[^"]*"|http://|https://|ftp://
ignoreTooShortPattern = ^$
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for lines that end with punctuation when no line is ignored.
 */
public class InputJavadocUtilizingTrailingSpaceIgnoreTooShortPatternNone {

    // violation 2 lines below 'Line under-utilized (22/80). Words from below could be moved up'
    /**
     * Reads the file.
     * Returns its lines when the file exists.
     */
    public void endsWithPeriod() { }

    // violation 2 lines below 'Line under-utilized (23/80). Words from below could be moved up'
    /**
     * Supported modes:
     * read, write and append.
     */
    public void endsWithColon() { }

    // violation 2 lines below 'Line under-utilized (30/80). Words from below could be moved up'
    /**
     * If the file is missing,
     * an empty list is returned.
     */
    public void endsWithComma() { }

    // violation 2 lines below 'Line under-utilized (25/80). Words from below could be moved up'
    /**
     * Reads the file and
     * returns its lines.
     */
    public void endsInMiddleOfSentence() { }

    // violation 2 lines below 'Line under-utilized (30/80). Words from below could be moved up'
    /**
     * Reads the file. Then it
     * returns its lines.
     */
    public void periodInMiddleOfLine() { }

    // violation 2 lines below 'Line under-utilized (22/80). Words from below could be moved up'
    /**
     * Reads the file.
     * Returns the lines of
     * it when the file exists.
     */
    public void lineAfterPeriodIsFreshStart() { }

    // violation 2 lines below 'Line under-utilized (25/80). Words from below could be moved up'
    /**
     * Reads the file and
     * returns the lines of
     * it to the caller.
     */
    public void laterLineEndsWithPeriod() { }

    // violation 2 lines below 'Line under-utilized (40/80). Words from below could be moved up'
    /**
     * @param path the path of the file,
     *     relative or absolute
     */
    public void blockTagValue() { }

    // violation 2 lines below 'Line is longer than 80 characters (found 87).'
    /**
     * Reads the whole file from the given path and returns every line of it as a list.
     * Returns an empty list when it is missing.
     */
    public void longLineEndsWithPeriod() { }
}
