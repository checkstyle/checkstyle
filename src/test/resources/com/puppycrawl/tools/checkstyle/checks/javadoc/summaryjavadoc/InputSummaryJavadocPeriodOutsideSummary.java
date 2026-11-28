/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = (default)^$
period = (default).


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

/**
 * Input for testing that only the summary text can end the first sentence.
 */
public class InputSummaryJavadocPeriodOutsideSummary {

    // violation below 'First sentence .* missing an ending period.'
    /**
     * Delegates to foo.bar for details
     *
     * @param value the value.
     */
    void periodInParamTag(int value) {}

    // violation below 'First sentence .* missing an ending period.'
    /**
     * Uses version 1.2 of the format
     *
     * @return the value.
     */
    int periodInReturnTag() { return 0; }

    // violation below 'First sentence .* missing an ending period.'
    /**
     * Delegates to foo.bar with {@code a. b} as input
     */
    void periodInInlineTag() {}

    // violation below 'First sentence .* missing an ending period.'
    /**
     * Delegates to foo.bar for details
     *
     * <p>This paragraph has a period.</p>
     */
    void periodInLaterParagraph() {}

    /**
     * Delegates to foo.bar for details.
     *
     * @param value the value.
     */
    void periodInSummary(int value) {}
}
