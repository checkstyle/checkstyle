/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = (default)^$
period = (default).


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

/**
 * Input for testing summary javadoc inside nested HTML tags.
 */
public class InputSummaryJavadocNestedHtml {

    /**
     * <p><b>Summary Normal Javadoc.</b></p>
     */
    public void foo1() {}

    /**
     * <p><b><i>Deeply nested summary.</i></b></p>
     */
    public void foo2() {}

    /**
     * <b>Bold summary text.</b>
     */
    public void foo3() {}

    /**
     * <p><em>Emphasized summary.</em></p>
     */
    public void foo4() {}

    // violation below 'First sentence of Javadoc is missing an ending period.'
    /**
     * <p><b>Summary without period</b></p>
     */
    public void foo5() {}

    // violation below 'Summary javadoc is missing.'
    /**
     * <p><b></b></p>
     */
    public void foo6() {}

    // violation below 'Summary javadoc is missing.'
    /**
     * <p></p>
     */
    public void foo7() {}

    // violation below 'Summary javadoc is missing.'
    /**
     * <p><b><i></i></b></p>
     */
    public void foo8() {}

    /**
     * <ul>
     *   <li>This is the summary.</li>
     * </ul>
     */
    public void foo11() {}

    /**
     * <div><span>Summary inside div and span.</span></div>
     */
    public void foo9() {}

    /**
     * <p>First sentence with period. More text.</p>
     */
    public void foo10() {}
}
