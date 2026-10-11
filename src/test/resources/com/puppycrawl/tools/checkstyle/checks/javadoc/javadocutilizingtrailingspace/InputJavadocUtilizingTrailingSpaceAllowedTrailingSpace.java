/*
JavadocUtilizingTrailingSpace
allowedTrailingSpace = 20
ignorePattern = (default)href\\s*=\\s*"[^"]*"|http://|https://|ftp://
ignoreTooShortPattern = (default)[.:,]$
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for the last 20 columns of the limit that do not have to be used.
 */
public class InputJavadocUtilizingTrailingSpaceAllowedTrailingSpace {

    /**
     * Reads the settings of the service from the given file
     * path.
     */
    public void nextWordEndsInAllowedSpace() { }

    // violation 2 lines below 'Line under-utilized (49/80). Words from below could be moved up'
    /**
     * Reads the settings of the service from the
     * given file path.
     */
    public void nextWordEndsBeforeAllowedSpace() { }

    // violation 2 lines below 'Line under-utilized (55/80). Words from below could be moved up'
    /**
     * Reads the settings of the service from the given
     * file path.
     */
    public void nextWordEndsAtLastUsedColumn() { }

    /**
     * Reads the settings of the services from the given
     * file path.
     */
    public void nextWordEndsAtFirstAllowedColumn() { }

    /**
     * Reads the settings of the service from the given file path here.
     */
    public void longerThanUsedColumns() { }

    // violation 2 lines below 'Line is longer than 80 characters (found 88).'
    /**
     * Reads the settings of the service from the given file path, when the file exists.
     */
    public void longerThanLimit() { }
}
