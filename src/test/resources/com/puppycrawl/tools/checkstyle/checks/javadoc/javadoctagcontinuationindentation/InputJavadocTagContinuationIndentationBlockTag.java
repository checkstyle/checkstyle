/*
JavadocTagContinuationIndentation
violateExecutionOnNonTightHtml = (default)false
offset = (default)4


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

import java.io.Serializable;

public class InputJavadocTagContinuationIndentationBlockTag {

    /**
     * Example from issue 5711.
     * Returns the value represented by the specified string of the specified
     * type. Returns 0 for types other than float, double, int, and long.
     * @param text the string to be parsed.
     * @param type the token type of the text. Should be a constant of
     * {@link TokenTypes}. // violation, 'Line continuation .* expected level should be 4'
     * @return the double value represented by the string argument.
     */
    public static double parseDouble(String text, int type) {
        return 0;
    }

    /**
     * Javadoc.
     *
     * @param x this line is normal
     * {@code this} line is wrongly indented
     */ // violation above 'Line continuation .* expected level should be 4'
    public void newlineThenBlockTag(int x) {
        // do stuff
    }

    /**
     * Not enough indentation.
     *
     * @param x this line is normal
     *   {@code this} line is wrongly indented
     */ // violation above 'Line continuation .* expected level should be 4'
    public void partialIndent(int x) {
        // do stuff
    }

    /**
     * There can be a newline but nothing follows it.
     *
     * @param x input
     * @return itself
     * */
    public int identity(int x) {
        return x;
    }

    /**
     * Javadoc.
     *
     * @param args
     * {@code this} line is not correctly indented
     *     {@code this} // violation above 'Line continuation .* expected level should be 4'
     * <pre>pre tags are skipped</pre>
     */
    public void multipleLines1(String args) {
        // do stuff
    }

    /**
     * Javadoc.
     *
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
    // violation 6 lines below 'Line continuation .* expected level should be 4'

    /**
     * Javadoc from regression test case (apache-ant).
     * // violation 3 lines below 'Line continuation .* expected level should be 4'
     * @param c the command line which will be configured
     * if the commandline is initially null, the function is a noop
     * otherwise the function append to the commandline arguments concerning
     * <ul> // violation, 'Line continuation .* expected level should be 4'
     * <li> // violation, 'Line continuation .* expected level should be 4'
     * cvs package // violation, 'Line continuation .* expected level should be 4'
     * </li> // violation, 'Line continuation .* expected level should be 4'
     * <li> // violation, 'Line continuation .* expected level should be 4'
     * compression // violation, 'Line continuation .* expected level should be 4'
     * </li> // violation, 'Line continuation .* expected level should be 4'
     * <li> // violation, 'Line continuation .* expected level should be 4'
     * quiet or reallyquiet // violation, 'Line continuation .* expected level should be 4'
     * </li> // violation, 'Line continuation .* expected level should be 4'
     * <li>cvsroot</li> // violation, 'Line continuation .* expected level should be 4'
     * <li>noexec</li> // violation, 'Line continuation .* expected level should be 4'
     *     <li>
     *     another item
     *     </li>
     *     <li> yet another item </li>
     * </ul> // violation, 'Line continuation .* expected level should be 4'
     * some text <i>word</i> more text.
     *     some text <i>word</i> more text.
     */ // violation 2 lines above 'Line continuation .* expected level should be 4'
    public String regressionNestedHtml(CharSequence c) {
        return "";
    }

}
