package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

/**
 * Config: default.
 */
public class InputSummaryJavadocHtmlFormat {

    /**
     * <p>{@summary Normal Javadoc.}</p>
     */
    private void foo1() {} // ok

    /**
     * <p>{@summary Normal Javadoc}</p>
     */
    private void foo2() {} // violation

    /**
     * <p>{@summary Normal Javadoc {@author my code}.}</p>
     */
    private void foo3() {} // violation

    /**
     * {@summary <p>Normal Javadoc.</p>}
     */
    private void foo4() {} // ok

    /**
     * <p>{@code Code.}</p>
     */
    private void foo6() {} // violation

    /**
     * <p>{@summary Some {@code Code.} javadoc.}</p>
     */
    private void foo7() {} // ok

    /**
     * <p>{@summary Some {@author Code.} javadoc.}</p>
     */
    private void foo8() {} // violation

    /**
     * <p>{@throws Some {@author Code.} javadoc.}</p>
     */
    private void foo9() {} // violation

    /**
     * <p>{@summary Some {@input Input Code.} javadoc.}</p>
     */
    private void foo10() {} // ok

    /**
     * <p>{@summary}</p>
     */
    private void foo11() {} // violation
}
