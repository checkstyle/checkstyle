/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = (default)^$
period = (default).


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

/**
 * Config: default.
 */
public class InputSummaryJavadocHtmlFormat {

    /**
     * <p><b>{@summary Normal Javadoc.}</b></p>
     */
    private void foo1() {}
    /**
     * <p><b><i>{@summary Normal Javadoc}</i></b></p>
     */ // violation above 'Summary .* missing an ending period.'
    private void foo2() {}

    /**
     * <p>{@summary Normal Javadoc {@author my code}.}</p>
     */
    private void foo3() {}

    /**
     * {@summary <p> .</p>}
     */
    private void foo4() {}
    // violation below 'Summary javadoc is missing.'
    /**
     * <p>{@code Code.}</p>
     */
    private void foo6() {}
    /**
     * <p>{@summary}</p>
     */ // violation above 'Summary javadoc is missing.'
    private void foo11() {}

    /**
     * <b><p>{@summary Normal Javadoc.}</p></b>
     */
    private void foo13() {}

    /**
     * <i><b><p>{@summary Normal Javadoc.}</p></b></i>
     */
    private void foo14() {}

    /** <b>{@summary Normal single-line Javadoc.}</b> */
    private void foo16() {}
}

