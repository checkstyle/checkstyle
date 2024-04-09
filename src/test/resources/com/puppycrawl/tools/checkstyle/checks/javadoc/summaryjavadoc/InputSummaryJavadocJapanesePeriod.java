/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = (default)^$
period = 。


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

public class InputSummaryJavadocJapanesePeriod {

    /**
     * Summary sentence ending with correct period and no following whitespace。The Japanese
     * period has whitespace built in!
     */
    void foo1() {}

    /**
     * 要約文。別の文。
     */
    void foo2() {}
}
