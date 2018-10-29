////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com . puppycrawl
    .tools.
    checkstyle.checks.javadoc.javadoctype;

/**
 * Class for testing javadoc issues.
 * error missing author tag
 **/
class InputJavadocTypeWhitespace
{
    /** another check */
    void donBradman(Runnable aRun)
    {
        donBradman(new Runnable() {
            public void run() {
            }
        });

        final Runnable r = new Runnable() {
            public void run() {
            }
        };
    }

    /** bug 806243 (NoWhitespaceBeforeCheck error for anonymous inner class) */
    void bug806243()
    {
        Object o = new InputJavadocTypeWhitespace() {
            private int j ;
        };
    }
}

/**
 * Bug 806242 (NoWhitespaceBeforeCheck error with an interface).
 * @author o_sukhodolsky
 * @version 1.0
 */
interface IFoo
{
    void foo() ;
}

/**
 * Avoid Whitespace errors in for loop.
 * @author lkuehne
 * @version 1.0
 */
class SpecialCasesInForLoop
{
    public void myMethod() {
        new Thread() {
            public void run() {
            }
        }.start();
    }
}
