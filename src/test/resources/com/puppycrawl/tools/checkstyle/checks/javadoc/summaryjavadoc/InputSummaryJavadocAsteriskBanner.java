/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = ^This method returns.*
period = (default).

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

public class InputSummaryJavadocAsteriskBanner {

    // violation below 'First sentence .* missing an ending period.'
    /** ********************* Test class ************************ */
    static class Test {
    }

    // violation 2 lines below 'Summary .* missing an ending period.'
    /****
     ** {@summary First sentence lacks period}
     */
    void noPeriodWithPluralLeadingAsterisks() {
    }

    // violation 2 lines below 'Forbidden summary fragment.'
    /****
     ** {@summary This method
     ** returns.}
     */
    void forbiddenFragmentWithPluralLeadingAsterisks() {
    }
}
