/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = (default)^$
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

    // violation below 'First sentence .* missing an ending period.'
    /**
     * Summary sentence containing correct period mid_word, but not at the end
     */
    void foo9() throws Exception {}
}
