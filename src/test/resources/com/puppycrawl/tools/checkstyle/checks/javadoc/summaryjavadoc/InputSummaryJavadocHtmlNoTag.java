package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

/**
 * Config: default.
 */
public class InputSummaryJavadocHtmlNoTag {

    /**
     * <p><i>Summary Normal Javadoc.</i></p>
     */
    public void foo1() {} // ok

    /**
     * <p>Summary Normal Javadoc.</p>
     */
    public void foo2() {} //ok

    /**
     * <p>Summary Normal Javadoc</p>
     */
    public void foo3() {} //violation

    /**
     * <p><b><i>Summary Normal Javadoc.</i></b></p>
     */
    public void foo4() {} //ok

    /**
     * <p><b><i>Summary Normal Javadoc</i></b></p>
     */
    public void foo5() {} //violation

    /**
     * <p><b>Summary Normal Javadoc</b></p>
     */
    public void foo6() {} //violation

    /**
     * <p><b></b></p>
     */
    public void foo7() {} //violation
}
