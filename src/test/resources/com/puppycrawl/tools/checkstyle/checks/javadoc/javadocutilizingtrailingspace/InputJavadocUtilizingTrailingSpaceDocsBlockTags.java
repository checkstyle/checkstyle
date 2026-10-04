/*
JavadocUtilizingTrailingSpace
ignorePattern = (default)href\\s*=\\s*"[^"]*"|http://|https://|ftp://
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for the block tag examples of the documentation.
 */
public class InputJavadocUtilizingTrailingSpaceDocsBlockTags {

    // violation 2 lines below 'Line is longer than 80 characters (found 82)'
    /**
     * @return Date the company was closed or dissolved, see {@link #getStatus()}.
     */
    public void blockTagTooLong() { }

    /**
     * @return
     *     Date the company was closed or dissolved, see {@link #getStatus()}.
     */
    public void blockTagValueOnNextLine() { }

    /**
     * @return Date the company was closed or dissolved, see
     * {@link #getStatus()}.
     */
    public void blockTagWrapped() { }
}
