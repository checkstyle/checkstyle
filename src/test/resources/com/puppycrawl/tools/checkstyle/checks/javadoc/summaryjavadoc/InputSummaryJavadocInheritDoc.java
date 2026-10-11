/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = (default)^$
period = (default).


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

public class InputSummaryJavadocInheritDoc {

    /** <h1> Some header </h1> {@inheritDoc} {@code bar1} text*/
    void bar2() {} // violation above 'First sentence of Javadoc is missing an ending period'
}
