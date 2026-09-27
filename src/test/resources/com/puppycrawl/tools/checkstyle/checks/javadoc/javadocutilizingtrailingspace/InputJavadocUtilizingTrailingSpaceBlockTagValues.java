/*
JavadocUtilizingTrailingSpace
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for line length and utilization of Javadoc block tag values.
 */
public class InputJavadocUtilizingTrailingSpaceBlockTagValues {

    // violation 2 lines below 'Line is longer than 80 characters (found 131).'
    /**
     * @return Date which the company was converted / closed or dissolved. Refer to {@link #getCompanyStatus()} to determine which.
     */
    public int returnValueTooLong() {
        return 0;
    }

    /**
     * @return
     *     Date which the company was converted / closed or dissolved. Refer to
     *     {@link #getCompanyStatus()} to determine which.
     */
    public int correctedReturnValueTooLong() {
        return 0;
    }

    /**
     * @return Date which the company was converted / closed or dissolved. Refer
     * to {@link #getCompanyStatus()} to determine which.
     */
    public int returnValueWrapped() {
        return 0;
    }

    // violation 2 lines below 'Line under-utilized (29/80). Words from below could be moved up'
    /**
     * @param first the first
     *     parameter which has its description broken too early
     * @param second the second parameter
     */
    public void paramValueTooShort(int first, int second) { }

    /**
     * @param first the first parameter which has its description broken too
     *     early
     * @param second the second parameter
     */
    public void correctedParamValueTooShort(int first, int second) { }

    /**
     * Creates an instance of the Companies House API. Companies House allows
     * you to query information on registered companies in Britain.
     *
     * @param apiKey API key obtained from the Companies House website.
     * @see <a href="https://find-and-update.company-information.service.gov.uk/">Companies House Website</a>
     */
    public void seeWithLongAnchor(String apiKey) { }

    /**
     * Company status.
     *
     * @return the status
     */
    public int getCompanyStatus() {
        return 0;
    }

    // violation 2 lines below 'Line is longer than 80 characters (found 87).'
    /**
     * @return {@link com.very.wide.bundles.name.that.exceeds.limit.SomeClass#method()}
     */
    public int returnWithLongInlineTag() {
        return 0;
    }

    // violation 2 lines below 'Line is longer than 80 characters (found 81).'
    /**
     * @return https://example.com/this/is/a/very/long/url/that/exceeds/the/limit
     */
    public int returnWithLongUrl() {
        return 0;
    }
}
