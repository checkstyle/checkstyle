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
    private void foo1() {} // ok
    // violation below
    /**
     * <p><b><i>{@summary Normal Javadoc}</i></b></p>
     */
    private void foo2() {}

    /**
     * <p>{@summary Normal Javadoc {@author my code}.}</p>
     */
    private void foo3() {} // ok

    /**
     * {@summary <p> .</p>}
     */
    private void foo4() {} // ok
    // violation below
    /**
     * <p>{@code Code.}</p>
     */
    private void foo6() {}
    // violation below
    /**
     * <p>{@summary}</p>
     */
    private void foo11() {}

    /**
     * <b><p>{@summary Normal Javadoc.}</p></b>
     */
    private void foo13() {} // ok

    /**
     * <i><b><p>{@summary Normal Javadoc.}</p></b></i>
     */
    private void foo14() {} // ok

    /** <b>{@summary Normal single line Javadoc.}</b> */
    private void foo16() {} // ok
}

