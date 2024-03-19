/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = ^$|fail-summary-fragment
period = _


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

public class InputSummaryJavadocPeriod
{   // violation below 'First sentence .* missing an ending period.'
    /**
     * As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}
     */
    void foo3() {}
    // violation below 'First sentence .* missing an ending period.'
    /**
     * Blabla
     */
    void foo4() throws Exception {}

    /** An especially short bit of Javadoc_ */
    void foo5() {}

    /**
     * An especially short bit of Javadoc_
     */
    void foo6() {}

    /**
     * {@summary An especially short bit of Javadoc_}
     */
    void foo7(){}
    /**
     * {@summary An especially short bit of Javadoc}
     */ // violation above 'Summary .* missing an ending period.'
    void foo8() {}

    // violation below 'Forbidden summary fragment.'
    /**
     * Summary sentence using wrong period. Sentence using correct period but containing disallowed words, fail-summary-fragment_
     */
    void foo9() {}
}
