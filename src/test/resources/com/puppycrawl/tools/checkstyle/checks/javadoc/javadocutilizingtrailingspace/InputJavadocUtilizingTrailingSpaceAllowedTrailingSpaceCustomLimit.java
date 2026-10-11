/*
JavadocUtilizingTrailingSpace
allowedTrailingSpace = 20
ignorePattern = (default)href\\s*=\\s*"[^"]*"|http://|https://|ftp://
ignoreTooShortPattern = (default)[.:,]$
lineLimit = 100
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for a limit of 100 whose last 20 columns do not have to be used.
 */
public class InputJavadocUtilizingTrailingSpaceAllowedTrailingSpaceCustomLimit {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public void wrappedAtEighty() { }

    // violation 2 lines below 'Line under-utilized (62/100). Words from below could be moved up'
    /**
     * Once this is done, it is highly recommended to call for
     * the destroy method to close and remove the listeners.
     */
    public void stopsEarly() { }

    /**
     * Once this is done, it is highly recommended to call for the destroy method to close.
     */
    public void longerThanUsedColumns() { }
}
