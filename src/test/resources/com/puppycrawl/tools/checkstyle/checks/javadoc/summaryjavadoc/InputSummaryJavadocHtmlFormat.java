package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

/**
 * Config: default.
 */
public class InputSummaryJavadocHtmlFormat {

    /**
     * <p><b>{@summary Normal Javadoc.}</b></p>
     */
    private void foo1() {} // ok

    /**
     * <p><b><i>{@summary Normal Javadoc}</i></b></p>
     */
    private void foo2() {} // violation

    /**
     * <p>{@summary Normal Javadoc {@author my code}.}</p>
     */
    private void foo3() {} // violation

    /**
     * {@summary <p> .</p>}
     */
    private void foo4() {} // ok

    /**
     * <p>{@code Code.}</p>
     */
    private void foo6() {} // violation

    /**
     * <p>{@summary}</p>
     */
    private void foo11() {} // violation

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

