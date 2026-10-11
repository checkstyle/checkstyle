/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = (default)^$
period =


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

public class InputSummaryJavadocNoPeriod
{
    /**
     * As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}
     */
    void foo3() {}

    /**
     * Blabla
     */
    void foo4() throws Exception {}

    /** An especially short bit of Javadoc */
    void foo5() {}

    /**
     * {@summary No Period in end}
     */
    void foo6(){}
}
