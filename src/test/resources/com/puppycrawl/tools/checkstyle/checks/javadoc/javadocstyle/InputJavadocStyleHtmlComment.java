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
public class InputJavadocStyleHtmlComment
{
    /**
     * sometimes a tag starts
     * <pre>
     * somewhere and has a comment in the middle
     * <!-- ignore this -->
     * and ends afterwards
     * </pre>
     */
    private void method1()
    { // JavadocStyle should not report any error for this method
    }

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
    private void method2()
    { // JavadocStyle should not report any error for this method
    }

    /**
     * sometimes a tag starts
     * <pre>
     * somewhere and has a multiline comment in the middle
     * <!-- ignore this
     * spanning
     * multiple lines --></pre>
     * and ends on the same line
     */
    private void method3()
    { // JavadocStyle should not report any error for this method
    }

    /**
     * sometimes a tag starts
     * <pre>
     * somewhere and has a comment in the middle
     * <!-- ignore this --> with text following
     * and ends afterwards
     * </pre>
     */
    private void method4()
    { // JavadocStyle should not report any error for this method
    }

    /**
     * sometimes a tag starts
     * <pre><!-- ignore this --></pre>
     * and ends with a comment in the middle
     */
    private void method5()
    { // JavadocStyle should not report any error for this method
    }

    /**
     * Parameterized interface.
     * @param <I>
     */
    interface Interface<I> {}
}
