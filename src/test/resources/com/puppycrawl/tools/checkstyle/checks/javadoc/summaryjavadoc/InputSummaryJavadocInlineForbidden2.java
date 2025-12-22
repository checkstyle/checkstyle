/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = @return the *|This method returns
period = (default).


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

public class InputSummaryJavadocInlineForbidden2 {

    /**
     * {@summary A {@code InnerInputCorrectJavaDocParagraphCheck} is a simple code.}
     */
    InputSummaryJavadocInlineForbidden.InnerInputCorrectJavaDocParagraphCheck anon =
            new InputSummaryJavadocInlineForbidden.InnerInputCorrectJavaDocParagraphCheck() {
                // violation below 'First sentence .* missing an ending period.'
                /**
                 * mm{@inheritDoc}
                 */
                void foo7() {
                }

                /**
                 * {@summary {@code see}.}
                 */
                void foo10() {
                }
            };
    // violation 2 lines below 'Summary .* missing an ending period.'
    /**
     * {@summary first sentence is normally the summary.
     * Use of html tags:
     * {@throws error}
     * <ul>
     * <li>Item one.</li>
     * <li>Item two.</li>
     * </ul>
     * <p> This is the paragraph.</p>
     * <h1> This is a heading </h1>}
     */
    public void validInlineJavadoc()
    {
    }
    /**
     * {@summary <p> </p>}
     */ // violation above 'Summary javadoc is missing.'
    void foo12() {
    }

    // violation below 'First sentence of Javadoc is missing an ending period'
    /**
     * Sentence starts as a plain text sentence
     * {@summary ... but ends in the summary tag}
     */
    public class TestClass {}

    /**
     * {@summary first sentence is normally the summary.}
     *
     * @param a some parameter.
     * @return This method returns a, this statement is allowed in return.
     */
    public int validInlineJavadocReturn(int a)
    {
        return a;
    }
}
