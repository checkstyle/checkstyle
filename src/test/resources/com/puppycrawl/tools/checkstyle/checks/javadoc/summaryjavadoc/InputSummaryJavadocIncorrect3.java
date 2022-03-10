/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = (default)^$
period = (default).


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

/**
 * This is the real summary.
 * {@summary This is not the summary}
 */
public class InputSummaryJavadocIncorrect3 {

    /**
     * {@summary This is the real summary.}
     * {@summary This is not the summary}
     */
    public void testAnotherSummaryTag() {
    }

    /**
     * <p>This is summary.</p>
     * {@summary This is not the summary}
     */
    public void testHtmlTags() {
    }

    // violation below 'Summary javadoc is missing.'
    /**
     * <p></p>
     * {@summary Still wont be displayed as summary}
     */
    public void testHtmlTags2() {
    }

    // violation below 'Summary javadoc is missing.'
    /**
     * @param a parameter
     * {@summary Summary Will be empty}
     */
    public void method(int a) {
    }

    /**
     * <p> This is {@summary the summary.}
     */
    public void testParagraphTag() {
    }

    // violation below 'Summary javadoc is missing.'
    /**
     * <pre></pre>
     * {@summary The summary will be empty}
     */
    public void testPreTag() {
    }

    /**
     * <i>This will be in italics</i>
     * {@summary but this will be still there in summary}
     */
    // violation 2 lines above 'Summary of Javadoc is missing an ending period.'
    public void testOtherTags() {
    }

    /**
     * <br>
     * <br>
     * {@summary Line breaks will be there but this will be the summary}
     */
    // violation 2 lines above 'Summary of Javadoc is missing an ending period.'
    public void testBreakTag() {
    }
}
