/*
JavadocUtilizingTrailingSpace
ignorePattern = (default)href\\s*=\\s*"[^"]*"|http://|https://|ftp://
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for pre and code elements, void elements and comments.
 */
public class InputJavadocUtilizingTrailingSpaceHtmlCode {

    /**
     * Example usage:
     * <pre>
     * This is a very very very very very long line inside a pre block that exceeds the limit.
     * </pre>
     * After the pre block, the normal rules apply again to this line.
     */
    public void preBlock() { }

    /**
     * Example usage:
     * <code>
     * This is a very very very very very long line inside a code block that exceeds the limit.
     * </code>
     * After the code block, the normal rules apply again to this line.
     */
    public void codeBlock() { }

    /**
     * <pre>
     * <code>
     * This is a very very very very very long line of code inside pre and code elements.
     * </code>
     * </pre>
     */
    public void codeInPre() { }

    /**
     * See <code>someMethod(first,
     *   second)</code> and then continue with the rest of the sentence.
     */
    public void multiLineCodeInSentence() { }

    // violation 2 lines below 'Line is longer than 80 characters (found 88).'
    /**
     * Some text that leads up to a call of <code>someVeryLongMethod(argumentOne)</code>
     */
    public void inlineCodeAtEnd() { }

    /**
     * Line with a break<br>
     * <hr/>
     * <!-- a comment that is not part of the text -->
     * and the rest of the line.
     */
    public void voidElementsAndComments() { }

    /**<pre>
     * public void method() { }
     * </pre>
     */
    public void preOnOpeningLine() { }

    /**<b>Bold</b> text on the opening line.
     */
    public void htmlOnOpeningLine() { }

    /**
     * <!-- this comment is very long and goes over the limit of eighty characters -->
     */
    public void longComment() { }
}
