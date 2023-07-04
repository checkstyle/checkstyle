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

    /**
     * <p><i></i></p>
     * {@summary This is summary}
     */
    // violation 2 lines above 'Summary of Javadoc is missing an ending period'
    public void testHtmlTags2() {
    }

    // violation below 'Summary javadoc is missing.'
    /**
     * @param a parameter
     * {@summary Wrong usage}
     */
    public void method(int a) {
    }

    // violation below 'First sentence of Javadoc is missing an ending period'
    /**
     * <p> This is the summary {@summary This is not summary.}
     */
    public void testParagraphTag() {
    }

    /**
     * <pre></pre>
     * {@summary This is summary}
     */
    // violation 2 lines above 'Summary of Javadoc is missing an ending period'
    public void testPreTag() {
    }

    // violation below 'First sentence of Javadoc is missing an ending period'
    /**
     * <i>This will be in italics and is the summary</i>
     * {@summary This is not summary}
     */
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

    // Until https://github.com/checkstyle/checkstyle/issues/11425
    // violation below 'Summary javadoc is missing'
    /**
     * <ul>
     *   <li><i>This is the summary</i></li>
     * </ul>
     * {@summary This is not the summary.}
     */
    public void testMultipleElements() {
    }

    /**
     * <p> </p>
     * {@summary This is not the summary}
     */
    // violation 2 lines above 'Summary of Javadoc is missing an ending period'
    public void testHtmlTags3() {
    }
}
