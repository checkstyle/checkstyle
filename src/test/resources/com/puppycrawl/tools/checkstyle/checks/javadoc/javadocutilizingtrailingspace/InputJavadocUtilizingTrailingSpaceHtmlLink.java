/*
JavadocUtilizingTrailingSpace
ignorePattern = (default)href\\s*=\\s*"[^"]*"|http://|https://|ftp://
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for links and other HTML elements in the values of block tags.
 */
public class InputJavadocUtilizingTrailingSpaceHtmlLink {

    /**
     * Creates an instance of the Companies House API.
     *
     * @param apiKey API key obtained from the Companies House website.
     * @see <a href="https://find-and-update.company-information.service.gov.uk/">Companies House Website</a>
     */
    public void linkIsWholeBlockTagValue(String apiKey) { }

    /**
     * @see <a href="https://find-and-update.company-information.service.gov.uk/">Companies House</a>
     *     the website of the registry
     */
    public void linkWithContinuationLine() { }

    // ok, a line that has a link is not reported as too long
    /**
     * @see <a href="https://find-and-update.company-information.service.gov.uk/">Companies House</a> for more
     */
    public void linkWithTextAfter() { }

    // ok, a line that has a link is not reported as too long
    /**
     * @see <a href="https://find-and-update.company-information.service.gov.uk/">Companies
     *     House Website</a>
     */
    public void linkOnSeveralLines() { }

    // violation 2 lines below 'Line is longer than 80 characters (found 95).'
    /**
     * @return <b>a value that is bold and much too long to fit within the configured limit</b>
     */
    public int elementIsWholeBlockTagValue() { return 0; }

    // ok, a line that has a link is not reported as too long
    /**
     * @return the site of the company, see <a href="https://find-and-update.company.org/">site</a>
     */
    public int linkAfterText() { return 0; }

    // ok, a line that has a link is not reported as too long
    /**
     * <a href="https://find-and-update.company-information.service.gov.uk/">Companies House</a>
     */
    public void linkStartsLine() { }

    // ok, a line that has a link is not reported as too long
    /**
     * @see
     *<a href="https://find-and-update.company-information.service.gov.uk/">Companies House</a>
     */
    public void linkWithoutSpaceAfterAsterisk() { }

    // ok, a line that has a link is not reported as too long
    /**
     * Refer to the documentation of <a href="https://find-and-update.company-info.org/">
     * Companies House</a>
     */
    public void longStartTagEndsLine() { }

    /**
     * <a href="https://find-and-update.company-information.service.gov.uk/company/00000006/filing-history">Companies House filing history of the first registered company</a>
     */
    public void veryLongLinkOnly() { }

    /**
     * @see <a href="https://find-and-update.company-information.service.gov.uk/company/00000006/filing-history">
     *     Companies House filing history</a>
     */
    public void veryLongLinkInBlockTagOnSeveralLines() { }
}
