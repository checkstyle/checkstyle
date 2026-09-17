/*
JavadocTagContinuationIndentation
violateExecutionOnNonTightHtml = (default)false
offset = (default)4


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

import java.io.Serializable;

public class InputJavadocTagContinuationIndentationBlockTag {
    // violation 7 lines below 'Line continuation .* expected level should be 4'
    /**
     * Example from issue 5711.
     * Returns the value represented by the specified string of the specified
     * type. Returns 0 for types other than float, double, int, and long.
     * @param text the string to be parsed.
     * @param type the token type of the text. Should be a constant of
     * {@link TokenTypes}.
     * @return the double value represented by the string argument.
     */
    public static double parseDouble(String text, int type) {
        return 0;
    }
    // violation 4 lines below 'Line continuation .* expected level should be 4'
    /**
     * Javadoc.
     * @param x this line is normal
     * {@code this} line is wrongly indented
     */
    public void newlineThenBlockTag(int x) {
        // do stuff
    }
    // violation 4 lines below 'Line continuation .* expected level should be 4'
    /**
     * Not enough indentation.
     * @param x this line is normal
     *   {@code this} line is wrongly indented
     */
    public void partialIndent(int x) {
        // do stuff
    }
    /**
     * There can be a newline but nothing follows it.
     * @param x input
     * @return itself
     * */
    public int identity(int x) {
        return x;
    }
    // violation 4 lines below 'Line continuation .* expected level should be 4'
    /**
     * Javadoc.
     * @param args
     * {@code this} line is not correctly indented
     *     {@code this}
     * <pre>pre tags are skipped</pre>
     */
    public void multipleLines1(String args) {
        // do stuff
    }
    /**
     * Javadoc.
     * @return false always
     * {@code this} line is not correctly indented
     * {@code this} line is not correctly indented
     * <pre>pre tags are skipped</pre>
     */
    public boolean isMultipleLines2() {
        return false;
    }
    // violation 7 lines above 'Line continuation .* expected level should be 4'
    // violation 7 lines above 'Line continuation .* expected level should be 4'
    // violation 19 lines below 'Line continuation .* expected level should be 4'
    // violation 19 lines below 'Line continuation .* expected level should be 4'
    // violation 19 lines below 'Line continuation .* expected level should be 4'
    // violation 19 lines below 'Line continuation .* expected level should be 4'
    // violation 19 lines below 'Line continuation .* expected level should be 4'
    // violation 19 lines below 'Line continuation .* expected level should be 4'
    // violation 19 lines below 'Line continuation .* expected level should be 4'
    // violation 19 lines below 'Line continuation .* expected level should be 4'
    // violation 19 lines below 'Line continuation .* expected level should be 4'
    // violation 19 lines below 'Line continuation .* expected level should be 4'
    // violation 19 lines below 'Line continuation .* expected level should be 4'
    // violation 19 lines below 'Line continuation .* expected level should be 4'
    // violation 19 lines below 'Line continuation .* expected level should be 4'
    // violation 19 lines below 'Line continuation .* expected level should be 4'
    // violation 23 lines below 'Line continuation .* expected level should be 4'
    // violation 23 lines below 'Line continuation .* expected level should be 4'
    /**
     * Javadoc from regression test case (apache-ant).
     * @param c the command line which will be configured
     * if the commandline is initially null, the function is a noop
     * otherwise the function append to the commandline arguments concerning
     * <ul>
     * <li>
     * cvs package
     * </li>
     * <li>
     * compression
     * </li>
     * <li>
     * quiet or reallyquiet
     * </li>
     * <li>cvsroot</li>
     * <li>noexec</li>
     *     <li>
     *     another item
     *     </li>
     *     <li> yet another item </li>
     * </ul>
     * some text <i>word</i> more text.
     *     some text <i>word</i> more text.
     */
    public String regressionNestedHtml(CharSequence c) {
        return "";
    }
}
