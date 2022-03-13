/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = (default)^$
period = 。


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

/**
 * の生成の生成。の生成の生成 の生成の生成
 */
public class InputSummaryJavadocCustomPeriodSymbol {

    // violation below 'First sentence of Javadoc is missing an ending period.'
    /**
     * お前はもう死んでいる
     */
    public void method() {
    }

    /**
     * {@summary お前はもう死んでいる}
     */
    // violation 2 lines above 'Summary of Javadoc is missing an ending period.'
    public void method2() {
    }
}
