/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = (default)^$
period = (default).


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

/**
 * Config: default.
 */
class InputSummaryJavadocInlineDefault2 {

    /**
     * {@summary {}@throws Exception if a problem occurs}
     */ // violation above 'Summary .* missing an ending period.'
    void foo4() throws Exception {}
    // violation below 'First sentence .* missing an ending period.'
    /**
     * mm{@inheritDoc}
     */
    void foo7() {}
    // violation below 'Summary javadoc is missing.'
    /**
     * {@link #setBounds(int,int,int,int)}
     */
    void foo8() {}

    /**
     * {@summary {@code see} .}
     */
    void foo10() {}
    /**
     * {@summary M m m m {@inheritDoc}}
     */ // violation above 'Summary .* missing an ending period.'
    void foo14() {}

    /**{@summary @summary .} */
    int foo15() {return 0;}

    /**
     * {@summary @author Akash Mondal.}
     */
    void foo16(){}

    /**
     * {@summary {@input Javadoc}.}
     */
    void foo17(){}
    /**
     * {@summary}
     */ // violation above 'Summary javadoc is missing.'
    void foo22() {}

    /** */
    String[] foo9() {return null;} // violation above 'Summary javadoc is missing.'

    /**
     * {@summary Javadoc {@code code} correct.}
     */
    void foo23(){}

    /**
     * {@summary first doesn't have period
     * Use of html tags:
     * <ul>
     * <li>Item one.</li>
     * <li>Period here.</li>
     * </ul>}
     */
    private void invalidInlineJavadocTwo()
    {
    }

    // violation 2 lines below 'Summary .* missing an ending period.'
    /**
     * {@summary first sentence is normally the summary.
     * Use of html tags:
     * {@code SomeCodeHere.}
     * <ul>
     * <li>Item one.</li>
     * <li>No period here</li>
     * </ul>}
     */
    private void invalidInlineJavadocList()
    {
    }
}

