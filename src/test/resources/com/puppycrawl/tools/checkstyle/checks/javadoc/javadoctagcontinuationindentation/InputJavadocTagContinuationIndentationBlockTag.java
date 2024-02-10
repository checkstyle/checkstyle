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
     * {@link TokenTypes}. // violation
     * @return the double value represented by the string argument.
     */
    public static double parseDouble(String text, int type) {
        return 0;
    }

    /**
     * Javadoc.
     *
     * @param x this line is normal
     * {@code this} line is wrongly indented // violation
     */
    public void newlineThenBlockTag(int x) {
        // do stuff
    }

    /**
     * Not enough indentation.
     *
     * @param x this line is normal
     *   {@code this} line is wrongly indented // violation
     */
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
     * {@code this} line is not correctly indented // violation
     *     {@code this}
     * <pre>this line is not correctly indented</pre> // violation
     */
    public void multipleLines1(String args) {
        // do stuff
    }

    /**
     * Javadoc.
     *
     * @return false always
     * {@code this} line is not correctly indented // violation
     * {@code this} line is not correctly indented // violation
     * <pre>this line is not correctly indented</pre> // violation
     */
    public boolean isMultipleLines2() {
        return false;
    }

    /**
     * Javadoc from regression test case (apache-ant).
     *
     * @param c the command line which will be configured
     * if the commandline is initially null, the function is a noop // violation
     * otherwise the function append to the commandline arguments concerning // violation
     * <ul> // violation
     * <li> // violation
     * cvs package // violation
     * </li> // violation
     * <li> // violation
     * compression // violation
     * </li> // violation
     * <li> // violation
     * quiet or reallyquiet // violation
     * </li> // violation
     * <li>cvsroot</li> // violation
     * <li>noexec</li> // violation
     *     <li>
     *     another item
     *     </li>
     *     <li> yet another item </li>
     * </ul> // violation
     * some text <i>word</i> more text. // violation
     *     some text <i>word</i> more text.
     */
    public String regressionNestedHtml(CharSequence c) {
        return "";
    }

}
