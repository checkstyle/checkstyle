/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = ^[a-z]


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

public class InputSummaryJavadocTestForbiddenFragmentsWithTabs {
    // violation below 'Forbidden summary fragment'
    /**
     * adds an element to the list.
     *
     * @param element the element to add
     */
    public void add(String element) {
    }
}
