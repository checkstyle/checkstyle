////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2014
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

/**
 * Test input for the JavadocStyleCheck.  This check is used to perform
 * some additional Javadoc validations.
 *
 * @author Tobias Geyer
 * @version 1.0
 */

/*
 * Config:
 * scope = private
 * excludeScope = null
 * checkFirstSentence = false
 * endOfSentenceFormat = "([.?!][ \t\n\r\f<])|([.?!]$)"
 * checkEmptyJavadoc = false
 * checkHtml = true
 */
public class InputJavadocStyleHtmlComment // ok
{
    /**
     * sometimes a tag starts
     * <pre>
     * somewhere and has a comment in the middle
     * <!-- ignore this -->
     * and ends afterwards
     * </pre>
     */
    private void method1() {} // ok

    /**
     * sometimes a tag starts
     * <pre>
     * somewhere and has a multiline comment in the middle
     * <!-- ignore this
     * spanning
     * multiple lines -->
     * and ends afterwards
     * </pre>
     */
    private void method2() {} // ok

    /**
     * sometimes a tag starts
     * <pre>
     * somewhere and has a multiline comment in the middle
     * <!-- ignore this
     * spanning
     * multiple lines --></pre>
     * and ends on the same line
     */
    private void method3() {} // ok

    /**
     * sometimes a tag starts
     * <pre>
     * somewhere and has a comment in the middle
     * <!-- ignore this --> with text following
     * and ends afterwards
     * </pre>
     */
    private void method4() {} // ok

    /**
     * sometimes a tag starts
     * <pre><!-- ignore this --></pre>
     * and ends with a comment in the middle
     */
    private void method5() {} // ok

    /**
     * Parameterized interface.
     * @param <I>
     */
    interface Interface<I> {} // ok
}
