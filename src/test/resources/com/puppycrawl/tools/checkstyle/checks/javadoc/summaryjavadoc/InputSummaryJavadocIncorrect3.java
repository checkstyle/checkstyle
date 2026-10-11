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

    // violation 3 lines below 'Summary of Javadoc is missing an ending period'
    /**
     * <p><i></i></p>
     * {@summary This is summary}
     */
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

    // violation 3 lines below 'Summary of Javadoc is missing an ending period'
    /**
     * <pre></pre>
     * {@summary This is summary}
     */
    public void testPreTag() {
    }

    // violation below 'First sentence of Javadoc is missing an ending period'
    /**
     * <i>This will be in italics and is the summary</i>
     * {@summary This is not summary}
     */
    public void testOtherTags() {
    }

    // violation 4 lines below 'Summary of Javadoc is missing an ending period.'
    /**
     * <br>
     * <br>
     * {@summary Line breaks will be there but this will be the summary}
     */
    public void testBreakTag() {
    }

    // violation below 'First sentence of Javadoc is missing an ending period.'
    /**
     * <ul>
     *   <li><i>This is the summary</i></li>
     * </ul>
     * {@summary This is not the summary.}
     */
    public void testMultipleElements() {
    }

    // violation 3 lines below 'Summary of Javadoc is missing an ending period'
    /**
     * <p> </p>
     * {@summary This is not the summary}
     */
    public void testHtmlTags3() {
    }

    // violation 5 lines below 'Summary of Javadoc is missing an ending period'
    /**
     * <p>
     *
     * </p>
     * {@summary This is the summary}
     */
    public void testHtmlTags4() {
    }

    // violation below 'Summary javadoc is missing.'
    /**
     * <p>
     *
     *  {@code  text}
     * </p>
     * {@summary This is not the summary}
     */
    public void testHtmlTags5() {
    }
}
