/*
JavadocUtilizingTrailingSpace
ignorePattern = ^$
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for URLs and links when no line is ignored.
 */
public class InputJavadocUtilizingTrailingSpaceIgnorePatternNone {

    // violation 2 lines below 'Line is longer than 80 characters (found 83).'
    /**
     * http://example.com/this/is/a/very/long/url/that/exceeds/the/configured/limit
     */
    public void longUrlAlone() { }

    // violation 2 lines below 'Line is longer than 80 characters (found 88).'
    /**
     * See the docs at http://example.com/this/is/a/very/long/url/exceeds to learn more.
     */
    public void longUrlInMiddle() { }

    // violation 2 lines below 'Line is longer than 80 characters (found 110).'
    /**
     * @see <a href="https://find-and-update.company-information.service.gov.uk/">Companies House Website</a>
     */
    public void linkIsWholeBlockTagValue() { }

    // violation 2 lines below 'Line is longer than 80 characters (found 92).'
    /**
     * @see <a href="https://find-and-update.company-information.service.gov.uk/">Companies
     *     House Website</a>
     */
    public void linkOnSeveralLines() { }

    /**
     * {@link com.example.registry.status.CompanyStatusRegistry#findStatus(String)}
     */
    public void longInlineTagAlone() { }

    /**
     * Check the resources at
     * http://example.com/short/url
     */
    public void urlIsNotMovedUp() { }
}
