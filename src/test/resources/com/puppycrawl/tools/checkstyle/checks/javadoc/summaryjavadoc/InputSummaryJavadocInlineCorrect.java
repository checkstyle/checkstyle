/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = (default)^$
period = (default).


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

class InputSummaryJavadocInlineCorrect {

    /**
     * {@summary Simple JavaDoc. }
     */
    private void foo1() {}

    /**
     * {@summary {@code ABC} Javadoc.}
     */
    private void foo2() {}

    /**
     * {@summary {@code ABC} Javadoc {@code some defination}.}
     */
    private void foo3() {}

    /**
     * {@summary , *. }
     */
    private void foo4() {}

    /**
     * {@summary first sentence is normally the summary.
     * Use of html tags:
     * <ul>
     * <li>Item one.</li>
     * <li>Item two.</li>
     * </ul>}
     */
    private void validInlineJavadocWithList()
    {
    }

    /**
     * {@summary first sentence is normally the summary.
     * Use of html tags:
     * {@code SomeCodeHere.}
     * <ul>
     * <li>Item one.</li>
     * <li>Item two.</li>
     * </ul>}
     */
    private void validInlineJavadocList()
    {
    }

    /**
     * {@summary first does have period.
     * Use of html tags:
     * {@code makes tree parse properly}
     * <p>
     * This is a paragraph.
     * </p>}
     */
    private void validInlineJavadocTwo()
    {
    }

    /**
     * {@summary first sentence is normally the summary.
     * Use of html tags:
     * <h1> This is heading.</h1>
     * <p> This is a paragraph.</p>}
     */
    private void validInlineJavadocWithParagraph()
    {
    }

    /**
     * {@summary first sentence is normally the summary.
     * Use of html tags:
     * <ul>
     * <a href="NOEHRE">Item one.</a>
     * <a href="SOMEWEHRE">Item two.</a>
     * </ul>}
     */
    private void validInlineJavadoc()
    {
    }

    /**
     * {@summary first sentence is normally the summary.}
     *
     * @param a some parameter.
     * @return This method returns input back.
     */
    public int validInlineJavadocReturn(int a)
    {
        return a;
    }

    /**
     * {@summary this is first sentence with period.
     * But here should also be period.
     * }
     */
    private void voidValidJavadoc() {}

    // violation below 'First sentence of Javadoc is missing an ending period'
    /**
     * Sentence starts as a plain text sentence
     * {@summary ... but ends in the summary tag.}
     */
    public class TestClass {}

}
