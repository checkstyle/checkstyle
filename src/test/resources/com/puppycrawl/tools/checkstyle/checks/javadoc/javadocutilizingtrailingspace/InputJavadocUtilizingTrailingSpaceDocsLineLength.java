/*
JavadocUtilizingTrailingSpace
ignorePattern = (default)href\\s*=\\s*"[^"]*"|http://|https://|ftp://
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for the line length examples of the documentation.
 */
public class InputJavadocUtilizingTrailingSpaceDocsLineLength {

    // violation 2 lines below 'Line under-utilized (56/80). Words from below could be moved up'
    /**
     * Not part of the API. This is returned when a type
     * not known to this wrapper is returned.
     */
    public void tooShort() { }

    /**
     * Not part of the API. This is returned when a type not known to this
     * wrapper is returned.
     */
    public void tooShortFixed() { }

    // violation 2 lines below 'Line is longer than 80 characters (found 88)'
    /**
     * Creates a client for the service and loads its settings from the given file path.
     */
    public void tooLong() { }

    /**
     * Creates a client for the service and loads its settings from the given
     * file path.
     */
    public void tooLongFixed() { }
}
